package com.example.vaksinasishedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailJadwalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_jadwal);
    }
}