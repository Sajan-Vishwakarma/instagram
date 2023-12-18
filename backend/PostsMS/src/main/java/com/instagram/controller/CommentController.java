package com.instagram.controller;


import com.instagram.dto.*;
import com.instagram.exception.CommentsException;
import com.instagram.service.CommentsService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private Environment environment;

    @PostMapping("add")
    public ResponseEntity<PostActivityDTO> addComment(@RequestBody CommentsDTO commentsDTO) throws CommentsException {
        Integer cid = commentsService.addComment(commentsDTO);
        RestTemplate rt = new RestTemplate();
        PostActivityDTO postActivityDTO = rt.postForObject("http://localhost:4003/posts-activity/comment/" + commentsDTO.getPostId(),
                null, PostActivityDTO.class);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }

    @GetMapping("get/{postid}")
    public ResponseEntity<List<CommentsInfo>> getAllCommentsOnAPost(@PathVariable Integer postid) throws CommentsException {
        List<CommentsDTO> commentsDTOS = commentsService.getAllCommentsOnAPost(postid);

        List<CommentsInfo> commentsInfos = new ArrayList<>();
        RestTemplate rt = new RestTemplate();
        commentsDTOS.forEach(comment -> {
            UserDTO userDTO = rt.getForObject("http://localhost:4001/users/" + comment.getUserId(), UserDTO.class);
            ImageDTO imageDTO = rt.getForObject("http://localhost:4001/images/image/" + userDTO.getProfileImgId(), ImageDTO.class);
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUsername(userDTO.getUsername());
            userInfoDTO.setUserId(userDTO.getUserId());
            userInfoDTO.setProfileImageURL(imageDTO.getImg_url());
            CommentsInfo commentsInfo = new CommentsInfo();
            commentsInfo.setUserInfo(userInfoDTO);
            commentsInfo.setCommentsDTO(comment);
            commentsInfos.add(commentsInfo);
        });

        return new ResponseEntity<>(commentsInfos, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<SuccessInfo> deleteAllComments(@PathVariable Integer postid) throws CommentsException {
        Integer deleteCount = commentsService.deleteAllComments(postid);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("COMMENT.DELETED") + " : " + deleteCount);
        return new ResponseEntity<>(successInfo, HttpStatus.OK);
    }

    @DeleteMapping("delete/comment/{cid}")
    public ResponseEntity<PostActivityDTO> deleteSingleComment(@PathVariable Integer cid) throws CommentsException {
        Integer postId = commentsService.deleteComment(cid);

        RestTemplate rt = new RestTemplate();
        PostActivityDTO postActivityDTO =  rt.postForObject("http://localhost:4003/posts-activity/comment/1/"+postId,null,PostActivityDTO.class);
        return new ResponseEntity<>(postActivityDTO, HttpStatus.OK);
    }
}
