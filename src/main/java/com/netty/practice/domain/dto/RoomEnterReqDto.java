package com.netty.practice.domain.dto;

import com.netty.practice.domain.Command;
import lombok.Getter;

@Getter
public class RoomEnterReqDto {
    private String roomId;
    private String username;
}
