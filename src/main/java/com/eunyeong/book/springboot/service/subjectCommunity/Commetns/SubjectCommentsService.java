package com.eunyeong.book.springboot.service.subjectCommunity.Commetns;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubComments;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments.SubCommentsRepository;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunityRepository;
import com.eunyeong.book.springboot.domain.user.*;
import com.eunyeong.book.springboot.web.dto.SubjectDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectCommentsService {
    private final SubCommentsRepository commentsRepository;

    private final SubjectCommunityRepository communityRepository;

    ///////////////////////// Comments 관련 ////////////////////////////
    /*
    Comment 저장 관련 Service
     */

    @Transactional
    public Long saveSubComments(SubjectDto.SubCommentSaveDto savedto)
    {
        return commentsRepository.save(savedto.toEntity()).getCmt_id();
    }

    // 해당 seq로 User 객체 찾아오기 ( 리팩토링 필요 )
    @Transactional
    public User findUserSubComments(Long id)
    {
        User user3 = commentsRepository.findUSerBySubComments(id);

        return user3;
    }

    // 해당 id로 Community 객체 찾아오기
    @Transactional
    public SubjectCommunity findSubCommunity(Long id)
    {
        SubjectCommunity community = commentsRepository.findCommunityBySubComments(id);

        return community;
    }


    // 게시글 삭제 service
    @Transactional
    public Long deleteSubComments(Long id, SubjectDto.SubCommentsDeleteDto subCommentsDeleteDto)
    {
        // user email로 db user번호 찾기
        Long seq = communityRepository.userIDForSubCommunity(subCommentsDeleteDto.getEmail());

        int check = commentsRepository.TrueOrFalseInSubComments(seq, subCommentsDeleteDto.getCit_id(), subCommentsDeleteDto.getId());

        // check == 0이 아닐 경우, 삭제하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        commentsRepository.deleteSubCommentsById(id);

        return id;
    }

    @Transactional
    public Long deleteSubComments2(Long id, SubjectDto.SubCommentsDeleteDto commentsDeleteDto)
    {
        // user email로 db user번호 찾기
        Long seq = communityRepository.userIDForSubCommunity(commentsDeleteDto.getEmail());

        int check = commentsRepository.TrueOrFalseInSubComments(seq, commentsDeleteDto.getCit_id(), commentsDeleteDto.getId());

        // check == 0이 아닐 경우, 삭제하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        commentsRepository.deleteSubCommentsById2(id);

        return id;
    }

    // 게시글 수정 service
    @Transactional
    public List<SubComments> SubcommentsUpdate(SubjectDto.SubCommentsUpdateDto subCommentsUpdateDto)
    {
        // 해당 id에 해당하는 댓글 찾기
        SubComments comments = commentsRepository.findSubCommentsById(subCommentsUpdateDto.getCmt_id(), subCommentsUpdateDto.getCit_id());

        Long seq = communityRepository.userIDForSubCommunity(subCommentsUpdateDto.getEmail());

        int check = commentsRepository.TrueOrFalseInSubComments(seq, subCommentsUpdateDto.getCit_id(), subCommentsUpdateDto.getCmt_id());

        // check == 0일 경우, 수정하려는 게시물의 댓글이 아니거나, 작성자가 아닙니다.
        if(check < 1)
        {
            throw new RuntimeException("댓글의 작성자가 아니거나 유효하지 않은 게시물의 댓글입니다.");
        }

        comments.update(subCommentsUpdateDto.getContent());

        List modelView = new ArrayList<>();

        modelView.add(subCommentsUpdateDto);

        return modelView;
    }


    /*
    조회 관련
     */

    // 댓글
    @Transactional
    public List<SubjectDto.SubCommentsDetailDto> findSubComments(Long community_id)
    {
        return commentsRepository.findSubCommentList(community_id).stream()
                .map(SubjectDto.SubCommentsDetailDto::new)
                .collect(Collectors.toList());
    }

    // 대댓글
    @Transactional
    public List<SubjectDto.SubCommentsDetailDto> findSubCcoments(Long user_id, Long community_id)
    {
        return commentsRepository.findSubCcomentsList(user_id, community_id).stream()
                .map(SubjectDto.SubCommentsDetailDto::new)
                .collect(Collectors.toList());
    }


    /////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// 대댓글 관련 로직 /////////////////////////////////////

    /*
    댓글 개수 카운팅
     */
    @Transactional
    public void updateSubCommentCount(Long cmt_id)
    {
        commentsRepository.IncreaseSubCommentsCount(cmt_id);
    }

    /*
    댓글이 어떤 게시물에 저장된 건지 확인하기 위한 로직
     */
    @Transactional
    public long SubFindCommunityId(Long cmt_id)
    {
        return commentsRepository.FindSubBoardId(cmt_id);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// 댓글 대댓글 관련 로직 /////////////////////////////////////
    //////////////////// 수정중 2022-11-7 3차 ////////////////////////////////////////////////
    @Transactional
    public List<Map<String, Object>> InfoSubCommunityComments(Long cmt_id)
    {
        return commentsRepository.CommunitySubCommentsList(cmt_id);
    }

}


