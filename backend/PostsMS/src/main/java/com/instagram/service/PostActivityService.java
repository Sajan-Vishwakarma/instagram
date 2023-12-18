package com.instagram.service;


import com.instagram.dto.PostActivityDTO;
import com.instagram.exception.PostActivityException;

public interface PostActivityService {
    public PostActivityDTO getPostActivity(Integer postId) throws PostActivityException;
    public PostActivityDTO createPostActivity(Integer postId) throws PostActivityException;
    public PostActivityDTO increaseLikeCount(Integer postId) throws PostActivityException;
    public PostActivityDTO decreaseLikeCount(Integer postId) throws PostActivityException;
    public PostActivityDTO increaseCommentCount(Integer postId) throws PostActivityException;
    public PostActivityDTO decreaseCommentCount(Integer postId) throws PostActivityException;
    public Integer deletePostActivity(Integer postId) throws PostActivityException;
}

