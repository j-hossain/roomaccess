package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class userRoomAccess extends AppCompatActivity {
    DatabaseReference db_ref;
    String roomID, userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_room_access);
        db_ref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth fr_auth =FirebaseAuth.getInstance();
        FirebaseUser user = fr_auth.getCurrentUser();
        userID = user.getUid().toString();
        roomID = getIntent().getExtras().getString("roomID");
        TextView tv_access = findViewById(R.id.tv_access_status);
        db_ref.child("Access").child(roomID).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    model_access access = snapshot.getValue(model_access.class);
                    if(access.getAccess_type().matches("owner") || access.getAccess_type().matches("moderator") || access.getAccess_type().matches("parmanent")){
                        accessGranted();
                    }
                    else if(access.getAccess_type().matches("limited_count")){
                        int count_left = Integer.parseInt(access.getAccess_count_left().toString());
                        if(count_left>0){
                            count_left--;
                            if(count_left==0){
                                db_ref.child("Access").child(roomID).child(userID).removeValue();
                            }
                            else{
                                db_ref.child("Access").child(roomID).child(userID).child("access_count_left").setValue(count_left);
                            }
                            accessGranted();
                        }
                        else{
                            accessRejected("Your access has expired");
                        }
                    }
                    else if(access.getAccess_type().matches("limited_time")){
                        if(isAccessExpired(access.getAccess_valid_till().toString())){
                            accessGranted();
                        }
                        else{
                            accessRejected("Your access has expired");
                        }
                    }
                }
                else{
                    checkRoom();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userRoomAccess.this,Home.class));
            }
        });
    }

    public void checkRoom(){
        db_ref.child("Rooms").child(roomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    accessRejected("You do not have access to this room");
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Such Room",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(userRoomAccess.this,Home.class));
//                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void accessRejected(String Reason){
        Toast.makeText(getApplicationContext(),Reason,Toast.LENGTH_LONG).show();
    }

    public void accessGranted(){
        Toast.makeText(getApplicationContext(),"Access Granted",Toast.LENGTH_LONG).show();
        db_ref.child("Rooms").child(roomID).child("Command").setValue("unlock");
        Toast.makeText(getApplicationContext(),"Lock Opening",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(userRoomAccess.this,Home.class));
    }
    private boolean isAccessExpired(String date){
        boolean isExpired=false;
        Date expiredDate = stringToDate(date, "EEE MMM d HH:mm:ss zz yyyy");
        if (new Date().after(expiredDate)) isExpired=true;

        return isExpired;
    }

    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}