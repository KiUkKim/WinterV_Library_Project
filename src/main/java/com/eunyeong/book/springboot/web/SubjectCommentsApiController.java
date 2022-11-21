package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubComments;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.user.Comments;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.Comments.CommentsService;
import com.eunyeong.book.springboot.service.subjectCommunity.Commetns.SubjectCommentsService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.SubjectDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SubjectCommentsApiController {
    private final UserService userService;

    private final SubjectCommentsService commentsService;


    // 댓글 작성
    @PostMapping("/subComments/save")
    @ResponseBody
    public Long SubCommentsSave(@RequestBody SubjectDto.SubCommentResponseDto commentResponseDto)
    {
        // 대댓글일 경우 판단. ( 대댓글일때, 해당 게시물에 해당하는 댓글인지 검증하는 로직)
        if(commentResponseDto.getCmt_id() != null)
        {
            Long community_id = commentsService.SubFindCommunityId(commentResponseDto.getCmt_id());

            if(community_id != commentResponseDto.getBoard_id())
            {
//                errorMessage2 errormessage2 = errorMessage2.builder()
//                        .status("400").msg("대댓글과 댓글의 게시물 번호가 일치하지 않습니다.").build();

                // 대댓글이 댓글의 게시물과 일치하지 않는다면 오류 발생
                throw new RuntimeException("댓글과 대댓글의 게시물 번호가 일치하지 않습니다.");
            }

        }


        //////////// 데이터 처리 //////////////////////////
        // front에서 전달받은 JSON 객체 중 email을 user table의 id로 변환
        Long seq = userService.SearchUser(commentResponseDto.getEmail());

        Assert.notNull(seq, "seq must not be NULL");

        // 게시판 id값 받아오기
        Long id = commentResponseDto.getBoard_id();


        //////////// 데이터 넣는 과정 처리 /////////////////////

        // seq로 변경한 값으로 댓글의 작성자를 저장.
        User user = commentsService.findUserSubComments(seq);

        // null check point
        Assert.notNull(user, "User must not be NULL");

        // RequestBody의 id 정보로 해당 게시물 불러오기
        SubjectCommunity community = commentsService.findSubCommunity(id);

        // null check point
        Assert.notNull(community, "community must not be NULL");

        // front에서 전달받은 게시물 목록에 댓글을 저장해둠.
        // 변환받은 id로 해당 유저에 대한 comment 등록.

        SubjectDto.SubCommentSaveDto commentSaveDto = new SubjectDto.SubCommentSaveDto();

        // 댓글 번호 받아오기
        Long cmt_id = commentResponseDto.getCmt_id();

        // 대댓글이라면,
        if(cmt_id != null)
        {
            commentSaveDto.setCommentGroup(cmt_id);
            commentSaveDto.setCommentDepth(1);

            commentsService.updateSubCommentCount(cmt_id);
        }

        commentSaveDto.setUser(user);

        commentSaveDto.setCommunity(community);

        BeanUtils.copyProperties(commentResponseDto, commentSaveDto);

        return commentsService.saveSubComments(commentSaveDto);
    }

    // 댓글 삭제 -> 완료
    @PutMapping("/subComments/delete")
    @ResponseBody
    public Long commentDelete(@RequestBody SubjectDto.SubCommentsDeleteDto commentsDeleteDto)
    {
//        return commentsService.deleteComments(commentsDeleteDto.getId(), commentsDeleteDto);
        return commentsService.deleteSubComments2(commentsDeleteDto.getId(), commentsDeleteDto);
    }

    // 댓글 수정
    @PutMapping("/subComments/update")
    @ResponseBody
    public List<SubComments> commentUpdate(@RequestBody SubjectDto.SubCommentsUpdateDto commentsUpdateDto) {
        return commentsService.SubcommentsUpdate(commentsUpdateDto);
    }
}
