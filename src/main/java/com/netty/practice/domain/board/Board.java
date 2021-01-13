package com.netty.practice.domain.board;

import com.netty.practice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 100,nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private Long clickedCount;

    @Builder
    public Board(String title,String author,String content){
        this.title=title;
        this.author=author;
        this.content=content;
        this.clickedCount=0L;
    }

    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }
}
