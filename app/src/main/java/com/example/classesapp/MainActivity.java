package com.example.classesapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button logout;
    EditText name;
    Button add,showMap;
    ListView listView;
    Uri imageUri;
    static final  int IMAGE_REQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Compiler EdgeToEdge = null;
        EdgeToEdge.enable();
        setContentView(R.layout.activity_main);
         logout=findViewById(R.id.logout);
         name = findViewById(R.id.name);
         add = findViewById(R.id.add);
         listView = findViewById(R.id.listView);
         showMap = findViewById(R.id.mapbtn);
         showMap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, MainActivity.class);
                 startActivity(intent);
                 finish();
             }
         });
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
               openImage();
           }
       });


    }

    private void openImage() {
        Intent intent= new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST&&resultCode==RESULT_OK){
            imageUri=data.getData();
            uploadImage();
        }
    }

    String getFileExtention(Uri uri){
     ContentResolver cr = getContentResolver();
     MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
     return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
 }
    private void uploadImage() {
      final  ProgressDialog pd =new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri!=null){

            StorageReference fileref = FirebaseStorage.getInstance().getReference().child("uploading").child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
           fileref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           String url = uri.toString();
                           Log.d("downloadUrl",url);
                           pd.dismiss();
                           Toast.makeText(MainActivity.this,"Image uploaded successfully!",Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           });
        }
    }
}