package com.example.vaksinasishedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RecyclerActivity extends AppCompatActivity {
RecyclerView recyclerView;
RecycleAdapter recycleAdapter;
String kodeRS[] = {"T001","T002","T003","T004","T005"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recycler);

    recyclerView=findViewById(R.id.recycleview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recycleAdapter = new RecycleAdapter(this,kodeRS);
    recyclerView.setAdapter(recycleAdapter);
    }
}