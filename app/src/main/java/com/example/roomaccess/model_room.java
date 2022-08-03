package com.example.roomaccess;

import android.os.Parcel;
import android.os.Parcelable;

public class model_room implements Parcelable {

    String room_name;
    String Status;
    String Command;
    String room_details;
    String roomID;


    public model_room(){}

    public model_room(String room_name, String status, String command, String room_details) {
        this.room_name = room_name;
        Status = status;
        Command = command;
        this.room_details = room_details;
    }


    protected model_room(Parcel in) {
        room_name = in.readString();
        Status = in.readString();
        Command = in.readString();
        room_details = in.readString();
        roomID = in.readString();
    }

    public static final Creator<model_room> CREATOR = new Creator<model_room>() {
        @Override
        public model_room createFromParcel(Parcel in) {
            return new model_room(in);
        }

        @Override
        public model_room[] newArray(int size) {
            return new model_room[size];
        }
    };

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

    public String getRoom_details() {
        return room_details;
    }

    public void setRoom_details(String room_details) {
        this.room_details = room_details;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(room_name);
        dest.writeString(Status);
        dest.writeString(Command);
        dest.writeString(room_details);
        dest.writeString(roomID);
    }
}
