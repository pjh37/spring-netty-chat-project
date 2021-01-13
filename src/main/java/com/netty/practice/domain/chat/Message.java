package com.netty.practice.domain.chat;

import com.netty.practice.domain.BaseTimeEntity;
import com.netty.practice.domain.Room.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ROOM_ID")
    private Room room;

    private String username;

    private String message;

    @Builder
    public Message(String username,String message){
        this.username=username;
        this.message=message;
    }

    public void setRoom(Room room){
        this.room=room;
        room.getMessages().add(this);
    }
}
