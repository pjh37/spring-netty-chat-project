package com.netty.practice.util;

import com.netty.practice.service.RoomService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoomManager {
    private final Map<String, ChannelGroup> rooms=new ConcurrentHashMap<>();

    public Map<String,ChannelGroup> getRoomManager(){
        return rooms;
    }

    public ChannelGroup getRoom(String roomId){
        if(roomId==null){
            throw new NullPointerException("roomId is not exist");
        }

        return rooms.get(roomId);
    }

    public void sendBroadcast(String roomId,Object body){
        getRoom(roomId).writeAndFlush(MapperUtil.returnMessage(body));
        //chaneelgroup으로 한번에 보내기
//        getRoom(roomId).parallelStream()
//                .filter(Channel::isOpen)
//                .forEach(channel -> channel.writeAndFlush(MapperUtil.returnMessage(body)));
    }

    public boolean addUser(String roomId,Channel channel){
        return getRoom(roomId).add(channel);
    }

    public ChannelGroup roomCreate(String roomId,ChannelGroup channelGroup){
        return rooms.put(roomId,channelGroup);
    }
}
