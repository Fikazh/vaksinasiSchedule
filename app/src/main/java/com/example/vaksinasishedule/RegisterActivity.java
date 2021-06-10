package com.example.vaksinasishedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference reff;
    FirebaseAuth mAuth;
    User usr;
    EditText txtNama, txtPassword, txtUlangPassword, txtEmail, txtNomorTelpon;
    Button btnRegis;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        txtNama = (EditText) findViewById(R.id.usernameField);
        txtPassword = (EditText) findViewById(R.id.passwordField);
        txtUlangPassword = (EditText) findViewById(R.id.ulangPasswordField);
        txtEmail = (EditText) findViewById(R.id.emailField);
        txtNomorTelpon = (EditText) findViewById(R.id.telponField);
        btnRegis = findViewById(R.id.BuatAkun);
        usr = new User();
        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("User");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getText().toString().trim();
                String ulangPassword = txtUlangPassword.getText().toString().trim();
                if (!password.matches(ulangPassword)){
                    Toast.makeText(getApplicationContext(),"Password Tidak Benar", Toast.LENGTH_SHORT).show();
                    txtPassword.setText("");
                    txtUlangPassword.setText("");
                }else{
                    daftarAkun();
                }
            }
        });
    }

    private void daftarAkun(){
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            usr.setNama(txtNama.getText().toString().trim());
                            usr.setEmail(txtEmail.getText().toString().trim());
                            usr.setNomorTelpon(txtNomorTelpon.getText().toString().trim());
                            reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usr)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Registrasi Gagal2", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(getApplicationContext(), "Registrasi Gagal1", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        Intent intet = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intet);
    }
}

