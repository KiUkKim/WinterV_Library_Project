package com.eunyeong.book.springboot.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Column(name = "id", columnDefinition = "TEXT")
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    public UserInfo update(String name){
        this.name = name;

        return this;
    }

}
