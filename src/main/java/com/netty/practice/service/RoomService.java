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
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomManager roomManager;

    public void create(Object body){
        RoomCreateReqDto roomCreateReqDto=MapperUtil.readValueOrThrow(body,RoomCreateReqDto.class);
        Room room=roomRepository.save(roomCreateReqDto.toEntity());
        roomManager.getRoomManager().put(room.getId(),new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

    public void addUser(Channel channel,Object body){
        RoomEnterReqDto roomEnterReqDto=MapperUtil.readValueOrThrow(body,RoomEnterReqDto.class);
        log.info("addUser: "+roomEnterReqDto.getRoomId());
        log.info("addUser: "+roomEnterReqDto.getUsername());
        roomManager.getRoomManager().get(roomEnterReqDto.getRoomId()).add(channel);
    }

    public List<RoomListResDto> findAllRoom(){
        return roomRepository.findAll()
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
}
