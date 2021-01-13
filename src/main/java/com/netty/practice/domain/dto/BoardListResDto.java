package com.netty.practice.domain.dto;


import com.netty.practice.domain.board.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Long clickedCount;
    private String createDate;

    public BoardListResDto(Board board){
        this.id=board.getId();
        this.title=board.getTitle();
        this.author=board.getAuthor();
        this.content=board.getContent();
        this.createDate=board.getCreateDate().toLocalDate().toString();
    }
}
