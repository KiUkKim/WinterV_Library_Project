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
    private String name;

    @Builder
    public errorMessage(String status, User user)
    {
        this.status = status;
        this.seq = user.getSeq();
        this.accessToken = user.getAccessToken();
        this.id = user.getUserInfo().getId();
        this.email = user.getUserInfo().getEmail();
        this.name = user.getUserInfo().getName();
    }
}
