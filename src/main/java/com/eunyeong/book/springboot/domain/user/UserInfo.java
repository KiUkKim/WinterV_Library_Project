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
    private String given_name;

    @Column
    private String picture;

    @Column
    private String hd;


    public UserInfo update(String name, String picture){
        this.given_name = name;
        this.picture= picture;

        return this;
    }

}
