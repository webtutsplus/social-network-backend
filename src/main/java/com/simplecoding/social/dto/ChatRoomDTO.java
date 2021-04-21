package com.simplecoding.social.dto;

import com.simplecoding.social.model.User;

public class ChatRoomDTO {
    User user;
    String meetingRoom;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
}
