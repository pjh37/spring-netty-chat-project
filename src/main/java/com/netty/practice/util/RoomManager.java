package com.netty.practice.util;

import com.netty.practice.service.RoomService;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomManager {
    private final Map<String, ChannelGroup> rooms=new ConcurrentHashMap<>();

    public Map<String, ChannelGroup> getRoomManager(){
        return rooms;
    }
}
