package com.eunyeong.book.springboot.service.Comments;

import com.eunyeong.book.springboot.domain.user.*;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    private final CommunityRepository communityRepository;

    ///////////////////////// Comments 관련 ////////////////////////////
    /*
    Comment 저장 관련 Service
     */

    @Transactional
    public Long saveComments(UserDto.CommentSaveDto commentSaveDto)
    {
        return commentsRepository.save(commentSaveDto.toEntity()).getCmt_id();
    }

    // 해당 seq로 User 객체 찾아오기 ( 리팩토링 필요 )
    @Transactional
    public User findUserComments(Long id)
    {
        User user3 = commentsRepository.findUSerByComments(id);

        return user3;
    }

    // 해당 id로 Community 객체 찾아오기
    @Transactional
    public Community findCommunity(Long id)
    {
        Community community = commentsRepository.findCommunityByComments(id);

        return community;
    }


    // 게시글 삭제 service
    @Transactional
    public Long deleteComments(Long id, UserDto.CommentsDeleteDto commentsDeleteDto)
    {
        // user email로 db user번호 찾기
        Long seq = communityRepository.userIDoFCommunity(commentsDeleteDto.getEmail());

        int check = commentsRepository.TrueOrFalseInComments(seq, commentsDeleteDto.getCit_id(), commentsDeleteDto.getId());

        // check == 0이 아닐 경우, 삭제하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        commentsRepository.deleteCommentsById(id);

        return id;
    }

    @Transactional
    public Long deleteComments2(Long id, UserDto.CommentsDeleteDto commentsDeleteDto)
    {
        // user email로 db user번호 찾기
        Long seq = communityRepository.userIDoFCommunity(commentsDeleteDto.getEmail());

        int check = commentsRepository.TrueOrFalseInComments(seq, commentsDeleteDto.getCit_id(), commentsDeleteDto.getId());

        // check == 0이 아닐 경우, 삭제하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        commentsRepository.deleteCommentsById2(id);

        return id;
    }

    // 게시글 수정 service
    @Transactional
    public List<Comments> commentsUpdate(UserDto.CommentsUpdateDto commentsUpdateDto)
    {
        // 해당 id에 해당하는 댓글 찾기
        Comments comments = commentsRepository.findCommentsById(commentsUpdateDto.getCmt_id(), commentsUpdateDto.getCit_id());

        Long seq = communityRepository.userIDoFCommunity(commentsUpdateDto.getEmail());

        int check = commentsRepository.TrueOrFalseInComments(seq, commentsUpdateDto.getCit_id(), commentsUpdateDto.getCmt_id());

        // check == 0일 경우, 수정하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        comments.update(commentsUpdateDto.getContent());

        List modelView = new ArrayList<>();

        modelView.add(commentsUpdateDto);

        return modelView;
    }
    
    
    /*
    조회 관련
     */
    
    // 댓글
    @Transactional
    public List<Comments> findComments(Long community_id)
    {
        return commentsRepository.findCommentList(community_id);
    }
    
    // 대댓글
    @Transactional
    public List<Comments> findCcoments(Long community_id)
    {
        return commentsRepository.findCcomentsList(community_id);
    }
    

    /////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// 대댓글 관련 로직 /////////////////////////////////////

    /*
    댓글 개수 카운팅
     */
    @Transactional
    public void updateCommentCount(Long cmt_id)
    {
        commentsRepository.IncreaseCommentsCount(cmt_id);
    }

    /*
    댓글이 어떤 게시물에 저장된 건지 확인하기 위한 로직
     */
    @Transactional
    public long FindCommunityId(Long cmt_id)
    {
        return commentsRepository.FindBoardId(cmt_id);
    }


}
