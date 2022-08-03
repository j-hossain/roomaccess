package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRooms extends AppCompatActivity {

    public ListView lv_rooms;
    ArrayList<model_my_room> rooms;
    roomViewAdapter adapter;
    FloatingActionButton btn_add_room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);
        lv_rooms = findViewById(R.id.lv_rooms);
        btn_add_room = findViewById(R.id.btn_add_room);
        rooms = new ArrayList<>();
        adapter = new roomViewAdapter(getApplicationContext(),rooms);
        lv_rooms.setAdapter(adapter);

        //first of all current user er id nicchi
        FirebaseAuth fr_auth =FirebaseAuth.getInstance();
        FirebaseUser user = fr_auth.getCurrentUser();
        String userID = user.getUid().toString();
        //taking the database
        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();


//        Shob access fetch korbo
        db_ref.child("Access").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    rooms.clear();
                    for(DataSnapshot access_rooms: snapshot.getChildren()){
                        for (DataSnapshot access: access_rooms.getChildren()) {
                            if(access.getKey().matches(userID)){
                                model_access the_access = access.getValue(model_access.class);
                                db_ref.child("Rooms").child(access_rooms.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        model_room the_room = snapshot.getValue(model_room.class);
                                        the_room.setRoomID(access_rooms.getKey().toString());
                                        the_access.setRoom(the_room);
                                        rooms.add(new model_my_room(the_room,the_access));
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // jokhonn room add korte chaibe
        btn_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_room = new Intent(MyRooms.this,act_createRoom.class);
                startActivity(create_room);
            }
        });

        // setting what will happen when clicked on a room item
        lv_rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoRoomView(rooms.get(position).getAccess());
            }
        });
    }

    public void gotoRoomView(model_access thisRoom){
        Intent roomView = new Intent(this, roomInfoView.class);
        roomView.putExtra("room",thisRoom);
        startActivity(roomView);
    }
}
