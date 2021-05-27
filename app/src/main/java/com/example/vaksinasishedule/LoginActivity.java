package com.example.vaksinasishedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        Button btn_regis = findViewById(R.id.daftarakun);
        btn_regis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daftarakun:
                Intent intet = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intet);
                break;

        }
    }

}