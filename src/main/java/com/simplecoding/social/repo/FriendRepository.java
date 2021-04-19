package com.simplecoding.social.repo;

import com.simplecoding.social.model.Friend;
import com.simplecoding.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer> {

    boolean existsByFirstUserAndSecondUser(User first,User second);

    List<Friend> findByFirstUser(User user);

}
