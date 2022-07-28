package com.example.roomaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userRegister extends AppCompatActivity {

        EditText ed_email , ed_pass , ed_con_pass;
        TextView tv_login_link;
        Button btn_register;
        FirebaseAuth fr_auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
//        getting the text fields from xml
        ed_email = findViewById(R.id.ed_email);
        ed_pass = findViewById(R.id.ed_password);
        ed_con_pass = findViewById(R.id.ed_password_confirm);
        btn_register = findViewById(R.id.btn_register);
        tv_login_link = findViewById(R.id.tv_login_link);

        // to go to the login page
        tv_login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userRegister.this,userLogin.class);
                startActivity(i);
                finish();
            }
        });

        //When the registration button is clicked
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        taking the values on the text fields
                String reg_email = ed_email.getText().toString();
                String reg_pass = ed_pass.getText().toString();
                String reg_con_pas = ed_con_pass.getText().toString();
                // no field should be empty
                if(reg_email.isEmpty() || reg_pass.isEmpty() || reg_con_pas.isEmpty()){
                    Toast.makeText(getApplicationContext(),"You must enter all values",Toast.LENGTH_LONG).show();
                    return;
                }
                // checking if the pass and confirm pass match
                if(!reg_pass.matches(reg_con_pas)){
                    Toast.makeText(getApplicationContext(),"The passwords do not match",Toast.LENGTH_LONG).show();
                    return;
                }

                // firebase authentication needed
                fr_auth = FirebaseAuth.getInstance();
                fr_auth.createUserWithEmailAndPassword(reg_email,reg_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration unsucessfull...",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"registration successfull",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // in on start method checking if
//        // the user is already sign in.
//        FirebaseUser user = fr_auth.getCurrentUser();
//        if (user != null) {
//            Toast.makeText(getApplicationContext(),user.getUid().toString(),Toast.LENGTH_LONG).show();
//            // if the user is not null then we are
//            // opening a main activity on below line.
//            Intent i = new Intent(userRegister.this, MainActivity.class);
//            startActivity(i);
////            this.finish();
//        }
//    }
}
