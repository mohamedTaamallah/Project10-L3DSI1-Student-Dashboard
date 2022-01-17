package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Model.file;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandlerFile;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Date;

public class AddFileActivity extends AppCompatActivity {
   private final int CHOOSE_PDF_FROM_DEVICE=1001;
   private final int RESULT_OK=-1;
   private TextView file;
   private Uri data;
   PDFView pdfView;
   String  matiere_id;
    Boolean fileFixed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
         fileFixed =false;

        Button choose = findViewById(R.id.BtnChoose);
        Button Upload = findViewById(R.id.BtnUpload);
        DataBaseHandlerFile db = new DataBaseHandlerFile(AddFileActivity.this);

        EditText fileTitle = findViewById(R.id.txtFile);
        Upload.setEnabled(false);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file f = new file();
                long fileId = (new Date().getTime())/1000;
                Intent intent=getIntent();
                matiere_id = intent.getStringExtra("matiere");
                f.setFileId(String.valueOf(fileId));
                f.setFilesPath(data.toString());
                f.setMatiereId(matiere_id);
                if(TextUtils.isEmpty(fileTitle.getText().toString()))
                {

                    f.setFileTitle("No Title");

                }
                else{
                    f.setFileTitle(fileTitle.getText().toString());
                }
                db.InsertFile(f);
                Toast.makeText(AddFileActivity.this, "fichier a ete choisis ", Toast.LENGTH_SHORT).show();
                fileFixed=true;

            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  callChooseFileFromDevice();
                    Upload.setEnabled(true);
                    Toast.makeText(AddFileActivity.this, "fichier ajouté ", Toast.LENGTH_SHORT).show();
                }






        });
    }

       public void callChooseFileFromDevice(){
        Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent,CHOOSE_PDF_FROM_DEVICE);
       }
        public void onActivityResult(int requestCode , int resultCode , Intent resultData) {
            super.onActivityResult(requestCode, resultCode, resultData);
            if(requestCode==CHOOSE_PDF_FROM_DEVICE && resultCode==RESULT_OK && resultData.getData()!=null){
                 data = resultData.getData();


                }
            }

}
