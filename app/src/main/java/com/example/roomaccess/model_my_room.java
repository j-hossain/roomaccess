package com.example.roomaccess;

public class model_my_room {
    model_room room;
    model_access access;

    public model_my_room(){}

    public model_my_room(model_room room, model_access access) {
        this.room = room;
        this.access = access;
    }

    public model_room getRoom() {
        return room;
    }

    public model_access getAccess() {
        return access;
    }
}
