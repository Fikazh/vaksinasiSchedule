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

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin, btnRegister;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.emailLoginField);
        txtPassword = findViewById(R.id.passwordLoginField);
        btnLogin = findViewById(R.id.masukButton);
        btnRegister = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void userLogin() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            txtEmail.setError("Email tidak boleh kosong");
            txtEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Email tidak valid");
            txtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            txtPassword.setError("Password tidak boleh kosong");
            txtPassword.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Gagal\nEmail atau Password salah\nSilahkan coba lagi", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }
    }
}