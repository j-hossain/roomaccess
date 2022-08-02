package com.example.roomaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Button btn_qrscan, btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_qrscan = (Button) findViewById(R.id.btn_qrscan);
        btn_logout = (Button) findViewById(R.id.btn_logout);
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
    }
}