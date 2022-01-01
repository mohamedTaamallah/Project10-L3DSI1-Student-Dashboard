package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.Adapters.FileAdapter;
import com.example.project.Model.file;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandlerFile;

import java.util.ArrayList;

public class listFilesActivity extends AppCompatActivity {
    String  matiere_id;
    ArrayList<file> listFichier;
    private final int CHOOSE_PDF_FROM_DEVICE=1001;
    private static final int REQUEST_CODE = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        Intent intent=getIntent();
        matiere_id = intent.getStringExtra("matiere");
        RecyclerView recyclerView = findViewById(R.id.recycleViewFiles);

        LinearLayoutManager layout = new LinearLayoutManager(listFilesActivity.this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);

        setTitle("Liste des fichiers ");
        DataBaseHandlerFile db = new DataBaseHandlerFile(this);
         listFichier = new ArrayList<file>();
        listFichier = db.getAllImage(matiere_id);
        FileAdapter fileAdapter = new FileAdapter(this,listFichier,this);

        recyclerView.setAdapter(fileAdapter);

        registerForContextMenu(recyclerView);

    }
    public void goToAddFile(View v){
        Intent i =new Intent(listFilesActivity.this,AddFileActivity.class);
        i.putExtra("matiere",matiere_id);
        startActivity(i);
    }
    public boolean onContextItemSelected(MenuItem item) {
        Boolean resultat= false;
        if(item.getTitle().equals("delete"))
        {
            Toast.makeText(listFilesActivity.this,  "Test  ", Toast.LENGTH_SHORT).show();

        }
        else
        {

                Intent i =new Intent(listFilesActivity.this, ViewFileActivity.class);
                String filePath = listFichier.get(item.getItemId()).getFilesPath();
                i.putExtra("filePath",filePath);
                startActivity(i);


        }

        return  resultat;
    }

    }



