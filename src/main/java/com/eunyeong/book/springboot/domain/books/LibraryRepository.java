package com.eunyeong.book.springboot.domain.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibraryRepository extends JpaRepository<Library, Long>{
    @Query("SELECT c FROM Library c WHERE c.id= :id")
    Library findLibraryInfoById(Long id);
}
