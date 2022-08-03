package com.example.roomaccess;

import android.os.Parcel;
import android.os.Parcelable;

public class model_access implements Parcelable {
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

    protected model_access(Parcel in) {
        room = in.readParcelable(model_room.class.getClassLoader());
        access_count_left = in.readString();
        access_type = in.readString();
        access_valid_till = in.readString();
    }

    public static final Creator<model_access> CREATOR = new Creator<model_access>() {
        @Override
        public model_access createFromParcel(Parcel in) {
            return new model_access(in);
        }

        @Override
        public model_access[] newArray(int size) {
            return new model_access[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(room, flags);
        dest.writeString(access_count_left);
        dest.writeString(access_type);
        dest.writeString(access_valid_till);
    }
}
