package com.instagram.repository;

import com.instagram.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media,Integer> {

    @Query("select m from Media m where m.postId = ?1 ")
    public List<Media> getAllMediaByPostId(Integer postid);

    public Integer deleteAllByPostId(Integer postId);
}
