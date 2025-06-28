package com.example.himatiftrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "himatif.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama";
    private static final String COL_NIM = "nim";
    private static final String COL_ANGKATAN = "angkatan";
    private static final String COL_EMAIL = "email";
    private static final String COL_TEMPAT = "tempat_lahir";
    private static final String COL_TANGGAL = "tanggal_lahir";
    private static final String COL_GENDER = "gender";
    private static final String COL_ALAMAT = "alamat";
    private static final String COL_HOBI = "hobi";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_MAHASISWA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAMA + " TEXT,"
                + COL_NIM + " TEXT,"
                + COL_ANGKATAN + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_TEMPAT + " TEXT,"
                + COL_TANGGAL + " TEXT,"
                + COL_GENDER + " TEXT,"
                + COL_ALAMAT + " TEXT,"
                + COL_HOBI + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }

    public void insertMahasiswa(Mahasiswa m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAMA, m.getNama());
        cv.put(COL_NIM, m.getNim());
        cv.put(COL_ANGKATAN, m.getAngkatan());
        cv.put(COL_EMAIL, m.getEmail());
        cv.put(COL_TEMPAT, m.getTempatLahir());
        cv.put(COL_TANGGAL, m.getTanggalLahir());
        cv.put(COL_GENDER, m.getGender());
        cv.put(COL_ALAMAT, m.getAlamat());
        cv.put(COL_HOBI, m.getHobi());
        db.insert(TABLE_MAHASISWA, null, cv);
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MAHASISWA, null);

        if (cursor.moveToFirst()) {
            do {
                Mahasiswa m = new Mahasiswa(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                list.add(m);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void updateMahasiswa(Mahasiswa m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAMA, m.getNama());
        cv.put(COL_NIM, m.getNim());
        cv.put(COL_ANGKATAN, m.getAngkatan());
        cv.put(COL_EMAIL, m.getEmail());
        cv.put(COL_TEMPAT, m.getTempatLahir());
        cv.put(COL_TANGGAL, m.getTanggalLahir());
        cv.put(COL_GENDER, m.getGender());
        cv.put(COL_ALAMAT, m.getAlamat());
        cv.put(COL_HOBI, m.getHobi());
        db.update(TABLE_MAHASISWA, cv, COL_ID + " = ?", new String[]{String.valueOf(m.getId())});
    }

    public void deleteMahasiswa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAHASISWA, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
