package com.eunyeong.book.springboot.domain.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {
    @Query("SELECT i FROM Info i")
    List<Info> findInfo();
}
