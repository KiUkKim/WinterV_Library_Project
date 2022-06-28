package com.eunyeong.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    /*
    공통으로 사용되는 query sentences
     */

    // email로 user 번호 찾기
    @Query("SELECT u.seq FROM User u WHERE u.userInfo.email = : email")
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

}
