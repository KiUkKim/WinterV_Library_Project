package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class FacilitiesDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SeatsUpdateRequestDto {
        private LocalDateTime startT;
        private LocalTime useT;
        private LocalDateTime checkT;
        private LocalDateTime assignmentT;
        private User user;
    }
}
