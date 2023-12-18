package com.instagram.service;

import com.instagram.dto.LikesDTO;
import com.instagram.exception.LikesException;

public interface LikeService {

    public Integer handleLike(LikesDTO like) throws LikesException;
    public Integer handleUnlike(LikesDTO like) throws LikesException;
    public Boolean checklike(LikesDTO like) throws LikesException;
    public Integer deleteAllLikesOfPost(Integer postId) throws LikesException;
}
