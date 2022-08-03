package com.example.roomaccess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class roomInfoView extends AppCompatActivity {
    TextView tv_room_name, tv_room_details;
    model_access thisRoom;
    RelativeLayout adminRoomControlLayout;
    Button btn_edit_room, btn_manage_mod, btn_delete_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info_view);
        tv_room_name = findViewById(R.id.tv_room_name);
        tv_room_details = findViewById(R.id.tv_room_details);
        thisRoom = getIntent().getParcelableExtra("room");
        setRoomInfo();
        adminRoomControlLayout = findViewById(R.id.layout_admin_room_control);
        View adminControl = LayoutInflater.from(getApplicationContext()).inflate(R.layout.admin_room_control,adminRoomControlLayout,false);

        //This will be done only when i am the owner of the room,
        // So now we will need the access object
        // only the room object is not enough
        if(thisRoom.getAccess_type().matches("owner")){
            adminRoomControlLayout.addView(adminControl);
        }

        btn_edit_room = adminControl.findViewById(R.id.btn_edit_room);
        btn_manage_mod = adminControl.findViewById(R.id.btn_manage_mod);
        btn_delete_room = adminControl.findViewById(R.id.btn_delete_room);

        btn_edit_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Buttotn cloinbjfn", Toast.LENGTH_SHORT).show();
                // this goes to a new page for editing the room info
            }
        });

        btn_manage_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoManangeMods(thisRoom.getRoom());
            }
        });

    }

    public void setRoomInfo(){
        tv_room_name.setText(thisRoom.getRoom().getRoom_name());
        tv_room_details.setText(thisRoom.getRoom().getRoom_details());
    }

    public void gotoManangeMods(model_room theRoom){

    }
}