package com.eunyeong.book.springboot.domain.ErrorMessage;

import com.eunyeong.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class errorMessage {
    private String status;

    private Long seq;
    private String accessToken;
    private String id;
    private String email;
    private String given_name;
    private String picture;
    private String hd;

    @Builder
    public errorMessage(String status, User user)
    {
        this.status = status;
        this.seq = user.getSeq();
        this.accessToken = user.getAccessToken();
        this.id = user.getUserInfo().getId();
        this.email = user.getUserInfo().getEmail();
        this.given_name = user.getUserInfo().getGiven_name();
        this.picture = user.getUserInfo().getPicture();
        this.hd = user.getUserInfo().getHd();
    }
}
