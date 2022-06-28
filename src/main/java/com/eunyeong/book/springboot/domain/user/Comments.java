package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    // 댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmt_id;

    // 댓글 내용
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    // 댓글 작성자
    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

//    // 해당 게시글에 대한 댓글
//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name="community_id")
//    private Community community;

    @Builder
    public Comments(String content, User user, Community community)
    {
        this.content = content;
        this.user = user;
//        this.community = community;
    }

}
