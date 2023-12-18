package com.instagram.controller;

import com.instagram.dto.ConnectionDTO;
import com.instagram.dto.FollowDTO;
import com.instagram.dto.UserDTO;
import com.instagram.exception.FollowException;
import com.instagram.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("follows")
@CrossOrigin(origins = "*")
@RestController
public class FollowsController {

    @Autowired
    private Environment environment;

    @Autowired
    private FollowService followService;

    @GetMapping("followers/{username}")
    public ResponseEntity<List<UserDTO>> getAllFollowers(@PathVariable String username) throws FollowException {

        RestTemplate rt = new RestTemplate();
        UserDTO userDTO;

        try {
            userDTO = rt.getForObject("http://localhost:4001/users/user/" + username, UserDTO.class);
        } catch (Exception e) {
            throw new FollowException("Error occured during calling AuthenticationMS: " + e.getMessage());
        }
        List<Integer> followerIds = followService.getAllFollowers(userDTO.getUserId());
        List<UserDTO> followers = followerIds
                .stream()
                .map(userID -> {
                    UserDTO u = rt.getForObject("http://localhost:4001/users/" + userID, UserDTO.class);
                    return u;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("followees/{username}")
    public ResponseEntity<List<UserDTO>> getAllFollowees(@PathVariable String username) throws FollowException {
        RestTemplate rt = new RestTemplate();
        UserDTO userDTO = rt.getForObject("http://localhost:4001/users/user/" + username, UserDTO.class);

        List<Integer> followerIds = followService.getAllFollowees(userDTO.getUserId());
        List<UserDTO> followers = followerIds
                .stream()
                .map(userID -> {
                    UserDTO u = rt.getForObject("http://localhost:4001/users/" + userID, UserDTO.class);
                    return u;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("top-followed")
    public ResponseEntity<List<UserDTO>> getTopFollowedUsers() throws FollowException {
        List<Integer> topFollowedIds = followService.getTopFollowedUsers();
        RestTemplate rt = new RestTemplate();
        List<UserDTO> topFollowedUsers = topFollowedIds
                .stream()
                .map(userID -> {
                    UserDTO u = rt.getForObject("http://localhost:4001/users/" + userID, UserDTO.class);
                    return u;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(topFollowedUsers, HttpStatus.OK);
    }

    @PostMapping("check-connection")
    public ResponseEntity<FollowDTO> checkConnection(@RequestBody ConnectionDTO connectionDTO) throws FollowException {
        RestTemplate rt = new RestTemplate();
        UserDTO follower = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFollowerName(), UserDTO.class);
        UserDTO followee = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFolloweeName(), UserDTO.class);

        if (follower == null || followee == null)
            throw new FollowException("follow.connection_absent");

        Boolean isConnected = followService.checkConnection(follower.getUserId(), followee.getUserId());
        FollowDTO f = new FollowDTO();
        f.setFollowerId(follower.getUserId());
        f.setFolloweeId(followee.getUserId());
        f.setIsConnected(isConnected);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @PostMapping("handle-follow")
    public ResponseEntity<FollowDTO> followRequest(@RequestBody ConnectionDTO connectionDTO) throws FollowException {
        RestTemplate rt = new RestTemplate();
        UserDTO follower = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFollowerName(), UserDTO.class);
        UserDTO followee = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFolloweeName(), UserDTO.class);

        if (follower == null || followee == null)
            throw new FollowException("follow.connection_absent");

        Boolean madeConnection = followService.followRequest(follower.getUserId(), followee.getUserId());
        FollowDTO f = new FollowDTO();
        f.setFollowerId(follower.getUserId());
        f.setFolloweeId(followee.getUserId());
        f.setIsConnected(madeConnection);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @PostMapping("handle-unfollow")
    public ResponseEntity<FollowDTO> unfollowRequest(@RequestBody ConnectionDTO connectionDTO) throws FollowException {
        RestTemplate rt = new RestTemplate();
        UserDTO follower = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFollowerName(), UserDTO.class);
        UserDTO followee = rt.getForObject("http://localhost:4001/users/user/" + connectionDTO.getFolloweeName(), UserDTO.class);

        if (follower == null || followee == null)
            throw new FollowException("follow.connection_absent");

        Boolean isConnected = followService.unfollowRequest(follower.getUserId(), followee.getUserId());
        FollowDTO f = new FollowDTO();
        f.setFollowerId(follower.getUserId());
        f.setFolloweeId(followee.getUserId());
        f.setIsConnected(isConnected);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }
}
