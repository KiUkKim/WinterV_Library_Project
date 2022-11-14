package com.eunyeong.book.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityDto {

    // User 정보 출력
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ForCommunityResponseDto {

        // 게시글 관련
        Map communityDetail = new HashMap<>();

        /*
        댓글관련정보
         */
        // comments List
        List<Map<String, Object>> commentList = new ArrayList();

        List<Map<String, Object>> CcomentList = new ArrayList();

        public void update(Map<String, Object> communityDetail, List<Map<String, Object>> commentList, List<Map<String, Object>> CcomentList)
        {
            this.communityDetail = communityDetail;
            this.commentList = commentList;
            this.CcomentList = CcomentList;
        }

    }
}
