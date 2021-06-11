package com.example.vaksinasishedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
                ganti_sandi_panel.setContentView(R.layout.akun_ganti_password);
                ganti_sandi_panel.show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navbar);

        bottomNavigationView.setSelectedItemId(R.id.akun);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.akun:
                        return true;
                    case R.id.jadwal:
                        startActivity(new Intent(getApplicationContext(), Jadwal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.pengaturan:
                        startActivity(new Intent(getApplicationContext(), PengaturanActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}