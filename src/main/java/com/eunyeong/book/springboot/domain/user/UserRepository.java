package com.eunyeong.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.userInfo.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userInfo.id = :id")
    User findUserById(String id);

    @Query("SELECT u FROM User u WHERE u.seq =:user_id")
    User findUserByid(Long user_id);

}
