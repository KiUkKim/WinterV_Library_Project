package com.eunyeong.book.springboot.domain.user;

import com.eunyeong.book.springboot.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    // 해당 게시글에 대한 댓글
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="community_id")
    private Community community;

    // depth 설정 (0이면 부모댓글, 1이면 답글(대댓글), default = 0)
    @Column(name = "CommentDepth")
    @ColumnDefault("0")
    private int CommentDepth;

    // 대댓글이라면 어느 댓글의 부모인지 저장
    @Column(name = "CommentGroup")
    private Long CommentGroup;

    @Column(name = "CommentCount")
    @ColumnDefault("0")
    private int CommentCount;

    // 댓글 삭제 여부 확인 ( 삭제되었으면 front에서 삭제된 댓글이라고 띄우기 위함)
    @Column(name = "CommentDel")
    private String CommentDel = "YES";

    @Builder
    public Comments(String content, User user, Community community, int CommentDepth, Long CommentGroup)
    {
        this.content = content;
        this.user = user;
        this.community = community;
        this.CommentDepth = CommentDepth;
        this.CommentGroup = CommentGroup;
    }

    // 댓글 수정
    public void update(String content)
    {
        this.content = content;
    }

}
