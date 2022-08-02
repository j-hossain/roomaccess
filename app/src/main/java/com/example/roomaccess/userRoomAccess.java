package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userRoomAccess extends AppCompatActivity {
    DatabaseReference db_ref;
    String roomID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_room_access);
        db_ref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth fr_auth =FirebaseAuth.getInstance();
        FirebaseUser user = fr_auth.getCurrentUser();
        String userID = user.getUid().toString();
        roomID = getIntent().getExtras().getString("roomID");
        TextView tv_access = findViewById(R.id.tv_access_status);
        db_ref.child("Access").child(roomID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase","Error Getting Access Data",task.getException());
                }
                else{

                    TextView testing = findViewById(R.id.tv_testing);
                    String userstring="-1";
                    for (DataSnapshot user:task.getResult().getChildren()){
                        if(userID.matches(user.getKey().toString())){
                            userstring = user.getKey().toString();
                            break;
                        }
                    }
                    if(userstring.matches("-1")){
                        tv_access.setText("You do not have access to this room");
                    }
                    else{
                        tv_access.setText("You have access to this room");
                        db_ref.child("Rooms").child(roomID).child("Command").setValue("unlock");
                    }
                }
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
}