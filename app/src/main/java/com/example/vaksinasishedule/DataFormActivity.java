package com.example.vaksinasishedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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

public class DataFormActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference reff;
    private FirebaseStorage storage;
    private StorageReference storageReff;
    private String userID, namaFile;
    private TextView txtKTP, txtKK, txtPenyakit, txtAlamat;
    private Uri img;
    private ImageView picKK, picKTP, picSuratSehat;
    private boolean picKKcek = false, picKTPcek = false, picSuratSehatcek = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data_form);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        ImageView imagebnr = findViewById(R.id.image_banner);

        imagebnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReff = storage.getReference();
        picKTP = findViewById(R.id.ktpUpload);
        picKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihFoto("KTP");
            }
        });
        picKK = findViewById(R.id.kkUpload);
        picKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihFoto("KK");
            }
        });
        picSuratSehat = findViewById(R.id.suratSehatUpload);
        picSuratSehat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihFoto("Surat Sehat");
            }
        });

        Button btnKirim = findViewById(R.id.kirim_button);
        txtKTP = findViewById(R.id.ktpFormField);
        txtKK = findViewById(R.id.kkFormField);
        txtAlamat = findViewById(R.id.alamatFormField);
        txtPenyakit = findViewById(R.id.riwayatPenyakitField);
        picKTP = findViewById(R.id.ktpUpload);

        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference();
        Pasien pasien = new Pasien();
        ProgressBar progressBar = findViewById(R.id.progressBar4);


        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String nKTP = txtKTP.getText().toString().trim();
                String nKK = txtKK.getText().toString().trim();
                String alamat = txtAlamat.getText().toString().trim();
                String rPenyakit = txtPenyakit.getText().toString().trim();
                reff.child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User usr = snapshot.getValue(User.class);
                        if (usr != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        if (picKTPcek == false) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Silahkan upload foto KTP", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (nKTP.isEmpty()) {
                            txtKTP.setError("Nomor KTP tidak boleh kosong");
                            txtKTP.requestFocus();
                            return;
                        } else if (nKTP.length() < 16) {
                            txtKTP.setError("Nomor KTP salah, Silahkan masukkan kembali");
                            txtKTP.requestFocus();
                            return;
                        }if (picKKcek == false) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Silahkan upload foto KK", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (nKK.isEmpty()) {
                            txtKK.setError("Nomor KK tidak boleh kosong");
                            txtKK.requestFocus();
                            return;
                        } else if (nKK.length() < 16) {
                            txtKK.setError("Nomor KK salah, Silahkan masukkan kembali");
                            txtKK.requestFocus();
                            return;
                        }if (picSuratSehatcek == false) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Silahkan upload foto Surat Keterangan Sehat", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (rPenyakit.isEmpty()) {
                            txtPenyakit.setError("Riwayat penyakit tidak boleh kosong");
                            txtPenyakit.requestFocus();
                            return;
                        } else {
                            pasien.setNama(usr.getNama());
                            pasien.setNomorKTP(nKTP);
                            pasien.setNomorKK(nKK);
                            pasien.setAlamat(alamat);
                            pasien.setRiwayatPenyakit(rPenyakit);
                            reff.child("Pasien").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(pasien)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            usr.setNomorKTP(nKTP);
                                            usr.setNomorKK(nKK);
                                            usr.setAlamat(alamat);
                                            usr.setRiwayatPenyakit(rPenyakit);
                                            reff.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usr);
                                            Toast.makeText(getApplicationContext(), "Pengajuan pasien berhasil", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Pengajuan pasien gagal", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    private void PilihFoto(String namaFile) {
        this.namaFile = namaFile;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            img = data.getData();
            if (namaFile.equals("KTP")) {
                picKTP.setImageURI(img);
                UploadPic(img, "KTP");
            } else if (namaFile.equals("KK")) {
                picKK.setImageURI(img);
                UploadPic(img, "KK");
            } else if (namaFile.equals("Surat Sehat")) {
                picSuratSehat.setImageURI(img);
                UploadPic(img, "Surat Sehat");
            }
        }
    }

    private void UploadPic(Uri imgFile, String namaFile) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();
        StorageReference picRef = storageReff.child("images/pasien/" + userID + "/" + namaFile);
        picRef.putFile(imgFile)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        if (namaFile == "KTP") {
                            picKTPcek = true;
                        } else if (namaFile == "KK") {
                            picKKcek = true;
                        } else if (namaFile == "Surat Sehat") {
                            picSuratSehatcek = true;
                        }
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