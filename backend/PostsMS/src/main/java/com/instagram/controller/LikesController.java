package com.instagram.controller;


import com.instagram.dto.LikeInfo;
import com.instagram.dto.LikesDTO;
import com.instagram.dto.PostActivityDTO;
import com.instagram.exception.LikesException;
import com.instagram.service.LikeService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("likes")
@CrossOrigin(origins = "*")
public class LikesController {

    @Autowired
    private Environment environment;

    @Autowired
    private LikeService likeService;

    @PostMapping("handle-like")
    public ResponseEntity<PostActivityDTO> handleLike(@RequestBody LikesDTO likesDTO) throws LikesException {

        RestTemplate rt = new RestTemplate();
        PostActivityDTO postActivityDTO = rt.postForObject("http://localhost:4003/posts-activity/like/" + likesDTO.getPostId(),
                null, PostActivityDTO.class);
        likeService.handleLike(likesDTO);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }


    @PostMapping("handle-unlike")
    public ResponseEntity<PostActivityDTO> handleUnlike(@RequestBody LikesDTO likesDTO) throws LikesException {
        RestTemplate rt = new RestTemplate();
        PostActivityDTO postActivityDTO = rt.postForObject("http://localhost:4003/posts-activity/unlike/" + likesDTO.getPostId(),
                null, PostActivityDTO.class);
        likeService.handleUnlike(likesDTO);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @GetMapping("check-like/{postid}/{userid}")
    public ResponseEntity<LikeInfo> checkLike(@PathVariable Integer postid,
                                              @PathVariable Integer userid) throws LikesException {

        LikesDTO likesDTO = new LikesDTO();
        likesDTO.setPostId(postid); likesDTO.setUserId(userid);

        Boolean likedOrNot = likeService.checklike(likesDTO);
        LikeInfo likeInfo = new LikeInfo();
        likeInfo.setDidLiked(likedOrNot);
        likeInfo.setUserId(likesDTO.getUserId());
        likeInfo.setPostId(likesDTO.getPostId());
        return new ResponseEntity<>(likeInfo, HttpStatus.OK);
    }


    @DeleteMapping("delete/{postid}")
    ResponseEntity<SuccessInfo> deleteAllLikesOfPost(@PathVariable Integer postid) throws LikesException {
        Integer count = likeService.deleteAllLikesOfPost(postid);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("LIKE.DELETE_ALL") + " : " + count);
        return new ResponseEntity<>(successInfo, HttpStatus.OK);
    }
}