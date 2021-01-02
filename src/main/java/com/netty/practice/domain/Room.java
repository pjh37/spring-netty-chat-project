package com.netty.practice.domain;

import com.netty.practice.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Entity
public class Room extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;//UUID.randomUUID().toString();

    private String title;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @Builder
    public Room(String title){
        this.roomId=UUID.randomUUID().toString();
        this.title=title;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public void addMessage(Message message){
        messages.add(message);
    }
}
