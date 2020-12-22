package com.netty.practice.domain.dto;

import com.netty.practice.domain.Room;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RoomListResDto implements Serializable {
    private String roomId;
    private String title;

    public RoomListResDto(Room room) {
        this.roomId = room.getId();
        this.title = room.getTitle();
    }
}
