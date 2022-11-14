package com.eunyeong.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    /*
    공통으로 사용되는 query sentences
     */
    // email로 user 번호 찾기
    @Query("SELECT u.seq FROM User u WHERE u.userInfo.email = :email")
    Long userIDoFCommunity(String email);

    /*
    Community Update에 사용되는 Query문
     */
    @Query("SELECT c FROM Community c WHERE c.id = :id")
    Community findCommunityById(Long id);

    @Query("SELECT count(c) FROM Community c WHERE c.user.seq = :user_seq and c.id = :id")
    int TureOrFalseInCommunity(Long user_seq, Long id);

    /*
    Community Delete에 사용되는 Query Sentence.
     */
    @Modifying
    @Query("DELETE FROM Community c WHERE c.id = :id")
    int deleteCommunityById(Long id);

    /*
    Community 조회
     */
    @Query("SELECT c FROM Community c")
    List<Community> findCommunityByIdOrderDesc();

    /*
    Community view 증가
     */
    @Modifying
    @Query("update Community c set c.view_count = c.view_count + 1 WHERE c.id = :id")
    int updateCommunityView(Long id);

    /*
    Commuity Table에 등록된 User seq찾기
     */
    @Query("SELECT c.user.seq FROM Community c WHERE c.id = :id")
    Long findCommunityUser(Long id);

    // List로 출력하기 위함
    @Query("SELECT c, c.user.userInfo.name FROM Community c WHERE c.user.seq = :user_id and c.id = :id")
    List<Community> findCommunityDetail(Long user_id, Long id);

    ///////////////////////////////////////////////////////////
    /////////////////// 게시판 -> 댓글 /////////////////////////




}
