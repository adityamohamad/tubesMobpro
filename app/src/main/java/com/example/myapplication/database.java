package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "song.db";
    private static final int DATABASE_VERSION = 1;

    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabel kesatu "music"
        String sql = "CREATE TABLE music (id INTEGER PRIMARY KEY, nama TEXT NULL, jm TEXT NULL, negara TEXT NULL);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "INSERT INTO music (id, nama, jm, negara) VALUES (001, 'Taylor Swift', 'Perempuan', 'Amerika Serikat');";
        db.execSQL(sql);
        sql = "INSERT INTO music (id, nama, jm, negara) VALUES (002, 'Taylor Swift', 'Perempuan', 'Amerika Serikat');";
        db.execSQL(sql);
        sql = "INSERT INTO music (id, nama, jm, negara) VALUES (003, 'Taylor Swift', 'Perempuan', 'Amerika Serikat');";
        db.execSQL(sql);

        // Tabel kedua "singer"
        String singerTable = "CREATE TABLE singer (id INTEGER PRIMARY KEY, title TEXT NULL, artist TEXT NULL, genre TEXT NULL);";
        Log.d("Data", "onCreate: " + singerTable);
        db.execSQL(singerTable);

        sql = "INSERT INTO singer (id, title, artist, genre) VALUES (001, 'ME', 'Taylor Swift', 'Pop');";
        db.execSQL(sql);
        sql = "INSERT INTO singer (id, title, artist, genre) VALUES (002, 'Karma', 'Taylor Swift', 'Pop');";
        db.execSQL(sql);
        sql = "INSERT INTO singer (id, title, artist, genre) VALUES (003, 'Love Story', 'Taylor Swift', 'Country');";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS music");
        db.execSQL("DROP TABLE IF EXISTS singer");
        onCreate(db);
    }
}
