package com.eunyeong.book.springboot.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestDto {

    @Getter
    @NoArgsConstructor
    public static class TestResponseDto{
        String library_id;
    }
}
