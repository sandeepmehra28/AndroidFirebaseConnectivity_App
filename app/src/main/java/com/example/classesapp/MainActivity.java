package com.example.classesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button logout;
    EditText name;
    Button add;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
         logout=findViewById(R.id.logout);
         name = findViewById(R.id.name);
         add = findViewById(R.id.add);
         listView = findViewById(R.id.listView);
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
                   FirebaseDatabase.getInstance().getReference().child("languages").child("lang").push().setValue(txt_name);
               }
           }
       });
        ArrayList<String>  list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("languages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());

                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}