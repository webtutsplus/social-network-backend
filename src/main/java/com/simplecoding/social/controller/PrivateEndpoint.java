package com.simplecoding.social.controller;

import com.simplecoding.social.auth.SecurityService;
import com.simplecoding.social.auth.models.UserDto;
import com.simplecoding.social.dtos.PostDto;
import com.simplecoding.social.dto.ChatRoomDTO;
import com.simplecoding.social.exceptions.UnauthorizedException;
import com.simplecoding.social.model.Post;
import com.simplecoding.social.model.User;
import com.simplecoding.social.repo.UserRepository;
import com.simplecoding.social.service.FriendService;
import com.simplecoding.social.service.PostService;
import com.simplecoding.social.service.RoomService;
import com.simplecoding.social.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("private")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PrivateEndpoint {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Autowired
    PostService postService;

    @Autowired
    RoomService roomService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SecurityService securityService;

    @GetMapping("user-details")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDto userDto) {
        User user = userService.getUser(userDto.getEmail());
        return ResponseEntity.ok(user);
    }

    @GetMapping("saveUser")
    public ResponseEntity<UserDto> saveUserInfo(@AuthenticationPrincipal UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.ok(userDto);
    }

    /*@GetMapping("addFriend")
    public ResponseEntity<?> addUserAsFriend(@AuthenticationPrincipal UserDto userDto){
        User user = userRepository.getOne(2);
        UserDto userDto2 = modelMapper.map(user,UserDto.class);
        friendService.saveFriend(userDto,userDto2);
        return ResponseEntity.ok("ok");
    }*/

    @GetMapping("addFriend")
    public ResponseEntity<?> addUser(@RequestParam("friendId") int friendId) throws NullPointerException{
        UserDto currentUser = securityService.getUser();
        //TODO throw exception if friend id is not valid
        friendService.saveFriend(currentUser, friendId);
        // also add a chatroom for the two users
        roomService.saveRoom(currentUser, friendId);
        return ResponseEntity.ok("Friend added successfully");
    }

    @GetMapping("listFriends")
    public ResponseEntity<List<User>> getFriends() {
        List<User> myFriends = friendService.getFriends();
        return new ResponseEntity<>(myFriends, HttpStatus.OK);
    }

    @GetMapping("rooms")
    // get roomId along with friends for currentUsers
    public ResponseEntity<?> getChatRooms() {
        List<ChatRoomDTO> chatRooms = roomService.getChatRooms();
        return new ResponseEntity<>(chatRooms, HttpStatus.OK);
    }

    @PostMapping("addpost")
    public ResponseEntity<?> addPost(@RequestBody Post post) throws NullPointerException {
        UserDto user = securityService.getUser();
        Post savedPost = postService.savePost(user,post.getContent());
        return ResponseEntity.created(URI.create("/private/mypost")).body(savedPost);
    }

    @GetMapping("mypost")
    public ResponseEntity<?> myPosts() throws NullPointerException {
        User user=userService.getUser(securityService.getUser().getEmail());
        List<PostDto> postList = postService.getPostsOfUser(user.getId());
        return ResponseEntity.ok(postList);
    }

    @GetMapping("getRoomName")
    public ResponseEntity<String> getRoom(@RequestParam("friendId") int friendId) {
        UserDto currentUser = securityService.getUser();
        roomService.saveRoom(currentUser, friendId);
        try {
            String room = roomService.getRoom(friendId);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (UnauthorizedException v) {
            return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
        }
    }
}
