package com.netty.practice.domain.dto;

import com.netty.practice.domain.Room.Room;
import lombok.Getter;

@Getter
public class RoomCreateReqDto {
    private String username;
    private String roomName;

    public Room toEntity(){
        return Room.builder()
                .title(roomName)
                .build();
    }
}
