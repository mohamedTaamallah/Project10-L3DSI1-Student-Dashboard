package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Control;
import com.example.project.Model.Examen;
import com.example.project.Model.Matiere;
import com.example.project.Model.Notepad;
import com.example.project.Model.TP;
import com.example.project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditNotePadActivity extends AppCompatActivity {

    private EditText mEditTextNote;
    private Button mButtonEditer;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    Notepad np, notepad;
    MyContextApp appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_pad);

        setTitle("Editer une note");

        appContext = (MyContextApp)getApplicationContext();
        np = appContext.getNotepad();


        mEditTextNote = (EditText) findViewById(R.id.LaNote);
        mButtonEditer = findViewById(R.id.BtnEdit);

        mEditTextNote.setText(np.getTexte());

        //firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Etudiant").child(appContext.getUid())
                .child("Matiere").child(appContext.getMatiere().getId())
                .child("Notepad").child(appContext.getNotepad().getId());


        mButtonEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    notepad = new Notepad();
                    notepad.setTexte(mEditTextNote.getText().toString());
                    mDatabaseRef.setValue(notepad);
                    Toast.makeText(getApplicationContext(),"Votre notepad a ete modifier",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MatiereDetailsActivity.class));
                }else {
                    Toast.makeText(EditNotePadActivity.this, "Verifier le champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean checkInput(){
        boolean result = true;
        if(TextUtils.isEmpty(mEditTextNote.getText()))
            result = false;
        return result;
    }
}