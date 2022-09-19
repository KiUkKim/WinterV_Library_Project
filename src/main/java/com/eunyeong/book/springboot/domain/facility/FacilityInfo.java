package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.books.Library;
import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class FacilityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FacilityInfoId")
    private Long id;

    // 평일/주말에 따라 openTime, closeTime 달라지는거 수정해야함
    @Column
    private LocalTime openTime;

    @Column
    private LocalTime closeTime;

    @Column(unique=true)
    private String facilityName;

    @Column
    private String groupName;

    @JsonBackReference
    @ManyToOne(targetEntity= Library.class, fetch=FetchType.EAGER)
    @JoinColumn(name="library")
    private Library library;


    @Builder
    public FacilityInfo(LocalTime openTime, LocalTime closeTime, String facilityName, Library library, String groupName) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.facilityName = facilityName;
        this.library = library;
        this.groupName=groupName;
    }
}
