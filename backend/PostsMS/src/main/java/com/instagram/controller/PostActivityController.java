package com.instagram.controller;


import com.instagram.dto.PostActivityDTO;
import com.instagram.exception.PostActivityException;
import com.instagram.service.PostActivityService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("posts-activity")
@CrossOrigin(origins = "*")
public class PostActivityController {

    @Autowired
    private PostActivityService postActivityService;

    @Autowired
    private Environment environment;

    @GetMapping("get/{postid}")
    public ResponseEntity<PostActivityDTO> getPostActivity(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.getPostActivity(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @PostMapping("create/{postid}")
    public ResponseEntity<PostActivityDTO> createPostActivity(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.createPostActivity(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @PostMapping("like/{postid}")
    public ResponseEntity<PostActivityDTO> increaseLikeCount(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.increaseLikeCount(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @PostMapping("unlike/{postid}")
    public ResponseEntity<PostActivityDTO> decreaseLikeCount(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.decreaseLikeCount(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @PostMapping("comment/{postid}")
    public ResponseEntity<PostActivityDTO> increaseCommentCount(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.increaseCommentCount(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @PostMapping("comment/1/{postid}")
    public ResponseEntity<PostActivityDTO> decreaseCommentCount(@PathVariable Integer postid) throws PostActivityException {
        PostActivityDTO postActivityDTO = postActivityService.decreaseCommentCount(postid);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<SuccessInfo> deletePostActivity(@PathVariable Integer postid) throws PostActivityException{
        Integer postActivityId = postActivityService.deletePostActivity(postid);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("POST_ACTIVITY.DELETION_SUCCESS") + " : " + postid);
        return new ResponseEntity<>(successInfo, HttpStatus.CREATED);
    }
}

