package com.eunyeong.book.springboot.domain.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    @Query("SELECT c FROM Category c WHERE c.id=:id")
    Category findCategoryInfoById(Long id);
}
