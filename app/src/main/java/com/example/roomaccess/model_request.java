package com.example.roomaccess;

public class model_request {
    String user;
    String message;
    String room;

    public model_request(String user, String message, String room) {
        this.user = user;
        this.message = message;
        this.room = room;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
