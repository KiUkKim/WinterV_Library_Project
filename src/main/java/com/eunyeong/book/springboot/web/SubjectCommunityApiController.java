package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubComments;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.subjectCommunity.Commetns.SubjectCommentsService;
import com.eunyeong.book.springboot.service.subjectCommunity.SubjectCommunityService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.SubjectDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SubjectCommunityApiController {
    private final UserService userService;

    private final SubjectCommunityService subjectCommunityService;

    private final SubjectCommentsService commentsService;

        // 커뮤니티 게시글 저장
        @PostMapping("/subjectCommunity/save")
        @ResponseBody
        public Long CommunitySave(@RequestBody SubjectDto.SubjectCommunityResponseDto communityResponseDto)
        {
            // front에서 전달받은 JSON 객체 중 email을 id로 변환
            Long seq = subjectCommunityService.SearchUser(communityResponseDto.getEmail());

            // 변환받은 id로 해당 유저에 대한 notice 등록
            // 먼저 responseDto에서 받아온 이메일을 db에 저장되어 있는 Primary Key로 수정 후 저장하는 방식 채택
            // Beanutils를 이용하여서 이러한 두 가지 DTO를 이용해서 저장
            User user = subjectCommunityService.findNotice(seq);

            SubjectDto.SubjectCommunitySaveDto communitySaveDto = new SubjectDto.SubjectCommunitySaveDto();

            communitySaveDto.setUser(user);

            BeanUtils.copyProperties(communityResponseDto, communitySaveDto);

            return subjectCommunityService.saveSubjectCommunity(communitySaveDto);
        }

        // 커뮤니티 게시글 수정
        @PutMapping("/subjectCommunity/update")
        @ResponseBody
        public Long CommunityUpdate(@RequestBody SubjectDto.SubjectCommunitySaveDto.SubjectCommunityUpdateDto communityUpdateDto)
        {
            return subjectCommunityService.SubjectCommunityUpdate(communityUpdateDto.getId(), communityUpdateDto);
        }

        // 커뮤니티 게시글 삭제
        @PutMapping("/subjectCommunity/delete")
        @ResponseBody
        public void noticeDelete(@RequestBody UserDto.CommunityDeleteDto communityDeleteDto)
        {
            userService.deleteCommunity(communityDeleteDto.getId(), communityDeleteDto);
        }

        // 커뮤니티 전체 조회
        @GetMapping("/subjectCommunity")
        @ResponseBody
        public List<SubjectDto.SubCommunityListResponseDto> SubCommunityAllList() {return subjectCommunityService.searchAllDescSubCommunity();}


        // 특정 커뮤니티 특정 게시글 조회 ( 게시글 눌렀을 때 보이는 정보 )
        @GetMapping("/subjectCommunity/detail")
        @ResponseBody
        public Map searchCommunity(@RequestParam(value = "id", required = true) Long id)
        {
            // ID Bean Null check
            Assert.notNull(id, "id must not be NULL");

            // 조회 시, 카운팅
            subjectCommunityService.updateSubCommunityView(id);

            // Community에 등록한 유저 찾기 위함
            Long user_id = subjectCommunityService.SubcommunityUser(id);

            Map<String, Object> map = new HashMap<>();

            // 게시물 조회
            map.put("communityDetail", userService.searchUserSubCommunity(user_id, id));

    //        List<Map<String, Object>> commentsList = commentsService.InfoCommunityComments(id);

    //
    //        map.put("commentsList", commentsList);

            List<SubjectDto.SubCommentsDetailDto> findComments = new ArrayList<>();

            findComments = commentsService.findSubComments(id);

            for(int i = 0; i < findComments.size(); i++)
            {
                map.put("commentsList", findComments);
            }

            user_id = userService.CcomentsUser(id);
            // 대댓글 조회
            map.put("CcomentsList", commentsService.findSubCcoments(user_id, id));

            //Bean null check
            Assert.notNull(map, "map must not be NULL");

    //        CommunityDto.ForCommunityResponseDto communityResponseDto = new CommunityDto.ForCommunityResponseDto();
    //
    //        communityResponseDto.update(map, commentsList, commentsList);

            return map;
        }

    // 특정 커뮤니티 조회
    @GetMapping("/subjectCommunity/")
    @ResponseBody
    public List<SubjectDto.SubCommunityListResponseDto> searchSubjectCommunity(@RequestParam(value = "subjectNum", required = true) Long subjectNum)
    {
        // ID Bean Null check
        Assert.notNull(subjectNum, "subjectNum must not be NULL");

       return subjectCommunityService.searchSubjectDescCommunity(subjectNum);
    }
}
