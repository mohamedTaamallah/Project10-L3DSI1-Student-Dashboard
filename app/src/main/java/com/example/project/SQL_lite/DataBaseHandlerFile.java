package com.example.project.SQL_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Model.Image;
import com.example.project.Model.file;

import java.util.ArrayList;


public class DataBaseHandlerFile extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "stockage_file";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHandlerFile (Context cnt){
        super(cnt,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table "+DATABASE_NAME+"(fileId text primary Key , matiereId text not null , filePath text not null ,fileTitle text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP table if exists "+DATABASE_NAME);
    }
    // insertion d'un file
    public void InsertFile(file f){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("fileId",f.getFileId());
        contentValues.put("matiereId",f.getMatiereId());
        contentValues.put("filePath",f.getFilesPath());
        contentValues.put("fileTitle",f.getFileTitle());

        db.insert(DATABASE_NAME,null,contentValues);

    }
    // get all photos
    public ArrayList<file> getAllImage(String matiere_id)
    {
        ArrayList<file> list_file = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+DATABASE_NAME+" Where matiereId ="+matiere_id,null);
        while(res.moveToNext()){
            String fileId = res.getString(0);
            String FilePath= res.getString(2);
            String fileTitle = res.getString(3);
            file f =new file(fileId,FilePath,fileTitle);
            list_file.add(f);


        }
        return list_file;

    }
}
