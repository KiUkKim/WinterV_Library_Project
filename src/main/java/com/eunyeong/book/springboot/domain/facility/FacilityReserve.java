package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class FacilityReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FacilityReserveId")
    private Long id;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    //대표 예약자 1명
    @JsonBackReference
    @OneToOne(targetEntity= User.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user")
    private User user;

    @JsonBackReference
    @OneToOne(targetEntity= FacilityInfo.class)
    @JoinColumn(name="facilityInfo_id")
    private FacilityInfo facilityInfo;

    @Builder
    public FacilityReserve(LocalDateTime startDateTime, LocalDateTime endDateTime, User user, FacilityInfo facilityInfo) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.user = user;
        this.facilityInfo=facilityInfo;
    }

    public void update(LocalDateTime startDateTime, LocalDateTime endDateTime, User user, FacilityInfo facilityInfo) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.user = user;
        this.facilityInfo=facilityInfo;
    }

}
