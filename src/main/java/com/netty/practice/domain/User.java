package com.netty.practice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="ROOM_ID")
    private Room room;

    @Builder
    public User(String name){
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        return this.id==((User)o).id;
    }
}
