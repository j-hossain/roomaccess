package com.example.roomaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Starter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        FirebaseAuth fr_auth = FirebaseAuth.getInstance();
        FirebaseUser user = fr_auth.getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(),user.getUid().toString(),Toast.LENGTH_LONG).show();
            // if the user is not null then we are
            // opening a main activity on below line.
            Intent i = new Intent(Starter.this, Home.class);
            startActivity(i);
            this.finish();
        }
        else{
            Intent i = new Intent(Starter.this, userLogin.class);
            startActivity(i);
            this.finish();
        }
    }
}