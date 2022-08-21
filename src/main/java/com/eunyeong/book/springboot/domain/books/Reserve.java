package com.eunyeong.book.springboot.domain.books;

import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name="reserve")
public class Reserve {
    @Id
    @Column(name="seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @JsonBackReference
    @ManyToOne(targetEntity = User.class, fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @JsonBackReference
    @OneToOne(targetEntity = CollectInfo.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "collectInfo_id")
    private CollectInfo collectInfo;

    @Column //예약일
    private LocalDate reserveDate;

    @Column //도착통보일
    private LocalDate arrivalNoticeDate;

    @Column //대출대기일
    private LocalDate loanWatingDate;

    @Column //순위
    private Integer ranking;

    @Column //상태
    private Integer state;

    @Column //취소
    private Integer cancel;


    @Builder
    public Reserve(User user, CollectInfo collectInfo, LocalDate reserveDate, LocalDate arrivalNoticeDate, LocalDate loanWatingDate, Integer ranking, Integer state, Integer cancel){
        this.user=user;
        this.collectInfo = collectInfo;
        this.reserveDate=reserveDate;
        this.arrivalNoticeDate = arrivalNoticeDate;
        this.loanWatingDate=loanWatingDate;
        this.ranking=ranking;
        this.state=state;
        this.cancel=cancel;

    }

    public void update(LocalDate arrivalNoticeDate, LocalDate loanWatingDate, Integer ranking, Integer state, Integer cancel){
        this.arrivalNoticeDate=arrivalNoticeDate;
        this.loanWatingDate=loanWatingDate;
        this.ranking=ranking;
        this.state=state;
        this.cancel=cancel;
    }

}
