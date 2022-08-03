package com.example.roomaccess;

public class model_room {

    String room_name;
    String Status;
    String Command;

    String details;

    public model_room(){}

    public model_room(String room_name, String status, String command, String details) {
        this.room_name = room_name;
        Status = status;
        Command = command;
        this.details = details;
    }


    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getStatus() {
        return Status;
    }


    public String getCommand() {
        return Command;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
