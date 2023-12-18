package com.instagram.service;

import com.instagram.dto.CommentsDTO;
import com.instagram.exception.CommentsException;

import java.util.List;

public interface CommentsService {

    public Integer addComment(CommentsDTO commentsDTO) throws CommentsException;
    public List<CommentsDTO> getAllCommentsOnAPost(Integer postId) throws CommentsException;
    public Integer deleteComment(Integer cid) throws CommentsException;
    public Integer deleteAllComments(Integer postId) throws CommentsException;
}