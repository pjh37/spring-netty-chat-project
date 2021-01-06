package com.netty.practice.service;

import com.netty.practice.domain.Chat.Message;
import com.netty.practice.domain.Room.Room;
import com.netty.practice.domain.dto.ChatLogSendReqDto;
import com.netty.practice.domain.dto.RoomCreateReqDto;
import com.netty.practice.domain.dto.RoomEnterReqDto;
import com.netty.practice.domain.dto.RoomListResDto;
import com.netty.practice.domain.Room.RoomRepository;
import com.netty.practice.util.MapperUtil;
import com.netty.practice.util.RoomManager;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private static final int BLOCK_PAGE_SIZE=4;
    private static final int PAGE_SIZE=10;
    private final RoomRepository roomRepository;
    private final RoomManager roomManager;
    //private final RedisTemplate<String,Integer> redisTemplate;

    public void create(Object body){
        RoomCreateReqDto roomCreateReqDto=MapperUtil.readValueOrThrow(body,RoomCreateReqDto.class);
        Room room=roomRepository.save(roomCreateReqDto.toEntity());
        roomManager.roomCreate(room.getId(),new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

    public void addUser(Channel channel,Object body){
        RoomEnterReqDto roomEnterReqDto=MapperUtil.readValueOrThrow(body,RoomEnterReqDto.class);
        assert roomEnterReqDto != null;
        roomManager.addUser(roomEnterReqDto.getRoomId(),channel);
    }

    @Transactional
    public List<RoomListResDto> findPagingRoom(int page){
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE, Sort.by("createDate").descending());
        return roomRepository.findPagingRoom(pageable)
                .stream()
                .map(RoomListResDto::new).collect(Collectors.toList());
    }

    public void removeUser(String roomId, Channel channel){
        roomManager.getRoomManager().get(roomId).remove(channel);
    }

    @Transactional
    public void sendBroadcast(Object body){
        ChatLogSendReqDto chatLogSendReqDto=MapperUtil.readValueOrThrow(body,ChatLogSendReqDto.class);
        assert chatLogSendReqDto != null;
        roomManager.sendBroadcast(chatLogSendReqDto.getRoomId(),body);
        save(chatLogSendReqDto);
    }

    @Transactional
    public void save(ChatLogSendReqDto chatLogSendReqDto){
        Room room=roomRepository.findById(chatLogSendReqDto.getRoomId())
                .orElseThrow(()->new IllegalArgumentException("맞는 방이 없습니다."));
        Message message=Message.builder()
                .username(chatLogSendReqDto.getUsername())
                .message(chatLogSendReqDto.getContent())
                .build();
        message.setRoom(room);
        room.addMessage(message);
    }

    public List<Integer> getPageList(int page,Long totalCount){
        List<Integer> pageList=new ArrayList<>();
        Double postTotalCount=Double.valueOf(totalCount);

        //총 게시글 기준 올림
        int totalLastPageNum=(int)(Math.ceil((postTotalCount/PAGE_SIZE)));
        int blockLastPageNum=0;
        if(page<=3){
            page=1;
            blockLastPageNum=page+BLOCK_PAGE_SIZE;
        }else{
            page=page-2;
            blockLastPageNum=Math.min(totalLastPageNum, page + BLOCK_PAGE_SIZE);
        }
        for(int val=page,idx=0;val<=blockLastPageNum;val++,idx++){
            pageList.add(val);
        }
        return pageList;
    }

    @Transactional
    public Long getTotalPageCount(Long totalCount){
        if(totalCount==1){
            return roomRepository.count();
        }
        return totalCount;
    }
}
