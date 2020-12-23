package com.netty.practice.service;

import com.netty.practice.domain.Room;
import com.netty.practice.domain.dto.ChatLogSendReqDto;
import com.netty.practice.domain.dto.RoomCreateReqDto;
import com.netty.practice.domain.dto.RoomEnterReqDto;
import com.netty.practice.domain.dto.RoomListResDto;
import com.netty.practice.repository.RoomRepository;
import com.netty.practice.util.MapperUtil;
import com.netty.practice.util.RoomManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        roomManager.getRoomManager().put(room.getRoomId(),new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

    public void addUser(Channel channel,Object body){
        RoomEnterReqDto roomEnterReqDto=MapperUtil.readValueOrThrow(body,RoomEnterReqDto.class);
        roomManager.getRoomManager().get(roomEnterReqDto.getRoomId()).add(channel);
    }

    public List<RoomListResDto> findPagingRoom(int page){
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE, Sort.by("id").descending());
        return roomRepository.findPagingRoom(pageable)
                .stream()
                .map(RoomListResDto::new).collect(Collectors.toList());
    }

    public void removeUser(String roomId, Channel channel){
        roomManager.getRoomManager().get(roomId).remove(channel);
    }

    public void sendBroadcast(Object body){
        ChatLogSendReqDto chatLogSendReqDto=MapperUtil.readValueOrThrow(body,ChatLogSendReqDto.class);
        roomManager.getRoomManager().get(chatLogSendReqDto.getRoomId()).writeAndFlush(MapperUtil.returnMessage(body));
    }

    public List<Integer> getPageList(int page){
        List<Integer> pageList=new ArrayList<>();
        Double postTotalCount=Double.valueOf(getPageCount());

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

    private Long getPageCount(){
        return roomRepository.count();
    }
}
