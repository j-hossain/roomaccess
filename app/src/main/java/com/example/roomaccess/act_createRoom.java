package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class act_createRoom extends AppCompatActivity {
    EditText ed_room_name, ed_room_details;
    Button btn_add_room;
//    For database reference
    DatabaseReference db_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        // setting the database reference
        db_ref = FirebaseDatabase.getInstance().getReference();
        //getting element from xml
        ed_room_name = findViewById(R.id.ed_room_name);
        ed_room_details = findViewById(R.id.ed_room_details);
        btn_add_room = findViewById(R.id.btn_add_room);

        // when the add room button is clicked
        btn_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String room_name = ed_room_name.getText().toString();
                String room_details = ed_room_details.getText().toString();
                createRoom(room_name,room_details);
            }
        });
    }

    public void createRoom(String name, String details){
        HashMap<String, String> room = new HashMap<String, String>();
        room.put("room_name",name);
        room.put("room_details",details);
        room.put("Command","lock");
        room.put("Status","closed");
        String roomID = db_ref.child("Rooms").push().getKey();
        //generating the QR code
        MultiFormatWriter mf = new MultiFormatWriter();
        try {
            BitMatrix bt = mf.encode(roomID.toString(), BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder br = new BarcodeEncoder();
            Bitmap bm = br.createBitmap(bt);

            // now saving it to firebase
            StorageReference st_ref = FirebaseStorage.getInstance().getReference();
            StorageReference mountainsRef = st_ref.child(roomID+".jpg");
            StorageReference mountainImagesRef = st_ref.child("image/"+roomID+".jpg");
            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }catch (Exception e){}
        db_ref.child("Rooms").child(roomID).setValue(room);
        createAccess(roomID);
    }

    public void createAccess(String roomID){
        // getting the user id of the currently logged in user, since he is the owner
        FirebaseAuth fr_auth = FirebaseAuth.getInstance();
        FirebaseUser cur_user = fr_auth.getCurrentUser();
        String userID = cur_user.getUid().toString();
        HashMap<String, String> access_details = new HashMap<String, String>();
        access_details.put("access_count_left","inf");
        access_details.put("access_type","owner");
        access_details.put("access_valid_till","forever");
        db_ref.child("Access").child(roomID).child(userID).setValue(access_details);
        backToMyRooms();
    }

    public  void backToMyRooms(){
        Intent myRooms = new Intent(act_createRoom.this,MyRooms.class);
        startActivity(myRooms);
    }
}