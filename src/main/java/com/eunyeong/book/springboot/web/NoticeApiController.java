package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.Notice;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NoticeApiController {
    private final UserService userService;

    // NoticeApi 게시판 저장
    @PostMapping("/notice/save")
    @ResponseBody
    public Long noticeSave(@RequestBody UserDto.NoticeResponseDto noticeResponseDto) {
        // front에서 전달받은 JSON 객체 중 email을 id로 변환
        Long seq = userService.SearchUser(noticeResponseDto.getEmail());

        // 변환받은 id로 해당 유저에 대한 notice 등록
        User user = userService.findNotice(seq);

        UserDto.NoticeSaveRequestDto noticeSaveRequestDto = new UserDto.NoticeSaveRequestDto();
        noticeSaveRequestDto.setUser(user);
        BeanUtils.copyProperties(noticeResponseDto, noticeSaveRequestDto);
        return userService.saveNotice(noticeSaveRequestDto);
    }


    // 공지사항을 눌렀을 때 보이는 상세조회
    @GetMapping("/notice/detail")
    @ResponseBody
    public Map<String, Object> searchNotice(@RequestBody HashMap<String, Long> param){
        Long id = param.get("id");

        // 조회 시, 카운팅
        userService.updateView(id);

        // User에 해당하는 게시물 출력하기 위해서 id받아오기
        Long user_id = userService.findNoticeUserId(id);

        Map<String, Object> map = new HashMap<>();

        map.put("noticeDetail", userService.searchUserNotice(user_id));

        return map;
    }

    // 전체 게시판 조회
    @GetMapping("/notice")
    @ResponseBody
    public List<UserDto.NoticeListResponseDto> NoticeAllList(){
        return userService.searchAllDesc();
    }




}
