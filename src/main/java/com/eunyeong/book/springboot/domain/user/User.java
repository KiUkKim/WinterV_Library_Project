package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.eunyeong.book.springboot.domain.Group.Group;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubComments;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
import com.eunyeong.book.springboot.domain.books.Reserve;
import com.eunyeong.book.springboot.domain.facility.Seats;
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

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Seats seat;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Community> communityList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Comments> commentsList = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//    private List<SubComments> subCommentsList = new ArrayList<>();
//
//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//    private List<SubjectCommunity> subjectCommunityList = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//    private List<Group> groupsList = new ArrayList<>();


    @Builder
    public User(String accessToken, UserInfo userInfo, List<Notice> noticeList, List<Reserve> reserveList, Seats seat, List<Community> communityList, List<Comments> commentsList){
        this.accessToken = accessToken;
        this.userInfo = userInfo;
        this.noticeList = noticeList;
        this.reserveList = reserveList;
        this.seat = seat;
        this.communityList = communityList;
        this.commentsList = commentsList;
    }

    public void accessTokenUpdate(String accessToken)
    {
        this.accessToken = accessToken;
    }

}
