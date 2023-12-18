package com.instagram.repository;


import com.instagram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comment,Integer> {

    @Query("select c from Comment c where c.postId = ?1")
    public List<Comment> findAllByPostId(Integer postId);

    public Integer deleteAllByPostId(Integer postId);

    public Optional<Comment> deleteByCid(Integer cid);
}
