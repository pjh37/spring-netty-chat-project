package com.netty.practice.util;

import com.netty.practice.domain.Room;
import com.netty.practice.repository.RoomRepository;
import com.netty.practice.service.RoomService;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class DummyData {
    private final RoomRepository roomRepository;
    private final RoomManager roomManager;
    @PostConstruct
    public void init(){
        for(int i=0;i<107;i++){
            Room room=roomRepository.save(Room.builder()
                    .title("채팅방 리스트 "+(i+1))
                    .build());
            roomManager.getRoomManager().put(room.getRoomId(),new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
    }
}
