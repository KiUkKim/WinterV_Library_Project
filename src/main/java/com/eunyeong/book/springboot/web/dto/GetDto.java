package com.eunyeong.book.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetDto {

    @Getter
    @NoArgsConstructor
    public static class BookSearchDto
    {
        String keyword;
    }
}
