package com.example.project.SQL_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.project.Model.Image;

import java.sql.Blob;
import java.util.ArrayList;

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
    //get all images by matiere
    public ArrayList<Image> getAllImage(String matiere_id)
    {
        ArrayList<Image> list_image = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+DATABASE_NAME+" Where matiere_id ="+matiere_id,null);
        while(res.moveToNext()){
            String image_id = res.getString(0);
            String description= res.getString(2);
            byte[]   image = res.getBlob(3);
            Image image1 = new Image(image_id,description,image);
            list_image.add(image1);


        }
        return list_image;

    }

    //delete image
    public Boolean deleteProduct(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+DATABASE_NAME+" WHERE photo_id=?",
                new String[]{id});
        return true;
    }
}