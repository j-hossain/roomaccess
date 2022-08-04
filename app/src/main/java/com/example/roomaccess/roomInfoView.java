package com.example.roomaccess;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class roomInfoView extends AppCompatActivity {
    TextView tv_room_name, tv_room_details;
    public model_access thisRoom;
    RelativeLayout adminRoomControlLayout;
    Button btn_edit_room, btn_manage_mod, btn_delete_room,btn_qr_save;
    ImageView qr_code;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    StorageReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info_view);
        tv_room_name = findViewById(R.id.tv_room_name);
        tv_room_details = findViewById(R.id.tv_room_details);
        qr_code = findViewById(R.id.qr_code);
        thisRoom = getIntent().getParcelableExtra("room");
        getQrCode();
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
        btn_qr_save = findViewById(R.id.btn_qr_save);
        btn_qr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference=firebaseStorage.getInstance().getReference();
                ref=storageReference.child(thisRoom.getRoom().getRoomID()+".jpg");

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=uri.toString();
                        downloadFile(roomInfoView.this,thisRoom.getRoom().getRoom_name(),".jpg",DIRECTORY_DOWNLOADS,url);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
            }
        });

    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }

    public void setRoomInfo(){
        tv_room_name.setText(thisRoom.getRoom().getRoom_name());
        tv_room_details.setText(thisRoom.getRoom().getRoom_details());
    }

    public void gotoManangeMods(model_room theRoom){
        Intent manageMods = new Intent(roomInfoView.this,manage_moderators.class);
        manageMods.putExtra("room",theRoom);
        startActivity(manageMods);
    }

    public void getQrCode(){
        String bucket = "https://firebasestorage.googleapis.com/v0/b/room-access-dbf98.appspot.com/o";
        String url = bucket+"/"+thisRoom.getRoom().getRoomID()+".jpg?alt=media";
//        String url = bucket+"/qrCode.jpg?alt=media";
        Picasso.get().load(url).into(qr_code);
    }
}