package com.eunyeong.book.springboot.domain.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReadingRoomRepository extends JpaRepository<ReadingRoom, Long> {
    @Query("SELECT r FROM ReadingRoom r WHERE r.library.id = :id")
    List<List<ReadingRoom>> readingRoomList(Long id);
}