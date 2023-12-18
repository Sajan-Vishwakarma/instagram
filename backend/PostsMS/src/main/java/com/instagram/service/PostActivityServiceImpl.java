package com.instagram.service;


import com.instagram.dto.PostActivityDTO;
import com.instagram.entity.PostActivity;
import com.instagram.exception.PostActivityException;
import com.instagram.repository.PostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostActivityServiceImpl implements PostActivityService {

    @Autowired
    private PostActivityRepository postActivityRepository;

    @Override
    public PostActivityDTO getPostActivity(Integer postId) throws PostActivityException {
        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        PostActivity postActivity = op.orElseThrow( ()-> new PostActivityException("POST_ACTIVITY.POST_ID_NOT_FOUND"));
        return new PostActivityDTO(postActivity);
    }

    @Override
    public PostActivityDTO createPostActivity(Integer postId) throws PostActivityException {
        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        if( op.isPresent()){
            throw new PostActivityException("POST_ACTIVITY.POST_ID_FOUND");
        }
        PostActivity postActivity = new PostActivity();
        postActivity.setPostId(postId);
        postActivity.setLikeCount(0);
        postActivity.setCommentCount(0);

        Integer postActivityId = postActivityRepository.save(postActivity).getPostActivityId();
        return new PostActivityDTO(postActivityRepository.findById(postActivityId).get());
    }

    @Override
    public PostActivityDTO increaseLikeCount(Integer postId) throws PostActivityException {

        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        PostActivity postActivity = op.orElseThrow( ()-> new PostActivityException("POST_ACTIVITY.POST_ID_NOT_FOUND"));
        postActivity.setLikeCount( postActivity.getLikeCount() + 1);
        return new PostActivityDTO(postActivity);
    }

    @Override
    public PostActivityDTO decreaseLikeCount(Integer postId) throws PostActivityException {
        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        PostActivity postActivity = op.orElseThrow( ()-> new PostActivityException("POST_ACTIVITY.POST_ID_NOT_FOUND"));
        postActivity.setLikeCount( postActivity.getLikeCount() - 1);
        return new PostActivityDTO(postActivity);
    }

    @Override
    public PostActivityDTO increaseCommentCount(Integer postId) throws PostActivityException {
        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        PostActivity postActivity = op.orElseThrow( ()-> new PostActivityException("POST_ACTIVITY.POST_ID_NOT_FOUND"));
        postActivity.setCommentCount( postActivity.getCommentCount() + 1);
        return new PostActivityDTO(postActivity);
    }

    @Override
    public PostActivityDTO decreaseCommentCount(Integer postId) throws PostActivityException {
        Optional<PostActivity> op = postActivityRepository.findByPostId(postId);
        PostActivity postActivity = op.orElseThrow( ()-> new PostActivityException("POST_ACTIVITY.POST_ID_NOT_FOUND"));
        postActivity.setCommentCount( postActivity.getCommentCount() - 1);
        return new PostActivityDTO(postActivity);
    }

    @Override
    public Integer deletePostActivity(Integer postId) throws PostActivityException {
        return postActivityRepository.deleteByPostId(postId);
    }
}
