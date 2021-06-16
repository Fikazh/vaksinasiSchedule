package com.example.vaksinasishedule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DetailJadwalActivity extends AppCompatActivity {
    private String kodeTempat;
    private FirebaseUser user;
    private DatabaseReference reff;
    private FirebaseStorage storage;
    private StorageReference storageReff;
    private String userID;
    private Button ganti_sandi;
    private Uri imgUri;
    private ImageView profilePic;
    private ArrayList<String> tglList = new ArrayList<String>();
    private ArrayList<Gelombang> gList = new ArrayList<Gelombang>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_jadwal);
        kodeTempat = getIntent().getStringExtra("coba");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReff = storage.getReference();
        profilePic = findViewById(R.id.fotoProfile);

        final TextView txtKodeTempat = findViewById(R.id.kodeTempatDetil);
        final TextView txtNamaTempat = findViewById(R.id.namaLokasiField);
        final TextView txtAlamat = findViewById(R.id.alamatDetilFild);
        final TextView txtTanggal = findViewById(R.id.tanggalVaksinField);
        final TextView txtG1 = findViewById(R.id.g1Field);
        final TextView txtG2 = findViewById(R.id.g2Field);
        final ImageView imgTempat = findViewById(R.id.fotoLokasiField);


        reff.child("Tempat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TempatJadwal tJ = snapshot.child(kodeTempat).getValue(TempatJadwal.class);
                txtKodeTempat.setText(kodeTempat);
                txtNamaTempat.setText(tJ.namaRS);
                txtAlamat.setText(tJ.alamatRS);
                Glide.with(getApplicationContext()).load(tJ.uriImage).into(imgTempat);
                reff.child("Gelombang/" + kodeTempat).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                        for (DataSnapshot ps : dsnapshot.getChildren()) {
                            tglList.add(ps.getKey());
                            Gelombang gTemp = ps.getValue(Gelombang.class);
                            gList.add(gTemp);
                        }
                        txtTanggal.setText(tglList.get(0));
                        txtG1.setText(gList.get(0).getG1());
                        txtG2.setText(gList.get(0).getG2());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navbar);

        bottomNavigationView.setSelectedItemId(R.id.jadwal);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.akun:
                        startActivity(new Intent(getApplicationContext(), AkunActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.jadwal:
                        startActivity(new Intent(getApplicationContext(), Jadwal.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    case R.id.pengaturan:
                        startActivity(new Intent(getApplicationContext(), PengaturanActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                }
                return false;
            }
        });
    }
}