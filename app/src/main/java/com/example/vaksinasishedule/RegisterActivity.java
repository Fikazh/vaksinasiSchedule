package com.example.vaksinasishedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        Button btn_regis = findViewById(R.id.BuatAkun);
        btn_regis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BuatAkun:
                final EditText txt = (EditText) findViewById(R.id.usernameField);
                String name = (String) txt.getText().toString();
                writeUser("001", name);
                Intent intet = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intet);
                break;

        }
    }

    public void writeUser(String userId, String username){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User usr = new User(username);
        mDatabase.child("Users").child(userId).setValue(usr);
    }

    public class User{
        public String name;
        public User(){
            this.name = null;
        }
        public User(String name){
            this.name = name;
        }
    }

}