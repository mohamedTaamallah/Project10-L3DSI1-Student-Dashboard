package com.example.project.SQL_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "stockage_photo";
    public static final int DATABASE_VERSION = 1;


    public DataBaseHandler(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "DataBase created ");


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_NAME + "(photo_id text primary key ,matiere_id text,description text,image BLOB not null )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
    }

    //insert image
    public Boolean insertImage(String photo_id ,String description, byte[] image,String matiere_id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("photo_id", photo_id);
        contentValues.put("description", description);
        contentValues.put("matiere_id",matiere_id);
        contentValues.put("image", image);

        db.insert(DATABASE_NAME, null, contentValues);
        return true;


    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}