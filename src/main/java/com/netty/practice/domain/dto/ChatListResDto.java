package com.netty.practice.domain.dto;

import com.netty.practice.domain.Chat.Message;
import lombok.Getter;

@Getter
public class ChatListResDto {
    private Long id;
    private String name;
    private String content;
    private String roomId;

    public ChatListResDto(Message message){
        this.name=message.getUsername();
        this.content=message.getMessage();
        this.id=message.getId();
        this.roomId=message.getRoom().getId();
    }
}
