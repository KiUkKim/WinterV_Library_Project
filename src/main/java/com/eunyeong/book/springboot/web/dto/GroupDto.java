package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.Group.Group;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GroupDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GroupSaveDto{
        private String email;
        private String title;
        private String description;
        private int view_count;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime EndTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime EndJoinTime;

        private String tag;
    }


    // Group 전체 save 영역
    @Getter
    @Setter
    @NoArgsConstructor
    public static class GroupCommunitySaveDto {
        //Group Community Entity 영역
        private String description;
        private int view_count;
        private String title;

        // User 작성자를 위한 Entity 영역
        private User user;

        // 끝나는 시간 받기 위함
        private LocalDateTime EndTime;
        
        // 마감 시간 받기 위함
        private LocalDateTime EndJoinTime;

        private String tag;
        
        @Builder
        public GroupCommunitySaveDto(String description, int view_count, String title, User user, LocalDateTime EndTime) {
            this.description = description;
            this.view_count = view_count;
            this.title = title;
            this.user = user;
            this.EndTime = EndTime;
        }


        @NotNull
        public Group toEntity() {
            return Group.builder()
                    .title(title)
                    .description(description)
                    .view_count(view_count)
                    .user(user)
                    .EndTime(EndTime)
                    .EndJoinTime(EndJoinTime)
                    .tag(tag)
                    .build();
        }
    }


    // 그룹 게시판 조회
    @Getter
    @Setter
    @NoArgsConstructor
    public static class groupCommunityListResponseDto{
        // Community 영역
        private Long group_Id;
        private LocalDateTime created_date;
        private LocalDateTime EndTime; // 종료 날짜
        private LocalDateTime EndJoinTime; // 모집 종료 날짜
        private String description;
        private String title;
        private int view_count;

        // User 영역
        private String email;
        private String name;

        public groupCommunityListResponseDto(Group entity)
        {
            this.group_Id = entity.getGroup_Id();
            this.created_date = entity.getCreatedDate();
            this.description = entity.getDescription();
            this.EndTime = entity.getEndTime();
            this.EndJoinTime = entity.getEndJoinTime();
            this.title = entity.getTitle();
            this.view_count = entity.getView_count();
            this.email = entity.getUser().getUserInfo().getEmail();
            this.name = entity.getUser().getUserInfo().getName();
        }
    }
}
