package com.example.roomaccess;

import android.os.Parcel;
import android.os.Parcelable;

public class model_my_room implements Parcelable {
    model_room room;
    model_access access;

    public model_my_room(){}

    public model_my_room(model_room room, model_access access) {
        this.room = room;
        this.access = access;
    }

    protected model_my_room(Parcel in) {
    }

    public static final Creator<model_my_room> CREATOR = new Creator<model_my_room>() {
        @Override
        public model_my_room createFromParcel(Parcel in) {
            return new model_my_room(in);
        }

        @Override
        public model_my_room[] newArray(int size) {
            return new model_my_room[size];
        }
    };

    public model_room getRoom() {
        return room;
    }

    public model_access getAccess() {
        return access;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
