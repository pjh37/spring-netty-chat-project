package com.netty.practice.domain;

public enum Command {

    ROOM_CREATE(false),
    ROOM_DELETE(false),
    ROOM_ENTER(false),
    ROOM_LIST(false),
    ROOM_EXIT(false),
    CONNECT(false),
    CHAT_LOG_SEND(false),
    CHAT_LOG_RECEIVE(false);

    private boolean isReceive;

    Command(boolean isReceive){
        this.isReceive=isReceive;
    }
}
