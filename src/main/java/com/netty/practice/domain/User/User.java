package com.netty.practice.domain.User;

import com.netty.practice.domain.BaseTimeEntity;
import com.netty.practice.domain.Room.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT",nullable = true)
    private String email;

    @Column(length = 500 ,nullable = true)
    private String password;

    @ManyToOne
    @JoinColumn(name="ROOM_ID")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder
    public User(String name,String email,String password,UserRole role){
        this.name=name;
        this.email=email;
        this.password=password;
        this.role=role;
    }

    public User update(String name){
        this.name=name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return this.id==((User)o).id;
    }
}
