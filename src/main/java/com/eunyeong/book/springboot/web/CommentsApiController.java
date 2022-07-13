package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.Comments;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.Comments.CommentsService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {
    private final UserService userService;

    private final CommentsService commentsService;
    // 댓글 작성
    @PostMapping("/comments/save")
    @ResponseBody
    public Long CommentsSave(@RequestBody UserDto.CommentResponseDto commentResponseDto)
    {
        //////////// 데이터 처리 //////////////////////////
        // front에서 전달받은 JSON 객체 중 email을 user table의 id로 변환
        Long seq = userService.SearchUser(commentResponseDto.getEmail());

        Assert.notNull(seq, "seq must not be NULL");

        // 게시판 id값 받아오기
        Long id = commentResponseDto.getBoard_id();


        //////////// 데이터 넣는 과정 처리 /////////////////////

        // seq로 변경한 값으로 댓글의 작성자를 저장.
        User user = commentsService.findComments(seq);

        // null check point
        Assert.notNull(user, "User must not be NULL");

        // RequestBody의 id 정보로 해당 게시물 불러오기
        Community community = commentsService.findCommunity(id);

        // null check point
        Assert.notNull(community, "community must not be NULL");

        // front에서 전달받은 게시물 목록에 댓글을 저장해둠.
        // 변환받은 id로 해당 유저에 대한 comment 등록.

        UserDto.CommentSaveDto commentSaveDto = new UserDto.CommentSaveDto();

        // 댓글 번호 받아오기
        Long cmt_id = commentResponseDto.getCmt_id();

        // 대댓글이라면,
        if(cmt_id != null)
        {
            commentSaveDto.setCommentGroup(cmt_id);
            commentSaveDto.setCommentDepth(1);

            commentsService.updateCommentCount(cmt_id);
        }

        commentSaveDto.setUser(user);

        commentSaveDto.setCommunity(community);

        BeanUtils.copyProperties(commentResponseDto, commentSaveDto);

        return commentsService.saveComments(commentSaveDto);
    }

    // 댓글 삭제
    @PutMapping("/comments/delete")
    @ResponseBody
    public Long commentDelete(@RequestBody UserDto.CommentsDeleteDto commentsDeleteDto)
    {
        return commentsService.deleteComments(commentsDeleteDto.getId(), commentsDeleteDto);
    }

    // 댓글 수정
    @PutMapping("/comments/update")
    @ResponseBody
    public List<Comments> commentUpdate(@RequestBody UserDto.CommentsUpdateDto commentsUpdateDto)
    {
        return commentsService.commentsUpdate(commentsUpdateDto);
    }
}
