package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.Group.GroupService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.GroupDto;
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
public class GroupCommunityApiController {
    private final UserService userService;

    private final GroupService groupService;

    @PostMapping("/groupCommunity/save")
    @ResponseBody
    public Long GroupCommunitySaveController(@RequestBody GroupDto.GroupSaveDto groupSaveDto)
    {
        System.out.println(groupSaveDto.getEndJoinTime());
        System.out.println(groupSaveDto.getEndTime());
        // front에서 전달받은 JSON 객체 중 email을 id로 변환
        Long seq = userService.SearchUser(groupSaveDto.getEmail());

        // 변환된 유저로 해당 유저에 대한 정보 받아주기
        User u = userService.findNotice(seq);

        GroupDto.GroupCommunitySaveDto groupCommunitySaveDto = new GroupDto.GroupCommunitySaveDto();

        groupCommunitySaveDto.setUser(u);

        BeanUtils.copyProperties(groupSaveDto, groupCommunitySaveDto);

        return groupService.GroupCommunitySaveService(groupCommunitySaveDto);
    }

    // 커뮤니티 전체 조회
    @GetMapping("/groupCommunity")
    @ResponseBody
    public List<GroupDto.groupCommunityListResponseDto> GetGroupCommunity()
    {
        return groupService.searchAllDescGroupCommunity();
    }

    @GetMapping("/groupCommunity/detail")
    @ResponseBody
    public Map searchCommunity(@RequestParam(value = "groupId", required = true) Long groupId)
    {
        // ID Bean Null check
        Assert.notNull(groupId, "id must not be NULL");

        // 조회 시, 카운팅
        groupService.GroupViewCount(groupId);

        // Community에 등록한 유저 찾기 위함
        Long user_id = groupService.FindUserByGroup(groupId);

        Map<String, Object> map = new HashMap<>();

        // 게시물 조회
        map.put("GroupCommunity", groupService.searchUserByGroupCommunity(user_id, groupId));

        return map;
    }


}
