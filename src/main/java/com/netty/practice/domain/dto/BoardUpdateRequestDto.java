package com.netty.practice.domain.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private String title;
    private String content;
}
