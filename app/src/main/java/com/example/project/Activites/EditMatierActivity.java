package com.example.project.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Control;
import com.example.project.Model.Examen;
import com.example.project.Model.Matiere;
import com.example.project.Model.TP;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMatierActivity extends AppCompatActivity {


    EditText Nom;
    EditText Coef;
    CheckBox BtnTp,BtnExam,BtnDc;
    MyContextApp app;
    public Matiere matiere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matier);
        app = (MyContextApp)getApplicationContext();
        setTitle("Modifier Matiere");
        DatabaseReference reff  = FirebaseDatabase.getInstance().getReference("Etudiant").child(app.getUid()).child("Matiere").child(app.getMatiere().getId());
        matiere  = new Matiere();

        Nom = findViewById(R.id.txtName);
        Coef = findViewById(R.id.txtCoef);
        Button btnEditer = findViewById(R.id.BtnEditer);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        BtnTp = findViewById(R.id.BtnTp);
        BtnExam = findViewById(R.id.BtnExamen);
        BtnDc = findViewById(R.id.BtnDc);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        matiere = snapshot.getValue(Matiere.class);
                    }
                    System.out.println("EditActivity: Inside........................................");
                    System.out.println(matiere);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        System.out.println("EditActivity: Outside........................................");
        System.out.println(matiere);
        initialDefaultValue();

        btnEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Control dc = app.getMatiere().getDc();
                dc.setExist(BtnDc.isChecked());
                matiere.setDc(dc);

                Examen ds = app.getMatiere().getExam();
                ds.setExist(BtnExam.isChecked());
                matiere.setExam(ds);


                TP tp = app.getMatiere().getTp();
                tp.setExist(BtnTp.isChecked());
                matiere.setTp(tp);


                if (checkInputs() == true) {

                    matiere.setName(Nom.getText().toString());
                    matiere.setCoef(Float.parseFloat(Coef.getText().toString()));


                    reff.setValue(matiere);
                    Toast.makeText(getApplicationContext(),"votre matiere a etait Modifier ",Toast.LENGTH_SHORT).show();
                    matiere.setId(app.getMatiere().getId());
                    //reset();

                }
            }
        });
    }

    public void reset ()
    {
        Nom.getText().clear();
        Coef.getText().clear();;


    }
    public boolean checkInputs(){
        boolean result = true;
        if(TextUtils.isEmpty(Nom.getText()))
        {
            Nom.setError("Veuillez entrer le nom du matiere");
            result=false;
        }
        if(TextUtils.isEmpty(Coef.getText()))
        {
            Coef.setError("Veuillez entrer le Coef de matiere");
            result=false;
        }
        if(!BtnDc.isChecked()&&!BtnTp.isChecked()&&!BtnExam.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Selectionnez les types d'epreuves", Toast.LENGTH_SHORT).show();
            result=false;

        }
        return result;
    }

    private void initialDefaultValue() {

        Nom.setText(app.getMatiere().getName());
        Coef.setText(String.valueOf(app.getMatiere().getCoef()));
        BtnExam.setChecked(app.getMatiere().getExam().isExist());
        BtnDc.setChecked(app.getMatiere().getDc().isExist());
        BtnTp.setChecked(app.getMatiere().getTp().isExist());
    }
}