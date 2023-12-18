package com.instagram.repository;

import com.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.postId = ?1")
    public Optional<Post> findByPostId(Integer postId);

    @Query("select p from Post p where p.userId = ?1")
    public List<Post> findAllByUserId(Integer userId);

    @Query("select p from Post p where p.privacy = ?1")
    public List<Post> findAllByPrivacy(Integer privacyValue);

    @Query("select p from Post p where p.privacy = 0 and p.userId = ?1")
    public List<Post> findAllPublicPostOfUser(Integer userId);

    public Integer deleteAllByPostId(Integer postId);
}
