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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
      EditText emailText,passwordText;
      Button login;
      FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        emailText = findViewById(R.id.email2);
        passwordText = findViewById(R.id.password2);
        login = findViewById(R.id.login2);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                if (!isConnectedToInternet()) {
                    Toast.makeText(LoginActivity.this, "Please connect your internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                String txt_email = emailText.getText().toString();
                String text_password = passwordText.getText().toString();
                loginUser(txt_email, text_password);
            }
        });
    }
    private boolean isConnectedToInternet() {
        android.net.ConnectivityManager cm =
                (android.net.ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    private void loginUser(String txtEmail, String textPassword) {
        mAuth.signInWithEmailAndPassword(txtEmail, textPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"Logged in!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}