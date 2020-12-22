package com.netty.practice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Room {

    @Id
    private String id;//UUID.randomUUID().toString();

    private String title;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

    @Builder
    public Room(String title){
        this.id=UUID.randomUUID().toString();
        this.title=title;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }
}
