package com.instagram.service;


import com.instagram.entity.Follow;
import com.instagram.exception.FollowException;
import com.instagram.repository.FollowRepository;
import com.instagram.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Override
    public List<Integer> getAllFollowers(Integer followeeId) throws FollowException {
        List<Integer> followers = followRepository.findAllFollowers(followeeId);
        return followers;
    }

    @Override
    public List<Integer> getAllFollowees(Integer followerId) throws FollowException{
        List<Integer> followers = followRepository.findAllFollowees(followerId);
        return followers;
    }

    @Override
    public List<Integer> getTopFollowedUsers() throws FollowException {
        List<Object[]> obj = followRepository.findTopFollowedUserIds();
        List<Integer> userIds = new ArrayList<>();
        for(Object[] o: obj){
            Integer a = (Integer) o[0];
            userIds.add(a);
        }
        return userIds;
    }

    @Override
    public Boolean checkConnection(Integer followerId, Integer followeeId) throws FollowException{
        Optional<Follow> op = followRepository.checkConnection(followerId, followeeId);
        if( op.isPresent() )
            return  true;
        return false;
    }

    @Override
    public Boolean followRequest(Integer followerId, Integer followeeId) throws FollowException{
        Optional<Follow> op = followRepository.checkConnection(followerId, followeeId);
        if( op.isPresent() )
            throw new FollowException("follow.follow_exists");

        Follow f = new Follow();
        f.setFollowerId(followerId);
        f.setFolloweeId(followeeId);
        followRepository.save(f);
        return true;
    }

    @Override
    public Boolean unfollowRequest(Integer followerId, Integer followeeId) throws FollowException {
        Optional<Follow> op = followRepository.checkConnection(followerId, followeeId);
        Follow f = op.orElseThrow( () -> new FollowException("follow.unfollow_exists"));
        followRepository.delete(f);
        return false;
    }

}
