package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.Comments.CommentsService;
import com.eunyeong.book.springboot.service.user.UserService;
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
public class CommunityApiController {
    private final UserService userService;

    private final CommentsService commentsService;

    // 커뮤니티 게시글 저장
    @PostMapping("/community/save")
    @ResponseBody
    public Long CommunitySave(@RequestBody UserDto.CommunityResponseDto communityResponseDto)
    {
        // front에서 전달받은 JSON 객체 중 email을 id로 변환
        Long seq = userService.SearchUser(communityResponseDto.getEmail());

        // 변환받은 id로 해당 유저에 대한 notice 등록
        // 먼저 responseDto에서 받아온 이메일을 db에 저장되어 있는 Primary Key로 수정 후 저장하는 방식 채택
        // Beanutils를 이용하여서 이러한 두 가지 DTO를 이용해서 저장
        User user = userService.findNotice(seq);

        UserDto.CommunitySaveDto communitySaveDto = new UserDto.CommunitySaveDto();

        communitySaveDto.setUser(user);

        BeanUtils.copyProperties(communityResponseDto, communitySaveDto);

        return userService.saveCommunity(communitySaveDto);
    }

    // 커뮤니티 게시글 수정
    @PutMapping("/community/update")
    @ResponseBody
    public Long CommunityUpdate(@RequestBody UserDto.CommunityUpdateDto communityUpdateDto)
    {
        return userService.communityUpdate(communityUpdateDto.getId(), communityUpdateDto);
    }




    // 커뮤니티 게시글 삭제
    @PutMapping("/community/delete")
    @ResponseBody
    public void noticeDelete(@RequestBody UserDto.CommunityDeleteDto communityDeleteDto)
    {
        userService.deleteCommunity(communityDeleteDto.getId(), communityDeleteDto);
    }


    // 커뮤니티 전체 조회
    @GetMapping("/community")
    @ResponseBody
    public List<UserDto.CommunityListResponseDto> CommunityAllList() {return userService.searchAllDescCommunity();}


    //TODO
    // API 수정
    // 커뮤니티 특정 게시글 조회 ( 게시글 눌렀을 때 보이는 정보 )
    @GetMapping("/community/detail")
    @ResponseBody
    public Map<String, Object> searchCommunity(@RequestParam(value = "id", required = true) Long id)
    {
        // ID Bean Null check
        Assert.notNull(id, "id must not be NULL");

        // 조회 시, 카운팅
        userService.updateCommunityView(id);

        // Community에 등록한 유저 찾기 위함
        Long user_id = userService.communityUser(id);

        Map<String, Object> map = new HashMap<>();

        // 게시물 조회
        map.put("communityDetail", userService.searchUserCommunity(user_id, id));

        // 댓글 조회
        map.put("commentsList", commentsService.findComments(id));

        // 대댓글 조회
        map.put("CcomentsList", commentsService.findCcoments(id));

        //Bean null check
        Assert.notNull(map, "map must not be NULL");

        return map;
    }

}
