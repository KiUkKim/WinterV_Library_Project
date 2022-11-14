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
            this.given_name = entity.getUserInfo().getName();
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
            private String name;

            public NoticeListResponseDto(Notice entity)
            {
                this.id = entity.getId();
                this.created_date = entity.getCreatedDate();
                this.modified_date = entity.getModifiedDate();
                this.content = entity.getContent();
                this.title = entity.getTitle();
                this.view_count = entity.getView_count();
                this.email = entity.getUser().getUserInfo().getEmail();
                this.name = entity.getUser().getUserInfo().getName();
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
    Community Response (출력 관련 , Save할 떄도 사용 )
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
    Community List ( 조회 시, 활용 )

     */
    // Notice List
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunityListResponseDto{
        // Community 영역
        private Long id;
        private LocalDateTime created_date;
        private LocalDateTime modified_date;
        private String content;
        private String title;
        private int view_count;

        // User 영역
        private String email;
        private String name;

        public CommunityListResponseDto(Community entity)
        {
            this.id = entity.getId();
            this.created_date = entity.getCreatedDate();
            this.modified_date = entity.getModifiedDate();
            this.content = entity.getContent();
            this.title = entity.getTitle();
            this.view_count = entity.getView_count();
            this.email = entity.getUser().getUserInfo().getEmail();
            this.name = entity.getUser().getUserInfo().getName();
        }
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
        public void CommunityUpdateDto(String title, String content){
            this.title = title;
            this.content = content;
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommunitytestDto{
        private String email;
        private Long id;

        @Builder
        public CommunitytestDto(String email, Long id)
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

        // 해당 게시글의 댓글 등록을 위함 [게시글 번호]
        private Long board_id;

        // 만약 대댓글이라면 댓글의 번호를 받기 위함
        private Long cmt_id;
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

        // 댓글인지 대댓글인지 확인
        private int CommentDepth;

        // 대댓글이라면 어느 댓글의 그룹인지
        private Long CommentGroup;

        @Builder
        public void CommentSaveDto(String content, User user, Community community, int CommentDepth, Long CommentGroup)
        {
            this.content = content;
            this.user = user;
            this.community = community;
            this.CommentDepth = CommentDepth;
            this.CommentGroup = CommentGroup;
        }

        public Comments toEntity()
        {
            return Comments.builder()
                    .content(content)
                    .user(user)
                    .community(community)
                    .CommentDepth(CommentDepth)
                    .CommentGroup(CommentGroup)
                    .build();
        }
    }

        /*
    Comments Delete 영역
     */

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentsDeleteDto{
        // 현재 로그인 되어 있는 유저와 비교
        private String email;

        // 삭제하려는 댓글 번호
        private Long id;

        // 삭제하려는 게시물 번호와 비교
        private Long cit_id;

        @Builder
        public CommentsDeleteDto(String email, Long id, Long cit_id)
        {
            this.email = email;
            this.id = id;
            this.cit_id = cit_id;
        }
    }

    /*
    Comments Update 영역
     */


    //TODO
    // 테스트 -> 완료
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentsUpdateDto{
        //comments Update영역
        private String content;
        private String email;

        private Long cit_id;

        private Long cmt_id;

        @Builder
        public void CommunityUpdateDto(String email){
            this.email = email;
        }
    }


    /*
    댓글 대댓글 관련 조회
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentsDetailDto{
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private String content;
        private int commentDepth;
        private int commentCount;
        private String commentDel;
        private Long commentGroup;

        // User 영역
        private String name;

        public CommentsDetailDto(Comments entity)
        {
            createdDate = entity.getCreatedDate();
            modifiedDate = entity.getModifiedDate();
            content = entity.getContent();
            commentDepth = entity.getCommentDepth();
            commentCount = entity.getCommentCount();
            commentDel = entity.getCommentDel();
            commentGroup = entity.getCommentGroup();
            name = entity.getUser().getUserInfo().getName().toString();
        }
    }

}
