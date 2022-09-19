package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.facility.FacilityInfo;
import com.eunyeong.book.springboot.domain.facility.FacilityReserve;
import com.eunyeong.book.springboot.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
    public static class FacilityReserveSaveRequestDto{
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private User user;
        private FacilityInfo facilityInfo;

        @NotNull
        public FacilityReserve toEntity() {
            return FacilityReserve.builder()
                    .user(user)
                    .endDateTime(endDateTime)
                    .startDateTime(startDateTime)
                    .facilityInfo(facilityInfo)
                    .build();
        }
    }
}
