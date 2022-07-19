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
@Table(name="FacilityInfo")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx")
    private Long idx;

    @Column
    private String floor;

    @Column
    private LocalTime openTime;

    @Column
    private LocalTime closeTime;

    @Column(columnDefinition = "TEXT")
    private String FacilityTel;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="library_id")
    private Library library;

    @Builder
    public Info(String floor, LocalTime openTime, LocalTime closeTime, String FacilityTel, Library library)
    {
        this.floor = floor;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.FacilityTel = FacilityTel;
        this.library = library;
    }

}
