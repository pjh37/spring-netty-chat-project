package com.netty.practice.domain.dto;

import com.netty.practice.domain.chat.Message;
import lombok.Getter;

@Getter
public class ChatListResDto {
    private Long id;
    private String username;
    private String content;
    private String roomId;

    public ChatListResDto(Message message){
        this.username=message.getUsername();
        this.content=message.getMessage();
        this.id=message.getId();
        this.roomId=message.getRoom().getId();
    }
}
