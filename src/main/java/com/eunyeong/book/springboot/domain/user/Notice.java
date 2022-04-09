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
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view_count;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Notice(String title, String content, int view_count, User user)
    {
        this.title = title;
        this.content = content;
        this.view_count = view_count;
        this.user = user;
    }

    public void update(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
}
