package com.eunyeong.book.springboot.domain.SubjectCommunity;

import com.eunyeong.book.springboot.domain.user.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectCommunityRepository extends JpaRepository<SubjectCommunity, Long> {

    /*
    공통으로 사용되는 query sentences
     */
    // email로 user 번호 찾기
    @Query("SELECT u.seq FROM User u WHERE u.userInfo.email = :email")
    Long userIDForSubCommunity(String email);

    /*
    Community Update에 사용되는 Query문
     */
    @Query("SELECT c FROM SubjectCommunity c WHERE c.id = :id")
    SubjectCommunity findSubjectCommunityById(Long id);

    @Query("SELECT count(c) FROM SubjectCommunity c WHERE c.user.seq = :user_seq and c.id = :id")
    int TureOrFalseInSubCommunity(Long user_seq, Long id);

    /*
    Community Delete에 사용되는 Query Sentence.
     */
    @Modifying
    @Query("DELETE FROM SubjectCommunity c WHERE c.id = :id")
    int deleteSubCommunityById(Long id);

    /*
    Community 조회
     */
    @Query("SELECT c FROM SubjectCommunity c")
    List<SubjectCommunity> findSubCommunityByIdOrderDesc();

    // 특정 커뮤니티 조회
    @Query("SELECT c FROM SubjectCommunity c WHERE c.SubjectNumber = :subjectNumber")
    List<SubjectCommunity> findSubjectCommunitiesBy(Long subjectNumber);

    /*
    Community view 증가
     */
    @Modifying
    @Query("update SubjectCommunity c set c.view_count = c.view_count + 1 WHERE c.id = :id")
    int updateSubCommunityView(Long id);

    /*
    Commuity Table에 등록된 User seq찾기
     */
    @Query("SELECT c.user.seq FROM SubjectCommunity c WHERE c.id = :id")
    Long findSubCommunityUser(Long id);

    // List로 출력하기 위함
    @Query("SELECT c, c.user.userInfo.name FROM SubjectCommunity c WHERE c.user.seq = :user_id and c.id = :id")
    List<SubjectCommunity> findSubCommunityDetail(Long user_id, Long id);

    ///////////////////////////////////////////////////////////
    /////////////////// 게시판 -> 댓글 /////////////////////////




}
