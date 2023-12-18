package com.instagram.service;

import com.instagram.dto.PostDTO;
import com.instagram.exception.TrendingException;

import java.util.List;

public interface TrendingService {

    public Integer extractTags(Integer postD, PostDTO postDTO) throws TrendingException;
    public List<Integer> findPostIdsByTagName(String tagname) throws TrendingException;
    public Integer deleteTagsByPostId(Integer postId) throws TrendingException;
}

