package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="seats")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEAT_ID")
    private Long pk;

    @Column
    private LocalDateTime startT; // 좌석 배정 시간(현재시간)

    @Column
    private LocalTime useT; //4시간, 사용시간

    @Column
    private LocalTime maxT;  //4시간, 최대 이용 가능시간(4시간이용가능석)

    @Column
    private LocalDateTime checkT; //12:56까지 입실확인(현재시간+10분)

    @Column
    private LocalDateTime assignmentT; //16:46까지, 배정시간(현재시간+사용시간)

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="READINGROOM_ID")
    private ReadingRoom readingRoom;

    @JsonBackReference
    @OneToOne(targetEntity= User.class, fetch=FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;

    @Builder
    public Seats(LocalDateTime startT, LocalTime useT, LocalTime maxT, LocalDateTime checkT, LocalDateTime assignmentT,  User user, ReadingRoom readingRoom){
        this.startT=startT;
        this.useT=useT;
        this.maxT=maxT;
        this.checkT=checkT;
        this.assignmentT=assignmentT;
        this.user=user;
        this.readingRoom=readingRoom;
    }

    public void update(LocalDateTime startT, LocalTime useT, LocalDateTime checkT, LocalDateTime assignmentT, User user){
        this.startT=startT;
        this.useT = useT;
        this.checkT = checkT;
        this.assignmentT=assignmentT;
        this.user=user;
    }
}