package com.example.vaksinasishedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AkunActivity extends AppCompatActivity {

    Button ganti_sandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_akun);
        getSupportActionBar().hide();

        ganti_sandi = findViewById(R.id.GantiSandi);
        ganti_sandi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BottomSheetDialog ganti_sandi_panel = new BottomSheetDialog(AkunActivity.this);
                ganti_sandi_panel.setContentView(R.layout.pengaturan_tentang_kami);
                ganti_sandi_panel.show();
            }
        });
    }
}