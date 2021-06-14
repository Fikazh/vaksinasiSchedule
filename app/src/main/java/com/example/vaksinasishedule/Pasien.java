package com.example.vaksinasishedule;

public class Pasien {
    public String nama, nomorKTP, nomorKK, riwayatPenyakit, alamat;

    public Pasien() {
    }

    public String getNomorKK() {
        return nomorKK;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNomorKK(String nomorKK) {
        this.nomorKK = nomorKK;
    }

    public String getNomorKTP() {
        return nomorKTP;
    }

    public void setNomorKTP(String nomorKTP) {
        this.nomorKTP = nomorKTP;
    }

    public String getRiwayatPenyakit() {
        return riwayatPenyakit;
    }

    public void setRiwayatPenyakit(String riwayatPenyakit) {
        this.riwayatPenyakit = riwayatPenyakit;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
