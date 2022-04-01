package com.eunyeong.book.springboot.domain.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query("SELECT p FROM Books p ORDER BY p.id DESC")
    List<Books> findAllBookDesc();

    @Query("SELECT p FROM Books p WHERE p.title LIKE %:keyword%")
    List<Books> findByTitleContaining(String keyword);

    @Query("SELECT p FROM Books p WHERE p.title = :title")
    List<Books> findBookDetail(String title);

    @Query("SELECT p FROM Books p WHERE p.id = :book_id")
    Books findBookByid(Long book_id);
}
