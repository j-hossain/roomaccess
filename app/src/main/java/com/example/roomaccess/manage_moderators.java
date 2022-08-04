package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class manage_moderators extends AppCompatActivity {
    public ListView lv_mods;
    static ArrayList<model_user> mods;
    static modViewAdapter modAdapter;
    static model_room thisRoom;
    static DatabaseReference db_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_moderators);
        lv_mods = findViewById(R.id.lv_moderators);
        thisRoom = getIntent().getParcelableExtra("room");
        mods = new ArrayList<model_user>();
        modAdapter = new modViewAdapter(getApplicationContext(),mods);
        lv_mods.setAdapter(modAdapter);

        //taking the database
        db_ref = FirebaseDatabase.getInstance().getReference();

//        ei room er Shob access fetch korbo
        db_ref.child("Access").child(thisRoom.getRoomID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mods.clear();
                    for(DataSnapshot users: snapshot.getChildren()){
                        if(users.child("access_type").getValue().toString().matches("moderator")){
                            db_ref.child("Users").child(users.getKey().toString()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        model_user us = snapshot.getValue(model_user.class);
                                        mods.add(us);
                                        modAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button btn_add_mod = findViewById(R.id.btn_add_mod);
        btn_add_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_mod_email = findViewById(R.id.ed_mod_email);
                String mod_email = ed_mod_email.getText().toString();
                db_ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot user:snapshot.getChildren()){
                                model_user us = user.getValue(model_user.class);
                                us.setUserID(user.getKey().toString());
                                if(us.getEmail().toString().matches(mod_email)){
                                    // add to database
                                    HashMap<String, String> access = new HashMap<String, String>();
                                    access.put("access_count_left","inf");
                                    access.put("access_type","moderator");
                                    access.put("access_valid_till","forever");
                                    db_ref.child("Access").child(thisRoom.getRoomID().toString()).child(us.getUserID().toString()).setValue(access);
                                    mods.add(us);
                                    modAdapter.notifyDataSetChanged();
                                    return;
                                }
                            }
                            Toast.makeText(getApplicationContext(),"User Not Found...",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public static void deleteMod(int position){
        model_user de_mod = mods.get(position);
        db_ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot user:snapshot.getChildren()){
                        if(user.child("Email").getValue().toString().matches(de_mod.getEmail())){
                            mods.remove(position);
                            modAdapter.notifyDataSetChanged();
                            db_ref.child("Access").child(thisRoom.getRoomID()).child(user.getKey().toString()).removeValue();
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