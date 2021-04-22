package com.simplecoding.social.service;

import com.simplecoding.social.auth.SecurityService;
import com.simplecoding.social.auth.models.UserDto;
import com.simplecoding.social.dto.ChatRoomDTO;
import com.simplecoding.social.exceptions.UnauthorizedException;
import com.simplecoding.social.model.Room;
import com.simplecoding.social.model.User;
import com.simplecoding.social.repo.FriendRepository;
import com.simplecoding.social.repo.RoomRepository;
import com.simplecoding.social.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SecurityService securityService;

    public void saveRoom(UserDto userDto1, int id) throws NullPointerException{

        User user = userRepository.getOne(id);
        UserDto userDto2 = modelMapper.map(user,UserDto.class);

        Room room = new Room();
        room.setCreatedDate(new Date());
        User user1 = userRepository.findUserByEmail(userDto1.getEmail());
        User user2 = userRepository.findUserByEmail(userDto2.getEmail());
        if (user1.getId() > user2.getId()) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }

        if( !(roomRepository.existsByFirstUserAndSecondUser(user1,user2)) && friendRepository.existsByFirstUserAndSecondUser(user1, user2) ){
            room.setFirstUser(user1);
            room.setSecondUser(user2);
            room.setRoomName(UUID.randomUUID().toString());
            roomRepository.save(room);
        }

    }

    public String getRoom (int id) throws UnauthorizedException {

        User user1 = userRepository.findUserByEmail(securityService.getUser().getEmail());
        User user2 = userRepository.getOne(id);
        if (user1.getId() > user2.getId()) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }
        if (!(roomRepository.existsByFirstUserAndSecondUser(user1,user2)))
            throw new UnauthorizedException();
        Room room = roomRepository.findByFirstUserAndSecondUser(user1, user2);
        return room.getRoomName();


    }

    public List<ChatRoomDTO> getChatRooms() {
        UserDto currentUserDto = securityService.getUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        System.out.println("current user: " + currentUser.getId());
        List<Room> rooms = roomRepository.findAllByFirstUserOrSecondUser(currentUser, currentUser);
        List<ChatRoomDTO> chatRoomDTOS = new ArrayList<>();
        for (Room room: rooms) {
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            chatRoomDTO.setMeetingRoom(room.getRoomName());
            if (room.getFirstUser() != currentUser) {
                chatRoomDTO.setUser(room.getFirstUser());
            } else {
                chatRoomDTO.setUser(room.getSecondUser());
            }
            chatRoomDTOS.add(chatRoomDTO);
        }
        return chatRoomDTOS;
    }

}
