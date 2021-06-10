package com.example.vaksinasishedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity{

    User usr;
    EditText txtNama, txtPassword;
    Button btnLogin;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        txtNama = findViewById(R.id.usernameField);
        txtPassword = findViewById(R.id.passwordField);
        btnLogin = findViewById(R.id.masukButton);
//        progressBar = findViewById(R.id.progressBar);
        usr = new User();
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin(){
        String username = txtNama.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (username.isEmpty()){
            txtNama.setError("Username Salah");
            txtNama.requestFocus();
            return;
        }
        if (username.isEmpty()){
            txtNama.setError("Password Salah");
            txtNama.requestFocus();
            return;
        }
//        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //ets
                            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}