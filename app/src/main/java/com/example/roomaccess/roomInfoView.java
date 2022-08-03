package com.example.roomaccess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class roomInfoView extends AppCompatActivity {
    TextView tv_room_name, tv_room_details;
    model_room thisRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info_view);
        tv_room_name = findViewById(R.id.tv_room_name);
        tv_room_details = findViewById(R.id.tv_room_details);
        thisRoom = getIntent().getParcelableExtra("room");
        setRoomInfo();
    }

    public void setRoomInfo(){
        tv_room_name.setText(thisRoom.getRoom_name());
        tv_room_details.setText(thisRoom.getRoom_details());
    }
}