package com.example.vaksinasishedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference reff;
    FirebaseAuth mAuth;
    User usr;
    EditText txtNama, txtPassword, txtUlangPassword, txtEmail, txtNomorTelpon;
    Button btnRegis;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        txtNama = (EditText) findViewById(R.id.emailLoginField);
        txtPassword = (EditText) findViewById(R.id.passwordLoginField);
        txtUlangPassword = (EditText) findViewById(R.id.ulangPasswordField);
        txtEmail = (EditText) findViewById(R.id.emailField);
        txtNomorTelpon = (EditText) findViewById(R.id.telponField);
        btnRegis = findViewById(R.id.BuatAkun);
        progressBar = findViewById(R.id.progressBar);

        usr = new User();
        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("User");

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftarAkun();
            }
        });
    }

    private void daftarAkun() {
        usr.setNama(txtNama.getText().toString().trim());
        String password = txtPassword.getText().toString().trim();
        String ulangPassword = txtUlangPassword.getText().toString().trim();
        usr.setEmail(txtEmail.getText().toString().trim());
        usr.setNomorTelpon(txtNomorTelpon.getText().toString().trim());
        if (usr.getNama().isEmpty()) {
            txtNama.setError("Nama tidak boleh kosong");
            txtNama.requestFocus();
            return;
        } else if (usr.getNama().length() < 5) {
            txtNama.setError("Minimal 5 karakter");
            txtNama.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            txtPassword.setError("Password tidak boleh kosong");
            txtPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            txtPassword.setError("Password tidak boleh kurang dari 6 karakter");
            txtPassword.requestFocus();
            return;
        }
        if (ulangPassword.isEmpty()) {
            txtUlangPassword.setError("Password tidak boleh kosong");
            txtUlangPassword.requestFocus();
            return;
        } else if (!ulangPassword.matches(password)) {
            txtUlangPassword.setError("Password harus sama");
            txtUlangPassword.requestFocus();
            return;
        }
        if (usr.getEmail().isEmpty()) {
            txtEmail.setError("Email tidak boleh kosong");
            txtEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usr.getEmail()).matches()) {
            txtEmail.setError("Email yang dimasukkan tidak valid");
            txtEmail.requestFocus();
            return;
        }
        if (usr.getNomorTelpon().isEmpty()) {
            txtNomorTelpon.setError("Nomor telpon tidak boleh kosong");
            txtNomorTelpon.requestFocus();
            return;
        } else if (usr.getNomorTelpon().length() < 11) {
            txtNomorTelpon.setError("Nomor yang dimasukkan tidak valid");
            txtNomorTelpon.requestFocus();
            return;
        } else if (usr.getNomorTelpon().charAt(0) != '0' && usr.getNomorTelpon().charAt(1) != '8') {
            txtNomorTelpon.setError("Nomor yang dimasukkan tidak valid");
            txtNomorTelpon.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(usr.getEmail(), password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usr)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Registrasi Gagal2", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "Registrasi Gagal1", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }
}

