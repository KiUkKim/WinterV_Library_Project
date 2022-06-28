package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.user.*;
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

    // 유저 정보 출력 dto
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserListRequestDto{
        private Long seq;
        private String accessToken;
        private String id;
        private String email;
        private String given_name;
        private String picture;
        private String hd;

        public UserListRequestDto(User entity){
            this.seq = entity.getSeq();
            this.accessToken = entity.getAccessToken();
            this.id = entity.getUserInfo().getId();
            this.email = entity.getUserInfo().getEmail();
            this.given_name = entity.getUserInfo().getGiven_name();
            this.picture = entity.getUserInfo().getPicture();
            this.hd = entity.getUserInfo().getHd();
        }
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////// Notice Dto 부분 //////////////////////////
    //////////////////////////////////////////////////////////////////

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


    // Notice Update Request
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeUpdateRequestDto{
        private String email;
        private Long id;
        private String title;
        private String content;

        @Builder
        public NoticeUpdateRequestDto(String title, String content){
            this.title = title;
            this.content = content;
        }
    }

    // Notice Delete Request
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NoticeDeleteRequestDto{
        private String email;
        private Long id;

        @Builder
        public NoticeDeleteRequestDto(String email, Long id)
        {
            this.email = email;
            this.id = id;
        }
    }


    ///////////////////// Community 관련 DTO ///////////////////////
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    /*
    Notice Response (출력 관련 , Save할 떄도 사용 )
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunityResponseDto{
        private String email;
        private String title;
        private String content;
        private int view_count;

    }



    /*
    * Community Save 영역
    * */

    // NoticeSave 영역
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunitySaveDto
    {
        // Community Entity 영역
        private String content;
        private int view_count;
        private String title;

        // User 작성자를 위한 Entity 영역
        private User user;

        @Builder
        public CommunitySaveDto(String content, int view_count, String title, User user)
        {
            this.content = content;
            this.view_count = view_count;
            this.title = title;
            this.user = user;
        }


        @NotNull
        public Community toEntity()
        {
            return Community.builder()
                    .title(title)
                    .content(content)
                    .view_count(view_count)
                    .user(user)
                    .build();
        }
    }


    /*
    Community Update 영역
     */

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunityUpdateDto{
        //Community Update영역
        private String title;
        private String content;
        private String email;
        private Long id;

        @Builder
        public void CommunityUpdateDto(String title, String email){
            this.title = title;
            this.email = email;
        }
    }


    /*
    Community Delete 영역
     */

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunityDeleteDto{
        private String email;
        private Long id;

        @Builder
        public CommunityDeleteDto(String email, Long id)
        {
            this.email = email;
            this.id = id;
        }
    }




    //////////////// COMMENTS 관련 부분 /////////////////////
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentResponseDto{
        // 댓글 내용
        private String content;

        // 댓글 작성자 받기 위함
        private String email;

        // 해당 게시글의 댓글 등록을 위함
        private Long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentSaveDto{
        // Comments 영역
        private String content;

        // User 작성자 영역
        private User user;

        // Community 영역
        private Community community;

        @Builder
        public void CommentSaveDto(String content, User user, Community community)
        {
            this.content = content;
            this.user = user;
            this.community = community;
        }

        @NotNull
        public Comments toEntity()
        {
            return Comments.builder()
                    .content(content)
                    .user(user)
                    .community(community)
                    .build();
        }
    }


}
