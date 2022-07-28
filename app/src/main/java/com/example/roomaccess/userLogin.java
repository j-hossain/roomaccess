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

public class userLogin extends AppCompatActivity {
    TextView tv_reg_link;
    EditText ed_log_email,ed_log_pass;
    Button btn_login;
    FirebaseAuth fr_auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // taking the elements from layout
        tv_reg_link = findViewById(R.id.tv_reg_link);
        ed_log_email = findViewById(R.id.ed_log_email);
        ed_log_pass = findViewById(R.id.ed_log_pass);
        btn_login = findViewById(R.id.btn_login);
        fr_auth = FirebaseAuth.getInstance();

        // to go to the Registration page
        tv_reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userLogin.this,userRegister.class);
                startActivity(i);
                finish();
            }
        });

        //When the login button is clicked
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // taking the values on the text fields
                String log_email = ed_log_email.getText().toString();
                String log_pass = ed_log_pass.getText().toString();

                // no field should be empty
                if(log_pass.isEmpty() || log_email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"You must enter all values",Toast.LENGTH_LONG).show();
                    return;
                }
                // firebase authentication needed
                fr_auth = FirebaseAuth.getInstance();
                fr_auth.signInWithEmailAndPassword(log_email,log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login unsucessfull...",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login successfull...",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(userLogin.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // in on start method checking if
        // the user is already sign in.
        FirebaseUser user = fr_auth.getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(),user.getUid().toString(),Toast.LENGTH_LONG).show();
            // if the user is not null then we are
            // opening a main activity on below line.
            Intent i = new Intent(userLogin.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}