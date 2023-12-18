package com.instagram.controller;


import com.instagram.dto.MediaDTO;
import com.instagram.service.MediaService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("media")
@CrossOrigin(origins = "*")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Environment environment;

    @PostMapping("add/{postid}")
    public ResponseEntity<SuccessInfo> addMedia(@PathVariable Integer postid, @RequestBody List<Integer> imgIds){
        Integer count = mediaService.addMedia(postid, imgIds);
        SuccessInfo successInfo = new SuccessInfo();
       successInfo.setSuccessCode(HttpStatus.CREATED.value());
       successInfo.setSuccessMessage(environment.getProperty("MEDIA.ADD_ALL_SUCCESS")+" : " + postid);
       return new ResponseEntity<>(successInfo,HttpStatus.CREATED);
    }

    @GetMapping("get/{postid}")
    public ResponseEntity<List<MediaDTO>> getMediaOfPost(@PathVariable Integer postid){
        List<MediaDTO> mediaDTOS = mediaService.getMediaOfPost(postid);
        return new ResponseEntity<>(mediaDTOS, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<SuccessInfo> deleteMediaOfPost(@PathVariable Integer postid){
        Integer count = mediaService.deleteMediaOfPost(postid);
        SuccessInfo successInfo = new SuccessInfo();
       successInfo.setSuccessCode(HttpStatus.CREATED.value());
       successInfo.setSuccessMessage(environment.getProperty("MEDIA.DELETE_ALL_SUCCESS")+" : " + postid);
       return new ResponseEntity<>(successInfo,HttpStatus.CREATED);
    }

}