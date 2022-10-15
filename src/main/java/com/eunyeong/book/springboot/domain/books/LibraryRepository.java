package com.eunyeong.book.springboot.domain.books;

import com.eunyeong.book.springboot.domain.facility.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long>{
    @Query("SELECT c FROM Library c WHERE c.id= :id")
    Library findLibraryInfoById(Long id);

    @Query("SELECT c.readingRoomList FROM ReadingRoom r,Library c WHERE c.id = :id")
    List<ReadingRoom> findReadingRoomInfo(Long id);
}
