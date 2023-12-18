package com.instagram.repository;

import com.instagram.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like,Integer> {

    @Query("select l from Like l where l.postId = ?1 and l.userId = ?2")
    public Optional<Like> findLike(Integer postId, Integer userId);

    public Integer deleteAllByPostId(Integer likeId);
}

