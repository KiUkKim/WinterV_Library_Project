package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.user.Notice;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.domain.user.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// 클라이언트에서 전달하는 데이터를 담는 객체

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserdDto{
        private String accessToken;
        private UserInfo userInfo;

        @Builder
        public UserdDto(String accessToken, UserInfo userInfo)
        {
            this.accessToken = accessToken;
            this.userInfo = userInfo;
        }


        public User toEntity2()
        {
            return User.builder()
                    .accessToken(accessToken)
                    .userInfo(userInfo)
                    .build();
        }
    }


    // 유저 update관련 dto
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserUpdateRequestDto{
        private String accessToken;
        private UserInfo userInfo;


        @Builder
        public UserUpdateRequestDto(String accessToken, UserInfo userInfo)
        {
            this.accessToken = accessToken;
            this.userInfo = userInfo;
        }
    }


    //////////////////////// Notice Dto 부분 //////////////////////////

    // Notice 출력 영역
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeResponseDto
    {
        private String email;
        private String title;
        private String content;
        private int view_count;
    }

    // NoticeSave 영역
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeSaveRequestDto
    {
        private User user;
        private String title;
        private String content;
        private int view_count;

        @Builder
        public NoticeSaveRequestDto(String title, String content, int view_count, User user)
        {
            this.title = title;
            this.content = content;
            this.view_count = view_count;
            this.user = user;
        }

        @NotNull
        public Notice toEntity(){
            return Notice.builder()
                    .title(title)
                    .content(content)
                    .view_count(view_count)
                    .user(user)
                    .build();
        }
    }


    // Notice List
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeListResponseDto{
        // Notice 영역
        private Long id;
        private LocalDateTime created_date;
        private LocalDateTime modified_date;
        private String content;
        private String title;
        private int view_count;

        // USer 영역
        private String email;
        private String given_name;

        public NoticeListResponseDto(Notice entity)
        {
            this.id = entity.getId();
            this.created_date = entity.getCreatedDate();
            this.modified_date = entity.getModifiedDate();
            this.content = entity.getContent();
            this.title = entity.getTitle();
            this.view_count = entity.getView_count();
            this.email = entity.getUser().getUserInfo().getEmail();
            this.given_name = entity.getUser().getUserInfo().getGiven_name();
        }

    }




}
