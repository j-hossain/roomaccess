package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    Button btn_qrscan, btn_logout, btn_my_rooms, btn_requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_qrscan = (Button) findViewById(R.id.btn_qrscan);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_my_rooms = (Button) findViewById(R.id.btn_my_rooms);
        btn_requests = findViewById(R.id.btn_access_requests);

        getUserName();

        btn_qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),act_qrscanner.class));
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fr_auth = FirebaseAuth.getInstance();
                fr_auth.signOut();
                Intent i = new Intent(Home.this,userLogin.class);
                startActivity(i);
                finish();
            }
        });

        btn_my_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,MyRooms.class);
                startActivity(i);
            }
        });

        btn_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getUserName(){
        FirebaseAuth fr_auth = FirebaseAuth.getInstance();
        FirebaseUser user = fr_auth.getCurrentUser();

        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
        db_ref.child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    model_user us = snapshot.getValue(model_user.class);
                    us.setUserID(user.getUid());
                    TextView tv_user_name = findViewById(R.id.tv_user_name);
                    tv_user_name.setText(us.getName().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}