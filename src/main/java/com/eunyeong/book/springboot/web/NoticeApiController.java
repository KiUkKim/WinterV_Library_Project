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


    // 공지사항을 눌렀을 때 보이는 상세조회 (이 때, 해당 게시글이 조회수 업데이트 )
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

    // 게시판 수정
    @PutMapping("/notice/update")
    @ResponseBody
    public Long noticeUpdate(@RequestBody UserDto.NoticeUpdateRequestDto noticeUpdateRequestDto)
    {
        /*
        front에서 받아야 할 정보 : 로그인 되어있는 user의 email, 수정하려는 게시글 id
        수정되어야 할 정보 : 제목, 내용 ( 임시)
        형식 :
        { email : '***********',
          id : x,
          title : '****',
          content : '****'
          }
         */

        // 현재 로그인되어 있는 유저 email -> seq로 변환 ( Notice table에서 비교를 위해)
        // 이메일 정보에 해당하는 유저 db 조회 ( 작성자 이외의 사람이 수정을 하게하면 안된다. )
        // 로그인 정보 일치 -> 업데이트,
        // 정보 불일치 -> 예외처리
        // 다음과 같은 정보를 service에 구현했음
        return userService.update(noticeUpdateRequestDto.getId(), noticeUpdateRequestDto);
    }


    // 게시판 삭제
    @PutMapping("/notice/delete")
    @ResponseBody
    public void noticeDelete(@RequestBody UserDto.NoticeDeleteRequestDto noticeDeleteRequestDto)
    {
        userService.delete(noticeDeleteRequestDto.getId(), noticeDeleteRequestDto);
    }
}
