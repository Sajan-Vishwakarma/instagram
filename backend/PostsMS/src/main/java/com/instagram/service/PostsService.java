package com.instagram.service;

import com.instagram.dto.PostDTO;
import com.instagram.exception.PostsException;

import java.util.List;

public interface PostsService {

    public List<PostDTO> getPublicPostIds();
    public Integer createPost(PostDTO postDTO);
    public PostDTO getPostDetails(Integer postId) throws PostsException;
    public List<PostDTO> getPublicPostIdsMadeByUser(Integer userId);
    public List<PostDTO> getPostIdsMadeByUser(Integer userId) throws PostsException;
    public Integer deletePost(Integer postId);
}
