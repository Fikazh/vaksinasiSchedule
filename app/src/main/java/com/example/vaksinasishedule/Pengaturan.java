package com.example.vaksinasishedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Pengaturan extends AppCompatActivity {

    Button tentang_kami;
    Button ulas_kami;
    Button pertanyaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        getSupportActionBar().hide();

        tentang_kami = findViewById(R.id.tentang_kami_button);
        tentang_kami.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BottomSheetDialog tentang_kami_teks = new BottomSheetDialog(Pengaturan.this);
                tentang_kami_teks.setContentView(R.layout.pengaturan_tentang_kami);
                tentang_kami_teks.show();
            }
        });

        ulas_kami = findViewById(R.id.ulas_kami_button);
        ulas_kami.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BottomSheetDialog ulas_kami_teks = new BottomSheetDialog(Pengaturan.this);
                ulas_kami_teks.setContentView(R.layout.pengaturan_ulas_kami);
                ulas_kami_teks.show();
            }
        });

        pertanyaan = findViewById(R.id.ajukan_pertanyaan_button);
        pertanyaan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BottomSheetDialog pertanyaan_teks = new BottomSheetDialog(Pengaturan.this);
                pertanyaan_teks.setContentView(R.layout.pengaturan_ajukan_pertanyaan);
                pertanyaan_teks.show();
            }
        });
    }
}