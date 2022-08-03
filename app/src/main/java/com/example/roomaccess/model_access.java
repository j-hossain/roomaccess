package com.example.roomaccess;

public class model_access {
    public model_room room;
    public model_user user;
    public String access_count_left;
    public String access_type;
    public String access_valid_till;

//    Since we are not getting the rooms and user information directly from the database, this is not included in the constructor
    public model_access(){} // blank constructor na dile firebase theke direct data neoa jay na , jani na keno

    public model_access(String access_count_left, String access_type, String access_valid_till) {
        this.access_count_left = access_count_left;
        this.access_type = access_type;
        this.access_valid_till = access_valid_till;
    }

    public model_room getRoom() {
        return room;
    }

    public void setRoom(model_room room) {
        this.room = room;
    }

    public model_user getUser() {
        return user;
    }

    public void setUser(model_user user) {
        this.user = user;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getAccess_count_left() {
        return access_count_left;
    }

    public void setAccess_count_left(String access_count_left) {
        this.access_count_left = access_count_left;
    }

    public String getAccess_valid_till() {
        return access_valid_till;
    }

    public void setAccess_valid_till(String access_valid_till) {
        this.access_valid_till = access_valid_till;
    }
}
