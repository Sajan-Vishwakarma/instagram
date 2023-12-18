package com.instagram.controller;


import com.instagram.dto.*;
import com.instagram.exception.PostsException;
import com.instagram.service.MediaService;
import com.instagram.service.PostsService;
import com.instagram.utility.SuccessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("posts")
@CrossOrigin(origins = "*")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Environment environment;

    @PostMapping("create")
    public ResponseEntity<Integer> createPost(@RequestBody PostDTO postDTO) {
        Integer postId = postsService.createPost(postDTO);
        RestTemplate rt = new RestTemplate();
        rt.postForObject("http://localhost:4003/posts-activity/create/" + postId, null, PostActivityDTO.class);
        rt.postForObject("http://localhost:4004/trending/extract/tags/" + postId, postDTO, SuccessInfo.class);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping("post/{postid}")
    public ResponseEntity<PostInfoDTO> getPostDetails(@PathVariable Integer postid) throws PostsException {

        PostDTO postDTO = postsService.getPostDetails(postid);
        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO.setPostDTO(postDTO);

        RestTemplate rt = new RestTemplate();

        UserDTO userDTO = rt.getForObject("http://localhost:4001/users/" + postDTO.getUserId(), UserDTO.class);
        ImageDTO imageDTO = rt.getForObject("http://localhost:4001/images/image/" + userDTO.getProfileImgId(), ImageDTO.class);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(userDTO.getUsername());
        userInfoDTO.setUserId(userDTO.getUserId());
        userInfoDTO.setProfileImageURL(imageDTO.getImg_url());
        postInfoDTO.setUserInfoDTO(userInfoDTO);

        ResponseEntity<List<MediaDTO>> resMediaDTOs = rt.exchange("http://localhost:4003/media/get/" + postid, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MediaDTO>>() {
                });
        List<MediaDTO> mediaDTOS = resMediaDTOs.getBody();
        List<MediaInfo> mediaInfos = mediaDTOS.stream()
                .map(media -> {
                    ImageDTO image = rt.getForObject("http://localhost:4001/images/image/" + media.getImgId(), ImageDTO.class);
                    MediaInfo mediaInfo = new MediaInfo();
                    mediaInfo.setImgId(media.getImgId());
                    mediaInfo.setPlace(media.getPlace());
                    mediaInfo.setImgurl(image.getImg_url());
                    return mediaInfo;
                })
                .collect(Collectors.toList());

        postInfoDTO.setMediaInfos(mediaInfos);
        return new ResponseEntity<>(postInfoDTO, HttpStatus.OK);
    }

    @GetMapping("get/user/{userid}")
    public ResponseEntity<List<PostInfoDTO>> getAllPostsOfUser(@PathVariable Integer userid) throws PostsException {
        List<PostDTO> postIds = postsService.getPostIdsMadeByUser(userid);
        RestTemplate rt = new RestTemplate();
        List<PostInfoDTO> postsOfUser = new ArrayList<>();
        postIds.forEach(post -> {
            PostInfoDTO postInfoDTO = rt.getForObject("http://localhost:4003/posts/post/" + post.getPostId(), PostInfoDTO.class);
            postsOfUser.add(postInfoDTO);
        });
        return new ResponseEntity<>(postsOfUser, HttpStatus.OK);
    }

    @GetMapping("public/all")
    public ResponseEntity<List<PostInfoDTO>> getAllPublicPosts() {
        List<PostDTO> publicPostIds = postsService.getPublicPostIds();

        RestTemplate rt = new RestTemplate();
        List<PostInfoDTO> postsOfUser = publicPostIds.stream()
                .map(post -> {
                    PostInfoDTO postInfoDTO = rt.getForObject("http://localhost:4003/posts/post/" + post.getPostId(), PostInfoDTO.class);
                    return postInfoDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(postsOfUser, HttpStatus.OK);
    }

    @GetMapping("public/users/{userid}")
    public ResponseEntity<List<PostInfoDTO>> getAllPublicPostsOfUser(@PathVariable Integer userid)
            throws PostsException {
        List<PostDTO> publicPostIdsOfUser = postsService.getPublicPostIdsMadeByUser(userid);

        RestTemplate rt = new RestTemplate();
        List<PostInfoDTO> postsOfUser = publicPostIdsOfUser.stream()
                .map(post -> {
                    PostInfoDTO postInfoDTO = rt.getForObject("http://localhost:4003/posts/post/" + post.getPostId(), PostInfoDTO.class);
                    return postInfoDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(postsOfUser, HttpStatus.OK);
    }

    @GetMapping("user/followee/{username}")
    public ResponseEntity<List<PostInfoDTO>> getFeedForUserByFollowees(@PathVariable String username) {

        List<PostInfoDTO> feedForUser = new ArrayList<PostInfoDTO>();

        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<UserDTO>> resUserDTOs = rt.exchange("http://localhost:4002/follows/followees/" + username, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserDTO>>() {
                });

        List<UserDTO> userDTOS = resUserDTOs.getBody();

        userDTOS.forEach(user -> {
            ResponseEntity<List<PostInfoDTO>> postInfoDTOs = rt.exchange("http://localhost:4003/posts/get/user  /" + user.getUserId(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PostInfoDTO>>() {
                    });
            feedForUser.addAll(postInfoDTOs.getBody());
        });

        return new ResponseEntity<>(feedForUser, HttpStatus.OK);
    }

    @DeleteMapping("delete/{postid}")
    public ResponseEntity<SuccessInfo> deletePost(@PathVariable Integer postid) {

        RestTemplate rt = new RestTemplate();
        rt.delete("http://localhost:4003/likes/delete/" + postid);
        rt.delete("http://localhost:4003/comments/delete/" + postid);
        rt.delete("http://localhost:4003/media/delete/" + postid);
        rt.delete("http://localhost:4003/posts-activity/delete/" + postid);
        rt.delete("http://localhost:4004/trending/delete/" + postid);

        Integer deleteCount = postsService.deletePost(postid);
        SuccessInfo successInfo = new SuccessInfo();
        successInfo.setSuccessCode(HttpStatus.CREATED.value());
        successInfo.setSuccessMessage(environment.getProperty("POST.DELETION_SUCCESS") + " : " + postid);
        return new ResponseEntity<>(successInfo, HttpStatus.CREATED);
    }

}