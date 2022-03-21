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

    // Notice에서 해당하는 id에 대한 정보가져오기
    @Query("SELECT n FROM Notice n WHERE n.id = :id")
    List<Notice> findNoticeById(Long id);
}
