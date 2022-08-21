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

    @Builder
    public FacilityReserve(LocalDateTime startDateTime, LocalDateTime endDateTime, User user) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.user = user;
    }

    public void update(LocalDateTime startDateTime, LocalDateTime endDateTime, User user) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.user = user;
    }

}
