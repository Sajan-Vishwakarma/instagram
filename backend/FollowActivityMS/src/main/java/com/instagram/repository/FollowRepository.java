package com.instagram.repository;

import com.instagram.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Integer> {

    @Query("Select f.followerId from Follow f where f.followeeId = ?1")
    public List<Integer> findAllFollowers(Integer followeeId);

    @Query("Select f.followeeId from Follow f where f.followerId = ?1")
    public List<Integer> findAllFollowees(Integer followeeId);

    @Query("select f from Follow f where f.followerId = ?1 and f.followeeId = ?2")
    public Optional<Follow> checkConnection(Integer followerId, Integer followeeId);

    @Query("select f.followeeId, COUNT(f.followeeId) as followersCount from Follow f group by f.followeeId order by followersCount desc limit 7")
    public List<Object[]> findTopFollowedUserIds();
}