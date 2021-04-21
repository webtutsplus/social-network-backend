package com.simplecoding.social.controller;

import com.simplecoding.social.dtos.PostDto;
import com.simplecoding.social.model.Post;
import com.simplecoding.social.model.User;
import com.simplecoding.social.service.PostService;
import com.simplecoding.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicEndpoints {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping("test")
    ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> postList = postService.getAllPost();
        return ResponseEntity.ok(postList);
    }
    @GetMapping("/{userId}/posts")
    public ResponseEntity<?> getPostofUser(@PathVariable Integer userId){
        List<PostDto> postList = postService.getPostsOfUser(userId);
        return ResponseEntity.ok(postList);
    }

}
