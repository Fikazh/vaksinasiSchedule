package com.example.vaksinasishedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Jadwal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        getSupportActionBar().hide();
    }
}