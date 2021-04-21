package com.simplecoding.social.service;

import com.simplecoding.social.auth.SecurityService;
import com.simplecoding.social.auth.models.UserDto;
import com.simplecoding.social.dtos.PostDto;
import com.simplecoding.social.model.Post;
import com.simplecoding.social.model.User;
import com.simplecoding.social.repo.PostRepository;
import com.simplecoding.social.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SecurityService securityService;

    public Post savePost(UserDto userDto, String content){
        Post post = new Post();
        User user = userRepository.findUserByEmail(userDto.getEmail());
        post.setUser(user);
        post.setContent(content);
        return postRepository.save(post);
    }

    public List<PostDto> getPostsOfUser(Integer userId){
        List<Post> postList= postRepository.findPostByUser(userRepository.findUserById(userId));
        List<PostDto> postDtoList= new ArrayList<>();
        for (Post post :postList) {
            postDtoList.add(modelMapper.map(post,PostDto.class));
        }
        return postDtoList;
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

}
