package com.netty.practice.domain.dto;

import com.netty.practice.domain.Command;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class ChatLogSendReqDto {
    private String username;
    private String content;
    private String roomId;
}
