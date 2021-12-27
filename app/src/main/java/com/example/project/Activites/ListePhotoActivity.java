package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.project.Adapters.ImageAdapter;
import com.example.project.Model.Image;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandlerImage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListePhotoActivity extends AppCompatActivity {
    ArrayList <Image> list_image;
    DataBaseHandlerImage db;
    ImageAdapter imageAdapter;
    static Image  SelectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_photo);

        setTitle("Liste Image");

        db = new DataBaseHandlerImage(this);
        Intent i = getIntent();
        String matiere_id = i.getStringExtra("matiere");

        FloatingActionButton addPhoto = findViewById(R.id.AddPhoto);
        RecyclerView gridView = (RecyclerView) findViewById(R.id.Grid);
        LinearLayoutManager layout = new LinearLayoutManager(ListePhotoActivity.this);
        gridView.setLayoutManager(layout);
        afficher(db,gridView,matiere_id);
        registerForContextMenu(gridView);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListePhotoActivity.this, matiere_id+" :p", Toast.LENGTH_SHORT).show();

                Intent i =new Intent(ListePhotoActivity.this, Upload_image_Activity.class);
                i.putExtra("matiere",matiere_id);

                startActivity(i);
            }
        });


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Boolean resultat= false;
        if(item.getTitle().equals("delete"))
        {
            Toast.makeText(ListePhotoActivity.this,  "Test  ", Toast.LENGTH_SHORT).show();
            alertDialog(list_image.get(item.getItemId()),db, item.getItemId());

        }
        else
        {
            Intent i =new Intent(ListePhotoActivity.this, ViewPhotoActivity.class);
            SelectedImage = list_image.get(item.getItemId());
            startActivity(i);


        }

        return  resultat;
    }

    public static Image getSelectedImage() {
        return SelectedImage;
    }

    public ImageAdapter afficher (DataBaseHandlerImage db, RecyclerView gridView, String matiere_id ){
        list_image  = db.getAllImage(matiere_id);
        ArrayList<String> list_descritption  = new ArrayList <>();
        ArrayList <byte[]> list_images  = new ArrayList <>();

        for (int i = 0; i <list_image.size() ; i++) {
            list_descritption.add(list_image.get(i).getTitre());
        }
         imageAdapter = new ImageAdapter(ListePhotoActivity.this,list_descritption,list_image,ListePhotoActivity.this);
        gridView.setAdapter(imageAdapter);
        return imageAdapter;
    }



    private void alertDialog(Image image, DataBaseHandlerImage db , int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Supprimer image");
        dialog.setTitle("Vous etes entrain de supprimer une photo ");


        dialog.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        db.deleteProduct(image.getImage_id());
                        imageAdapter.deleteItem(position);

                    }
                });
        dialog.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}