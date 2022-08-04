package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Allrequests extends AppCompatActivity {
    public ListView lv_mods;
    static ArrayList<model_request> mods;
    static requestAdapter modAdapter;
    static model_user thisUser;
    static DatabaseReference db_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrequests);
        lv_mods = findViewById(R.id.lv_moderators);
        mods = new ArrayList<model_request>();
        modAdapter = new requestAdapter(getApplicationContext(),mods);
        lv_mods.setAdapter(modAdapter);


        //taking the database
        db_ref = FirebaseDatabase.getInstance().getReference();
        setUser();

        db_ref.child("Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot room: snapshot.getChildren()){
                        String roomID = room.getKey().toString();
                        db_ref.child("Access").child(roomID).child(thisUser.getUserID().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    if(snapshot.child("access_type").getValue().toString().matches("owner") || snapshot.child("access_type").getValue().toString().matches("moderator")){
                                        for(DataSnapshot reqs: room.getChildren()){
                                            String userID = reqs.getKey().toString();
                                            String message = reqs.child("message").getValue().toString();
                                            model_request rq = new model_request(userID,message,roomID);
                                            mods.add(rq);
                                            modAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUser(){
        FirebaseAuth fr_auth = FirebaseAuth.getInstance();
        FirebaseUser us = fr_auth.getCurrentUser();
        db_ref.child("Users").child(us.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    thisUser = snapshot.getValue(model_user.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}