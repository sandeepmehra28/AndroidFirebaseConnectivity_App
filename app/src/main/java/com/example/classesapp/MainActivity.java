package com.example.classesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button logout;
    EditText name;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
         logout=findViewById(R.id.logout);
         name = findViewById(R.id.name);
         add = findViewById(R.id.add);
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(MainActivity.this,StartActivity.class);
                 startActivity(intent);
                 finish();
             }
         });

       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String txt_name = name.getText().toString();
               if(txt_name.isEmpty()){
                   Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();

               }else {
                   FirebaseDatabase.getInstance().getReference().child("ClassesAppData").push().setValue(txt_name);
               }
           }
       });
    }
}