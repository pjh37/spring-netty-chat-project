package com.netty.practice.domain.dto;

import com.netty.practice.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardSaveRequestDto {
    private String title;
    private String author;
    private String content;

    @Builder
    public BoardSaveRequestDto(String title,String author,String content){
        this.title=title;
        this.author=author;
        this.content=content;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();
    }
}
