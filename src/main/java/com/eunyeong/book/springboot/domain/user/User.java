package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
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
    private List<CollectInfo> collectInfoListForUser = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Community> communityList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Comments> commentsList = new ArrayList<>();



    @Builder
    public User(String accessToken, UserInfo userInfo, List<Notice> noticeList, List<CollectInfo> collectInfoList, List<Community> communityList, List<Comments> commentsList){
        this.accessToken = accessToken;
        this.userInfo = userInfo;
        this.noticeList = noticeList;
        this.collectInfoListForUser = collectInfoList;
        this.communityList = communityList;
        this.commentsList = commentsList;
    }

    public void accessTokenUpdate(String accessToken)
    {
        this.accessToken = accessToken;
    }

}
