package com.eunyeong.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT u.seq FROM User u WHERE u.userInfo.email = :email")
    Long userIdForEmail(String email);

    @Query("SELECT u FROM User u WHERE u.seq = :seq")
    User findNoticeUSer(Long seq);

    // Notice Table에서 user_id 찾기
    @Query("SELECT n.user.seq FROM Notice n where n.id = :id")
    Long findNoticeUserId(Long id);

    @Modifying
    @Query("update Notice n set n.view_count = n.view_count + 1 where n.id = :id")
    int updateView(Long id);

    @Query("SELECT n FROM Notice n")
    List<Notice> findNoticeByIdOrderByIdDesc();

    // Notice에서 해당하는 id에 대한 정보가져오기 ( List로 출력을 위함 )
    @Query("SELECT n FROM Notice n WHERE n.id = :id")
    List<Notice> findNoticeById(Long id);

    /*
    Update에서 사용되는 query sentence
     */
    @Query("SELECT count(n) FROM Notice n WHERE n.user.seq = :user_seq and n.id = :id")
    int TrueOrFalseInNotice(Long user_seq, Long id);

    // Notice에서 해당하는 id에 대한 정보가져오기 ( Notice 객체로 가져오기 )
    @Query("SELECT n FROM Notice n WHERE n.id = :id")
    Notice findNotice(Long id);


    /////// Delete 관련////////////
    @Modifying
    @Query("DELETE FROM Notice n WHERE n.id = :id")
    int deleteNotice(Long id);

}
