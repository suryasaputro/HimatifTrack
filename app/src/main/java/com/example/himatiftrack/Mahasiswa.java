package com.example.himatiftrack;

public class Mahasiswa {
    private int id;
    private String nama, nim, angkatan, email, tempatLahir, tanggalLahir, gender, alamat, hobi;

    public Mahasiswa(int id, String nama, String nim, String angkatan, String email, String tempatLahir, String tanggalLahir, String gender, String alamat, String hobi) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.angkatan = angkatan;
        this.email = email;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.gender = gender;
        this.alamat = alamat;
        this.hobi = hobi;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getNim() { return nim; }
    public String getAngkatan() { return angkatan; }
    public String getEmail() { return email; }
    public String getTempatLahir() { return tempatLahir; }
    public String getTanggalLahir() { return tanggalLahir; }
    public String getGender() { return gender; }
    public String getAlamat() { return alamat; }
    public String getHobi() { return hobi; }

    @Override
    public String toString() {
        return nama + " (" + nim + ")";
    }

    public String toFullString() {
        return "Nama: " + nama +
                "\nNIM: " + nim +
                "\nTahun Angkatan: " + angkatan +
                "\nEmail: " + email +
                "\nTempat, Tanggal Lahir: " + tempatLahir + ", " + tanggalLahir +
                "\nJenis Kelamin: " + gender +
                "\nAlamat: " + alamat +
                "\nHobi: " + hobi;
    }
}
