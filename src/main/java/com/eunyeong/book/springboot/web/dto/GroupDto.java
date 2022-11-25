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
        private LocalDateTime start_at;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime end_at;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime apply_until;

        private String tags;
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

        // 시작 시간 받기 위함
        private LocalDateTime start_at;

        // 끝나는 시간 받기 위함
        private LocalDateTime end_at;
        
        // 마감 시간 받기 위함
        private LocalDateTime apply_until;

        private String tags;
        
        @Builder
        public GroupCommunitySaveDto(String description, int view_count, String title, User user, LocalDateTime start_at, LocalDateTime end_at, LocalDateTime apply_until, String tags) {
            this.description = description;
            this.view_count = view_count;
            this.title = title;
            this.user = user;
            this.start_at = start_at;
            this.end_at = end_at;
            this.apply_until = apply_until;
            this.tags = tags;
        }


        @NotNull
        public Group toEntity() {
            return Group.builder()
                    .title(title)
                    .description(description)
                    .view_count(view_count)
                    .user(user)
                    .start_at(start_at)
                    .end_at(end_at)
                    .apply_until(apply_until)
                    .tags(tags)
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
        private LocalDateTime start_at; // 시작 날짜
        private LocalDateTime end_at; // 종료 날짜
        private LocalDateTime apply_until; // 모집 종료 날짜
        private String description;
        private String title;
        private int view_count;
        private String tags;

        // User 영역
        private String email;
        private String name;

        public groupCommunityListResponseDto(Group entity)
        {
            this.group_Id = entity.getGroup_Id();
            this.created_date = entity.getCreatedDate();
            this.description = entity.getDescription();
            this.start_at = entity.getStart_at();
            this.end_at = entity.getEnd_at();
            this.apply_until = entity.getApply_until();
            this.title = entity.getTitle();
            this.view_count = entity.getView_count();
            this.email = entity.getUser().getUserInfo().getEmail();
            this.name = entity.getUser().getUserInfo().getName();
            this.tags = entity.getTags();
        }
    }
}
