package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository extends JpaRepository<Seats, Long> {
    Seats findSeatByUser(User user);
}
