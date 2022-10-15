package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seats, Long> {
    Seats findSeatByUser(User user);

    @Query("SELECT c.readingRoom FROM Seats c WHERE c.readingRoom.library.id = :library_id")
    List<ReadingRoom> ReturnReadingRoom(Long library_id);

    @Query("SELECT c FROM Seats c WHERE c.readingRoom.library.id = :library_id")
    List<Seats> ReturnSeats(Long library_id);
}
