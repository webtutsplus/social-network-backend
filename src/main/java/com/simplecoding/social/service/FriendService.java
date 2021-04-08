package com.simplecoding.social.service;

import com.simplecoding.social.auth.SecurityService;
import com.simplecoding.social.auth.models.UserDto;
import com.simplecoding.social.model.Friend;
import com.simplecoding.social.model.User;
import com.simplecoding.social.repo.FriendRepository;
import com.simplecoding.social.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SecurityService securityService;

    public void saveFriend(UserDto userDto1, int id) throws NullPointerException{

        User user = userRepository.getOne(id);
        UserDto userDto2 = modelMapper.map(user,UserDto.class);

        Friend friend = new Friend();
        friend.setCreatedDate(new Date());
        User user1 = userRepository.findUserByEmail(userDto1.getEmail());
        User user2 = userRepository.findUserByEmail(userDto2.getEmail());

        Friend friendViceVersa = new Friend();
        friendViceVersa.setCreatedDate(new Date());
        User firstUser = userRepository.findUserByEmail(userDto2.getEmail());
        User secondUser = userRepository.findUserByEmail(userDto1.getEmail());

        if( !(friendRepository.existsByFirstUserAndSecondUser(user1,user2)) ){
            friend.setFirstUser(user1);
            friend.setSecondUser(user2);

            friendViceVersa.setFirstUser(firstUser);
            friendViceVersa.setSecondUser(secondUser);

            friendRepository.save(friend);
            friendRepository.save(friendViceVersa);
        }

    }

    public List<User> getFriends(){

        UserDto currentUserDto = securityService.getUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        List<Friend> friends = friendRepository.findByFirstUser(currentUser);
        List<User> friendUsers = new ArrayList<User>();

        for (Friend friend : friends) {
            friendUsers.add(userRepository.findUserById(friend.getSecondUser().getId()));
        }
        return friendUsers;

    }

}
