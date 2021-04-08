package com.simplecoding.social.repo;

import com.simplecoding.social.model.Room;
import com.simplecoding.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    boolean existsByFirstUserAndSecondUser(User first,User second);

    Room findByFirstUserAndSecondUser(User first,User second);

}
