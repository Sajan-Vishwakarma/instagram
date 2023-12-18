package com.instagram.repository;

import com.instagram.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrendingRepository extends JpaRepository<Tags,Integer> {


    @Query("select t from Tags t where t.tagname = ?1 and t.postId = ?2")
    public Optional<Tags> findByTagNameAndPostId(String tagname, Integer postId);

    @Query("select t.postId from Tags t where t.tagname = ?1")
    public List<Integer> findPostIdsByTagName(String tagname);

    @Query("select t.tid from Tags t where t.postId = ?1")
    public List<Integer> findAllTidByPostId(Integer postId);

}

