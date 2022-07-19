package com.eunyeong.book.springboot.domain.ErrorMessage;

import com.eunyeong.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class errorMessage2 {
    private String status;

    private String msg;

    @Builder
    public errorMessage2(String status, String msg)
    {
        this.status = status;
        this.msg = msg;
    }
}
