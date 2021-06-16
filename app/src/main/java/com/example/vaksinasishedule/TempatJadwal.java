package com.example.vaksinasishedule;

public class TempatJadwal {
    String  namaRS, alamatRS, uriImage;

    public TempatJadwal() {
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public TempatJadwal(String namaRS, String alamatRS, String uriImage) {
        this.namaRS = namaRS;
        this.alamatRS = alamatRS;
        this.uriImage = uriImage;
    }

    public String getAlamatRS() {
        return alamatRS;
    }

    public void setAlamatRS(String alamatRS) {
        this.alamatRS = alamatRS;
    }


    public String getNamaRS() {
        return namaRS;
    }

    public void setNamaRS(String namaRS) {
        this.namaRS = namaRS;
    }
}
