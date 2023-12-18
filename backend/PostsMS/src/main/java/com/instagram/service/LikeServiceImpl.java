package com.instagram.service;


import com.instagram.dto.LikesDTO;
import com.instagram.entity.Like;
import com.instagram.exception.LikesException;
import com.instagram.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LikeServiceImpl implements LikeService{

    @Autowired
    private LikesRepository likesRepository;

    @Override
    public Integer handleLike(LikesDTO likeDTO) throws LikesException {

        Optional<Like> op = likesRepository.findLike(likeDTO.getPostId(), likeDTO.getUserId());
        if( op.isPresent() ){
            throw new LikesException("LIKE.ALREADY_LIKED");
        }

        Like like = new Like();
        like.setUserId(likeDTO.getUserId());
        like.setPostId(likeDTO.getPostId());
        likesRepository.save(like);
        return  1;
    }

    @Override
    public Integer handleUnlike(LikesDTO likeDTO) throws LikesException {
        Optional<Like> op = likesRepository.findLike(likeDTO.getPostId(), likeDTO.getUserId());
        Like like =  op.orElseThrow( () ->  new LikesException("LIKE.ALREADY_UNLIKED") );
        like.setUserId(likeDTO.getUserId());
        like.setPostId(likeDTO.getPostId());
        likesRepository.delete(like);
        return 0;
    }

    @Override
    public Boolean checklike(LikesDTO likeDTO) throws LikesException {
        Optional<Like> op = likesRepository.findLike(likeDTO.getPostId(), likeDTO.getUserId());
        if( op.isPresent()) return true;
        return false;
    }

    @Override
    public Integer deleteAllLikesOfPost(Integer postId) throws LikesException {
        return likesRepository.deleteAllByPostId(postId);
    }
}
