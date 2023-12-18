package com.instagram.controller;


import com.instagram.dto.PostDTO;
import com.instagram.dto.PostInfoDTO;
import com.instagram.exception.TrendingException;
import com.instagram.service.TrendingService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("trending")
@CrossOrigin(origins = "*")
@RestController
public class TrendingController {

    @Autowired
    private TrendingService trendingService;

    @Autowired
    private Environment environment;

    @PostMapping("extract/tags/{postid}")
    public ResponseEntity<SuccessInfo> extractTags(@PathVariable Integer postid,
            @RequestBody PostDTO postDTO) throws TrendingException {
        Integer success = trendingService.extractTags(postid, postDTO);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("TAGS.EXTRACTION_SUCCESS") + " : " + postid);
        return new ResponseEntity<>(successInfo, HttpStatus.CREATED);
    }

    @GetMapping("search/posts/{hashtag}")
    public ResponseEntity<List<PostInfoDTO>> searchHashtags(@PathVariable String hashtag) throws TrendingException {

        List<Integer> postIds = trendingService.findPostIdsByTagName(hashtag);
        RestTemplate rt = new RestTemplate();
        List<PostInfoDTO> postInfoDTOS = new ArrayList<>();

        for (Integer postId : postIds) {
            PostInfoDTO postInfoDTO =  rt.getForObject("http://localhost:4003/posts/post/"+postId,PostInfoDTO.class);
            if( postInfoDTO.getPostDTO().getPrivacy() == 0)
                postInfoDTOS.add(postInfoDTO);
        }
        return new ResponseEntity<>(postInfoDTOS, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<SuccessInfo> deleteTagsByPostId(@PathVariable Integer postid) throws TrendingException {

        Integer deletedCount = trendingService.deleteTagsByPostId(postid);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("TAGS.DELETION_SUCCESS") + " : " + postid);
        return new ResponseEntity<>(successInfo, HttpStatus.CREATED);
    }

}
