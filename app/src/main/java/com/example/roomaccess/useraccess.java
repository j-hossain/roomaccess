package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class useraccess extends AppCompatActivity {
    DatabaseReference db_ref;
    TextView tv_roomName;
    Button btn_unlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccess);
        db_ref = FirebaseDatabase.getInstance().getReference();
        tv_roomName = findViewById(R.id.tv_roomName);
        btn_unlock = findViewById(R.id.btn_unlock);
        String roomID = getIntent().getExtras().getString("roomID");
        tv_roomName.setText(roomID);
        db_ref.child("Rooms").child(roomID).child("room_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String roomName = String.valueOf(task.getResult().getValue());
                    tv_roomName.setText(roomName);
                }
            }
        });
        btn_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_username = findViewById(R.id.inp_username);
                EditText ed_pass = findViewById(R.id.inp_pass);
                String username = ed_username.getText().toString();
                String pass = ed_pass.getText().toString();
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
                                if(username.matches(user.getKey().toString())){
                                    userstring = user.getKey().toString();
                                    break;
                                }
                            }
                            if(userstring.matches("-1")){
                                testing.setText("This user does not have access");
                            }
                            else{
                                db_ref.child("Users").child(userstring).child("user_pass").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            String fr_pass = task.getResult().getValue().toString();
                                            if(fr_pass.matches(pass)){
                                                testing.setText("Door is Opening");
                                                db_ref.child("Rooms").child(roomID).child("Command").setValue("unlock");
                                            }
                                            else{

                                                testing.setText("The pass is wrong!! " +pass+" "+fr_pass);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }
}