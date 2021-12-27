package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.example.project.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class ViewFileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = -1;
    private final int CHOOSE_PDF_FROM_DEVICE=1001;
    Uri uriFile;
    PDFView pdfView;
    String filePath;
    Intent intent;
    String test;
    String displayName;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        Intent i=getIntent();
        filePath=i.getStringExtra("fileUri");

         Uri uri = Uri.parse(filePath);
        File myFile = new File(filePath);

        if (filePath.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = this.getContentResolver().query(uriFile, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    test = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  if (filePath.startsWith("file://")) {
            displayName = myFile.getName();
        }
        setTitle("File viewer");
        pdfView.fromUri(uri).defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(2)
                .load();





    }

}
