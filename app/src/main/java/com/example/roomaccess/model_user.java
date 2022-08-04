package com.example.roomaccess;

import android.os.Parcel;
import android.os.Parcelable;

public class model_user implements Parcelable {

    String Name;
    String Email;
    public String userID;

    public model_user(String name, String email, String userID) {
        Name = name;
        Email = email;
        this.userID = userID;
    }

    public model_user(){}
    public model_user(String Email, String Name) {
        this.Name = Name;
        this.Email = Email;
    }

    protected model_user(Parcel in) {
        Name = in.readString();
        Email = in.readString();
        userID = in.readString();
    }

    public static final Creator<model_user> CREATOR = new Creator<model_user>() {
        @Override
        public model_user createFromParcel(Parcel in) {
            return new model_user(in);
        }

        @Override
        public model_user[] newArray(int size) {
            return new model_user[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Email);
        dest.writeString(userID);
    }
}
