package com.instagram.service;


import com.instagram.exception.FollowException;

import java.util.List;

public interface FollowService {

    public List<Integer> getAllFollowers(Integer followeeId) throws FollowException;
    public List<Integer> getAllFollowees(Integer followerId) throws FollowException;
    public List<Integer> getTopFollowedUsers() throws FollowException;
    public Boolean checkConnection(Integer followerId, Integer followeeId) throws FollowException;
    public Boolean followRequest(Integer followerId, Integer followeeId) throws FollowException;
    public Boolean unfollowRequest(Integer followeId, Integer followeeId) throws FollowException;
}
