package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.books.Library;
import com.eunyeong.book.springboot.domain.facility.Seats;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class FacilitiesDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ReadingRoomDto {

        private Long id;
        private Library library;
        private String name;
        private Integer totalNum;
        private Integer useNum;
        private Integer availableNum;
        private Integer utilizationRate;
        private List<Seats> seatList;
    }
}
