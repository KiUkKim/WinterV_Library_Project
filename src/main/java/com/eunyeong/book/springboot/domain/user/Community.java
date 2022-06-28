package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Community extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시판 제목
    @Column
    private String title;

    // 게시판 내용
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;


    // 게시판 조회수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view_count;

    // 작성자
    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

//    // 댓글
//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "community", orphanRemoval = true)
//    private List<Comments> commentsList = new ArrayList<>();


    // 추가 해야 할 것 : comment -> 댓글 List

    @Builder
    public Community(String title, String content, int view_count, User user)
    {
        this.title = title;
        this.content = content;
        this.view_count = view_count;
        this.user = user;
    }

    // 게시글 수정
    public void update(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
}
