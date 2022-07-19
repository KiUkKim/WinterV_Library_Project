package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.facility.Info;
import com.eunyeong.book.springboot.domain.user.Notice;
import com.eunyeong.book.springboot.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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


    @Getter
    @Setter
    @NoArgsConstructor
    public static class InfoListDto{
        // Library Table
        private Long library_id;
        private String library_name;

        // Info Table
        private String floor;
        private LocalTime openTime;
        private LocalTime closeTime;
        private String FacilityTel;

        public InfoListDto(Info entity)
        {
            this.library_id = entity.getLibrary().getId();
            this.library_name = entity.getLibrary().getLibrary_name();
            this.floor = entity.getFloor();
            this.openTime = entity.getOpenTime();
            this.closeTime = entity.getCloseTime();
            this.FacilityTel = entity.getFacilityTel();
        }
    }
}
