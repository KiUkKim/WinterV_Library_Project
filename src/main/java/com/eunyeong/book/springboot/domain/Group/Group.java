package com.eunyeong.book.springboot.domain.Group;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.eunyeong.book.springboot.domain.user.Comments;
import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "GroupCommunity")
public class Group extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_Id;

    // 스터디 게시판 제목
    @Column
    private String title;

    // 스터디 게시판 내용
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // 스터디 게시판 조회수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view_count;

    // 스터디 게시판 작성자 (모집자)
    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    // 끝나는 시간
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
//    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end_at;

    // 모집 마감 시간
//    @Column
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime apply_until;

    // 시작 시간
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start_at;

    // 스터디 게시판 관련 tag
    @Column(name = "tag", columnDefinition = "TEXT")
    private String tags;

    //TODO
    // 스터디 참여자 등록

    @Builder
    public Group(String title, String description, int view_count, User user, LocalDateTime end_at, LocalDateTime apply_until, LocalDateTime start_at, String tags)
    {
        this.title = title;
        this.description = description;
        this.view_count = view_count;
        this.user = user;
        this.start_at = start_at;
        this.end_at = end_at;
        this.apply_until = apply_until;
        this.tags = tags;
    }

    // 게시글 수정
    public void update(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

}

