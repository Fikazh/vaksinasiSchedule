package com.example.vaksinasishedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AkunActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reff;
    private FirebaseStorage storage;
    private StorageReference storageReff;
    private String userID;
    private Button ganti_sandi;
    private Uri imgUri;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_akun);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference();
        userID = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReff = storage.getReference();
        profilePic = findViewById(R.id.fotoProfile);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihFoto();
            }
        });

        final TextView txtNama = findViewById(R.id.namaAkunField);
        final TextView txtKodeVaksin = findViewById(R.id.kodeVaksinField);
        final TextView txtEmail = findViewById(R.id.emailAkunField);
        final TextView txtKTP = findViewById(R.id.ktpAkunField);
        final TextView txtKK = findViewById(R.id.kkAkunField);
        final TextView txtTelpon = findViewById(R.id.telponAkunField);
        final TextView txtAlamat = findViewById(R.id.alamatAkunField);

        ganti_sandi = findViewById(R.id.GantiSandi);
        ganti_sandi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BottomSheetDialog ganti_sandi_panel = new BottomSheetDialog(AkunActivity.this);
                ganti_sandi_panel.setContentView(R.layout.akun_ganti_password);
                ganti_sandi_panel.show();
                TempatGantiPassword(ganti_sandi_panel);

            }
        });

        ProgressBar progressBar1 = findViewById(R.id.progressBar3);
        ProgressBar progressBar2 = findViewById(R.id.progressBar5);
        progressBar1.setVisibility(View.VISIBLE);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User kdPasien = snapshot.child("JadwalVaksin").child(userID).getValue(User.class);
                if (snapshot.child("JadwalVaksin").hasChild(userID)) {
                    txtKodeVaksin.setText(kdPasien.kodePasien);
                } else {
                    txtKodeVaksin.setText("-");
                }

                User userProfile = snapshot.child("User").child(userID).getValue(User.class);
                if (userProfile != null) {
                    progressBar2.setVisibility(View.VISIBLE);
                    txtNama.setText(userProfile.nama);
                    txtEmail.setText(userProfile.email);
                    txtTelpon.setText(userProfile.nomorTelpon);
                    txtKTP.setText(userProfile.nomorKTP);
                    txtKK.setText(userProfile.nomorKK);
                    txtAlamat.setText(userProfile.alamat);
                    StorageReference picRef = storageReff.child("images/profile/" + userID);
                    picRef.getBytes(1024 * 1024)
                            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    profilePic.setImageBitmap(bitmap);
                                    progressBar2.setVisibility(View.INVISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar2.setVisibility(View.INVISIBLE);
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            progressBar2.setVisibility(View.INVISIBLE);
                        }
                    });
                    progressBar1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_LONG).show();
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

    private void TempatGantiPassword(BottomSheetDialog bsd) {
        TextView txtPasswordLama = bsd.findViewById(R.id.passwordLamaField);
        TextView txtPasswordBaru = bsd.findViewById(R.id.passwordBaruField);
        TextView txtPasswordUlangBaru = bsd.findViewById(R.id.passwordUlangBaruField);
        Button btnconfirm = bsd.findViewById(R.id.confirmButton);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    if (txtPasswordLama.getText().toString().trim().isEmpty()) {
                        txtPasswordLama.setError("Password tidak boleh kosong");
                        txtPasswordLama.requestFocus();
                        return;
                    }
                    if (txtPasswordBaru.getText().toString().trim().isEmpty()) {
                        txtPasswordBaru.setError("Password tidak boleh kosong");
                        txtPasswordBaru.requestFocus();
                        return;
                    } else if (txtPasswordBaru.getText().toString().trim().length() < 6) {
                        txtPasswordBaru.setError("Password tidak boleh kurang dari 6 karakter");
                        txtPasswordBaru.requestFocus();
                        return;
                    }
                    if (txtPasswordUlangBaru.getText().toString().trim().isEmpty()) {
                        txtPasswordUlangBaru.setError("Password tidak boleh kosong");
                        txtPasswordUlangBaru.requestFocus();
                        return;
                    } else if (!txtPasswordUlangBaru.getText().toString().trim().matches(txtPasswordBaru.getText().toString().trim())) {
                        txtPasswordUlangBaru.setError("Password tidak sama");
                        txtPasswordUlangBaru.requestFocus();
                        return;
                    } else {
                        GantiPassword(txtPasswordLama.getText().toString().trim(), txtPasswordBaru.getText().toString().trim());
                        bsd.hide();

                    }
                }
            }
        });
    }

    private void GantiPassword(String passwordLama, String passwordBaru) {
        ProgressBar progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), passwordLama);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                user.updatePassword(passwordBaru).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Password berhasil diganti", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Password gagal diganti", Toast.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void PilihFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            profilePic.setImageURI(imgUri);
            UploadPic();
        }
    }

    private void UploadPic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();
        StorageReference picRef = storageReff.child("images/profile/" + userID);
        picRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Upload Gagal", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }
}