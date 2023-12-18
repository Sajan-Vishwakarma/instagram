package com.instagram.repository;

import com.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByUsername(String username);

    @Query("select u from User u where LOWER(u.username) like %:text% ")
    public List<User> findByMatchingUserName(@Param("text") String username);

    public Optional<User> findByEmailId(String emailId);
}
