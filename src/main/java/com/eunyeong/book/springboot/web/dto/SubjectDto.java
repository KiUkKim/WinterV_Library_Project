package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
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
}
