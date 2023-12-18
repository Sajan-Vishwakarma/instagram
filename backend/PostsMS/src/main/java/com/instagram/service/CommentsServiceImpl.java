package com.instagram.service;


import com.instagram.dto.CommentsDTO;
import com.instagram.entity.Comment;
import com.instagram.exception.CommentsException;
import com.instagram.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService{

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public Integer addComment(CommentsDTO commentsDTO) throws CommentsException {
        Comment c = new Comment();
        c.setComment(commentsDTO.getComment());
        c.setUserId(commentsDTO.getUserId());
        c.setPostId(commentsDTO.getPostId());
        return commentsRepository.save(c).getCid();
    }

    @Override
    public List<CommentsDTO> getAllCommentsOnAPost(Integer postId) throws CommentsException {
        List<Comment> comments = commentsRepository.findAllByPostId(postId);
        List<CommentsDTO> commentsDTOS = comments.stream()
                .map( com -> {
                    return new CommentsDTO(com);
                })
                .collect(Collectors.toList());
        return commentsDTOS;
    }

    @Override
    public Integer deleteComment(Integer cid) throws CommentsException {
        Optional<Comment> op = commentsRepository.findById(cid);
        Comment c = op.orElseThrow(() -> new CommentsException("COMMENT.NOT_FOUND"));
        commentsRepository.deleteByCid(cid);
        return c.getPostId();
    }

    @Override
    public Integer deleteAllComments(Integer postId) throws CommentsException {
        return commentsRepository.deleteAllByPostId(postId);
    }
}
