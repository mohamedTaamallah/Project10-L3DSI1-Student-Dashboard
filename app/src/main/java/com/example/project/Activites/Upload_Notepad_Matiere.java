package com.example.project.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Control;
import com.example.project.Model.Examen;
import com.example.project.Model.TP;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Upload_Notepad_Matiere extends AppCompatActivity {
    long maxid = 0;

    private EditText mEditTextNote;
    private Button mButtonAjouter;

    //fire base
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    MyContextApp appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_notepad_matiere);
        setTitle("Ajouter une note");

        appContext = (MyContextApp)getApplicationContext();

        Intent intent=getIntent();
        String matiere_id = intent.getStringExtra("matiere");

        mEditTextNote = findViewById(R.id.LaNote);
        mButtonAjouter = findViewById(R.id.BtnAjouter);

        //firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Etudiant").child(appContext.getUid()).child("Matiere").child(appContext.getMatiere().getId()).child("Notepad");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mButtonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mEditTextNote.getText()) == false) {

                    mDatabaseRef.child(String.valueOf(maxid+1)).setValue(mEditTextNote.getText().toString());
                    Toast.makeText(Upload_Notepad_Matiere.this,"votre note à était ajouté ",Toast.LENGTH_SHORT).show();
                    reset();

                }

            }
        });

    }

    public void reset ()
    {
        mEditTextNote.getText().clear();

    }


}












