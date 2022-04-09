package com.eunyeong.book.springboot.domain.books;

import com.eunyeong.book.springboot.domain.user.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT r FROM Reserve r WHERE r.seq=:seq")
    Reserve findReserveBySeq(Long seq);

    @Query("SELECT r FROM Reserve r WHERE r.user.seq=:user_id")
    List<Reserve> findReserveByIdOrderByUserIdDesc(Long user_id);
}
