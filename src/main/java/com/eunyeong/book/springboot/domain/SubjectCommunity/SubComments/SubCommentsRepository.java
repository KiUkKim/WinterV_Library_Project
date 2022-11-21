package com.eunyeong.book.springboot.domain.SubjectCommunity.SubComments;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SubCommentsRepository extends JpaRepository<SubComments, Long> {

    // save를 위해서 User 객체 찾기
    @Query("SELECT u FROM User u WHERE u.seq = :seq")
    User findUSerBySubComments(Long seq);

    // save를 위해서 Community 객체 찾기
    @Query("SELECT c FROM SubjectCommunity c WHERE c.id = :id")
    SubjectCommunity findCommunityBySubComments(@Param("id") Long id);

    // Comments의 작성자와 삭제하려는 게시물의 번호가 일치하는 지 확인.
    @Query("SELECT COUNT(c) FROM SubComments c WHERE c.user.seq = :seq and c.community.id = :cit_id and c.cmt_id = :id")
    int TrueOrFalseInSubComments(Long seq, Long cit_id, Long id);

    // comments 삭제에 사용되는 Query
    @Modifying
    @Query("DELETE FROM SubComments c WHERE c.cmt_id = :id")
    int deleteSubCommentsById(Long id);

    // 댓글 삭제 로직2
    @Modifying
    @Query("update SubComments c set c.CommentDel = 'NO' WHERE c.cmt_id = :id")
    int deleteSubCommentsById2(Long id);

    // 해당 게시물의 id에 해당하는 댓글 찾기
    @Query("SELECT c FROM SubComments c WHERE c.cmt_id = :cmt_id and c.community.id = :cit_id")
    SubComments findSubCommentsById(Long cmt_id, Long cit_id);


    /////////////////////////////////////////// 댓글, 대댓글 조회 ////////////////////////////////////////

    // 댓글 조회
//    @Query("SELECT c.cmt_id, c.CommentDepth, c.content, c.CommentDel, c.user.userInfo.given_name, c.CommentCount, c.createdDate, c.modifiedDate FROM Comments c WHERE c.community.id = :community_id and c.user.seq = :user_id and c.CommentDepth = 0")
//    List<Comments> findCommentList(Long community_id, Long user_id);
//
//    // 대댓글 조회
//    @Query("SELECT c.cmt_id, c.CommentDepth, c.content, c.CommentDel, c.user.userInfo.given_name, c.createdDate, c.modifiedDate FROM Comments c WHERE c.CommentGroup = :GroupId")
//    List<Comments> findCcomentsList(Long GroupId);

    @Query("SELECT c, c.user.userInfo.name FROM SubComments c WHERE c.community.id = :community_id and c.CommentDepth = 0")
    List<SubComments> findSubCommentList(Long community_id);

    // 대댓글 조회
    @Query("SELECT c, c.user.userInfo.name FROM SubComments c WHERE c.community.id = :community_id " +
            "AND c.CommentDepth = 1 AND c.user.seq = :user_id")
    List<SubComments> findSubCcomentsList(Long user_id, Long community_id);



    /*
    Comments User 찾기 원함
     */
    @Query("SELECT c.user.seq FROM SubComments c WHERE c.community.id = :community_id and c.CommentDepth = 0")
    List<Long> SubCommentsUser(Long community_id);

    /*
    CComents User 찾기 원함
     */
    @Query("SELECT c.user.seq FROM SubComments c WHERE c.community.id = :community_id and c.CommentDepth = 1")
    Long SubCcommentsUser(Long community_id);


    /////////////////////////////////////////// 대댓글 //////////////////////////////////////////////////

    // 댓글의 대댓글 개수 파악
    @Modifying
    @Query("update SubComments c set c.CommentCount = c.CommentCount + 1 WHERE c.CommentDepth = 0 and c.cmt_id = :cmt_id")
    int IncreaseSubCommentsCount(Long cmt_id);

    // 대댓글 입력시 cmt_id에 해당하는 게시판 번호 받아오기
    @Query("SELECT c.community.id FROM SubComments c WHERE c.cmt_id = :cmt_id")
    Long FindSubBoardId(Long cmt_id);

    /////////////////////////////////////////////////////////////////////
    /*
    댓글 대댓글 관련 최신 쿼리 (2022-11-6)
    */
    @Query("SELECT c.createdDate, c.modifiedDate, c.CommentDel, c.content, u.userInfo.name FROM SubComments c LEFT JOIN User u ON u.seq = c.user.seq WHERE c.cmt_id = :cmt_id and c.CommentDepth = 0")
    List<Map<String, Object>> CommunitySubCommentsList(Long cmt_id);
}
