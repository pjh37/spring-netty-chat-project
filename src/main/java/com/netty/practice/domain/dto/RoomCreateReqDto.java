package com.netty.practice.domain.dto;

import com.netty.practice.domain.Command;
import com.netty.practice.domain.Room;
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
