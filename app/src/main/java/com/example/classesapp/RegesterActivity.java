package com.example.classesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegesterActivity extends AppCompatActivity {
      EditText emailText,passwordText;
       Button register;
     FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regester);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        register = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = emailText.getText().toString();
                String textPassword = passwordText.getText().toString();
                if(TextUtils.isEmpty(textEmail)||TextUtils.isEmpty(textPassword)){
                    Toast.makeText(RegesterActivity.this, "please fill all the fields", Toast.LENGTH_SHORT).show();
                }else if(textPassword.length()<6){
                     Toast.makeText(RegesterActivity.this, "password is too short", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(textEmail,textPassword);
                }
            }
        });

    }

    private void registerUser(String textEmail, String textPassword) {
     mAuth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 Toast.makeText(RegesterActivity.this, "Register created", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(RegesterActivity.this,MainActivity.class);
                 startActivity(intent);
                 finish();
             }else{
                 Toast.makeText(RegesterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
             }
         }
     });
    }
}