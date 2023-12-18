package com.instagram.repository;

import com.instagram.entity.PostActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostActivityRepository extends JpaRepository<PostActivity,Integer> {

    public Optional<PostActivity> findByPostId(Integer postId);
    public Integer deleteByPostId(Integer postId);
}
