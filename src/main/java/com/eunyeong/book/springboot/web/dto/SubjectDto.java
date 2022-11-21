package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubComments;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.user.Comments;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SubjectDto {

        /*
    Community Response (출력 관련 , Save할 떄도 사용 )
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SubjectCommunityResponseDto{
        private String email;
        private String title;
        private String content;
        private int view_count;
        private Long subjectNum;
    }

    // NoticeSave 영역
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SubjectCommunitySaveDto
    {
        // Community Entity 영역
        private String content;
        private int view_count;
        private String title;

        // User 작성자를 위한 Entity 영역
        private User user;

        // 학과별로 나누기 위함
        private Long subjectNum;

        @Builder
        public SubjectCommunitySaveDto(String content, int view_count, String title, User user, Long subjectNum)
        {
            this.content = content;
            this.view_count = view_count;
            this.title = title;
            this.user = user;
            this.subjectNum = subjectNum;
        }


        @NotNull
        public SubjectCommunity toEntity()
        {
            return SubjectCommunity.builder()
                    .title(title)
                    .content(content)
                    .view_count(view_count)
                    .user(user)
                    .SubjectNumber(subjectNum)
                    .build();
        }


            /*
    Community Update 영역
     */

        @Getter
        @Setter
        @NoArgsConstructor
        public static class SubjectCommunityUpdateDto{
            //Community Update영역
            private String title;
            private String content;
            private String email;
            private Long id;

            @Builder
            public void SubjectCommunityUpdateDto(String title, String content){
                this.title = title;
                this.content = content;
            }
        }
    }

        /*
    Community Delete 영역
     */

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SubCommunityDeleteDto{
        private String email;
        private Long id;

        @Builder
        public SubCommunityDeleteDto(String email, Long id)
        {
            this.email = email;
            this.id = id;
        }
    }

    /*
Community List ( 조회 시, 활용 )

 */
    // Notice List
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SubCommunityListResponseDto{
        // Community 영역
        private Long id;
        private LocalDateTime created_date;
        private LocalDateTime modified_date;
        private String content;
        private String title;
        private int view_count;
        private Long subjectNum;

        // User 영역
        private String email;
        private String name;

        public SubCommunityListResponseDto(SubjectCommunity entity)
        {
            this.id = entity.getId();
            this.created_date = entity.getCreatedDate();
            this.modified_date = entity.getModifiedDate();
            this.content = entity.getContent();
            this.title = entity.getTitle();
            this.view_count = entity.getView_count();
            this.email = entity.getUser().getUserInfo().getEmail();
            this.name = entity.getUser().getUserInfo().getName();
            this.subjectNum = entity.getSubjectNumber();
        }
    }

    //////////////// COMMENTS 관련 부분 /////////////////////
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SubCommentResponseDto{
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
    public static class SubCommentSaveDto{
        // Comments 영역
        private String content;

        // User 작성자 영역
        private User user;

        // Community 영역
        private SubjectCommunity community;

        // 댓글인지 대댓글인지 확인
        private int CommentDepth;

        // 대댓글이라면 어느 댓글의 그룹인지
        private Long CommentGroup;

        @Builder
        public void CommentSaveDto(String content, User user, SubjectCommunity community, int CommentDepth, Long CommentGroup)
        {
            this.content = content;
            this.user = user;
            this.community = community;
            this.CommentDepth = CommentDepth;
            this.CommentGroup = CommentGroup;
        }

        public SubComments toEntity()
        {
            return SubComments.builder()
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
    public static class SubCommentsDeleteDto{
        // 현재 로그인 되어 있는 유저와 비교
        private String email;

        // 삭제하려는 댓글 번호
        private Long id;

        // 삭제하려는 게시물 번호와 비교
        private Long cit_id;

        @Builder
        public SubCommentsDeleteDto(String email, Long id, Long cit_id)
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
    public static class SubCommentsUpdateDto{
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
    public static class SubCommentsDetailDto{
        private Long cmt_id;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private String content;
        private int commentDepth;
        private int commentCount;
        private String commentDel;
        private Long commentGroup;

        // User 영역
        private String name;

        public SubCommentsDetailDto(SubComments entity)
        {
            cmt_id = entity.getCmt_id();
            createdDate = entity.getCreatedDate();
            modifiedDate = entity.getModifiedDate();
            content = entity.getContent();
            commentDepth = entity.getCommentDepth();
            commentCount = entity.getCommentCount();
            commentDel = entity.getCommentDel();
            commentGroup = entity.getCommentGroup();
            name = entity.getUser().getUserInfo().getName();
        }
    }
}
