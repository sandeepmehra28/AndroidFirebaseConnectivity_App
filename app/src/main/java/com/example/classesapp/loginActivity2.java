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

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class loginActivity2 extends AppCompatActivity {
    private EditText email,password;
    private Button login;
    FirebaseAuth auth;
    FloatingActionButton fab;
    Button btngoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
       email=findViewById(R.id.email2);
       password=findViewById(R.id.password2);
       login=findViewById(R.id.button2);
       auth=FirebaseAuth.getInstance();
       fab=findViewById(R.id.floatingbtn);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(loginActivity2.this,"your are clicked in floating button",Toast.LENGTH_SHORT).show();
           }
       });
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String txt_email = email.getText().toString();
               String txt_pass = password.getText().toString();
               if (!isConnectedToInternet()) {
                   Toast.makeText(loginActivity2.this, "Please connect your internet", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(txt_email.isEmpty()||txt_pass.isEmpty()){
                   Toast.makeText(loginActivity2.this,"please fill all the fields!",Toast.LENGTH_SHORT).show();
               }else if(!isValidEmail(txt_email)){
                   Toast.makeText(loginActivity2.this,"please enter a valid email!",Toast.LENGTH_SHORT).show();
               }else {
                  loginUser2(txt_email,txt_pass);
               }

           }
       });

    }

    private boolean isConnectedToInternet() {
        android.net.ConnectivityManager cm =
                (android.net.ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void loginUser2(String txtEmail, String txtPass) {
        auth.signInWithEmailAndPassword(txtEmail,txtPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(loginActivity2.this,"Logged in!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity2.this,MainActivity.class);
                startActivity(intent);
               finish();
            }
        });
    }

    private boolean isValidEmail(String txtEmail) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return Pattern.matches(regex, txtEmail);
    }
}