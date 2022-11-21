package com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.user.Comments;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

public class SubCComents extends BaseTimeEntity {
    // 대댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ccmt_id;

    // 대댓글 내용
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    // 작성자
    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    // 댓글의 번호
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cmt_id")
    private SubComments commentsList;

    // 해당 댓글의 번호
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cmu_id")
    private SubjectCommunity community;

}
