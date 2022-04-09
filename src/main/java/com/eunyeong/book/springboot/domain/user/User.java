package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
import com.eunyeong.book.springboot.domain.books.Reserve;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name="User")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "accessToken", columnDefinition = "TEXT")
    private String accessToken;

    @Embedded
    private UserInfo userInfo;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Notice>  noticeList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<CollectInfo> collectInfoList = new ArrayList<>();


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Reserve> reserveList = new ArrayList<>();

    @Builder
    public User(String accessToken, UserInfo userInfo, List<Notice> noticeList, List<Reserve> reserveList){
        this.accessToken = accessToken;
        this.userInfo = userInfo;
        this.noticeList = noticeList;
        this.reserveList = reserveList;
    }

    public void accessTokenUpdate(String accessToken)
    {
        this.accessToken = accessToken;
    }

}
