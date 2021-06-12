package com.example.vaksinasishedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AkunActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reff;
    private String userID;
    private Button ganti_sandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_akun);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        final TextView txtNama = findViewById(R.id.namaAkunField);
        final TextView txtEmail = findViewById(R.id.emailAkunField);
        final TextView txtKtp = findViewById(R.id.ktpAkunField);
        final TextView txtTelpon = findViewById(R.id.telponAkunField);
        final TextView txtAlamat = findViewById(R.id.alamatAkunField);

        ganti_sandi = findViewById(R.id.GantiSandi);
        ganti_sandi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BottomSheetDialog ganti_sandi_panel = new BottomSheetDialog(AkunActivity.this);
                ganti_sandi_panel.setContentView(R.layout.akun_ganti_password);
                ganti_sandi_panel.show();
            }
        });

        ProgressBar progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        reff.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    txtNama.setText(userProfile.nama);
                    txtEmail.setText(userProfile.email);
                    txtTelpon.setText(userProfile.nomorTelpon);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan",Toast.LENGTH_LONG).show();
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