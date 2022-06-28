package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.Comments.CommentsService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {
//    private final UserService userService;
//
//    private final CommentsService commentsService;
//    // 댓글 작성
//    @PostMapping("/comments/save")
//    public Long CommentsSave(@RequestBody UserDto.CommentResponseDto commentResponseDto)
//    {
//        // front에서 전달받은 JSON 객체 중 email을 id로 변환
//        Long seq = userService.SearchUser( commentResponseDto.getEmail());
//
//        // front에서 전달받은 게시물 목록에 댓글을 저장해둠.
//
//        // 변환받은 id로 해당 유저에 대한 comment 등록.
//        // 해당 게시글이 존재하지 않는다면, 취소,
//        User user = userService.findNotice(seq);
//
//        UserDto.CommentSaveDto commentSaveDto = new UserDto.CommentSaveDto();
//
//        commentSaveDto.setUser(user);
//
//        BeanUtils.copyProperties(commentResponseDto, commentSaveDto);
//
////        return commentsService.saveComments(commentSaveDto);
//    }
}
