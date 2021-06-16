package com.example.vaksinasishedule;

public class User {
    public String nama, email, nomorTelpon, nomorKTP, nomorKK, alamat, riwayatPenyakit, kodePasien, kodeTempat;

    public User() {
    }

    public String getNama() {
        return nama;
    }

    public String getNomorKTP() {
        return nomorKTP;
    }

    public void setNomorKTP(String nomorKTP) {
        this.nomorKTP = nomorKTP;
    }

    public String getKodePasien() {
        return kodePasien;
    }

    public void setKodePasien(String kodePasien) {
        this.kodePasien = kodePasien;
    }

    public String getKodeTempat() {
        return kodeTempat;
    }

    public void setKodeTempat(String kodeTempat) {
        this.kodeTempat = kodeTempat;
    }

    public String getNomorKK() {
        return nomorKK;
    }

    public void setNomorKK(String nomorKK) {
        this.nomorKK = nomorKK;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRiwayatPenyakit() {
        return riwayatPenyakit;
    }

    public void setRiwayatPenyakit(String riwayatPenyakit) {
        this.riwayatPenyakit = riwayatPenyakit;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelpon() {
        return nomorTelpon;
    }

    public void setNomorTelpon(String nomorTelpon) {
        this.nomorTelpon = nomorTelpon;
    }
}
