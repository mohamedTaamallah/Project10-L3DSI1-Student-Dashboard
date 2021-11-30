package com.example.project.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
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

public class AddMatierActivity extends AppCompatActivity {
    long maxid = 0;
    EditText Nom;
    EditText Coef;
    CheckBox BtnTp,BtnExam,BtnDc;
    MyContextApp appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matier);
        setTitle("Ajouter une Matiere");
        appContext = (MyContextApp)getApplicationContext();

        DatabaseReference reff  = FirebaseDatabase.getInstance().getReference("Etudiant").child(appContext.getUid());
        Matiere matiere  = new Matiere();

        Button btnAjouter = findViewById(R.id.BtnAjouter);

         Nom = findViewById(R.id.txtName);
         Coef = findViewById(R.id.txtCoef);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

         BtnTp = findViewById(R.id.BtnTp);
         BtnExam = findViewById(R.id.BtnExamen);
         BtnDc = findViewById(R.id.BtnDc);

        reff.addValueEventListener(new ValueEventListener() {
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
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Control dc = new Control();
                    dc.setExist(BtnDc.isChecked());
                    matiere.setDc(dc);

                    Examen ds = new Examen();
                    ds.setExist(BtnExam.isChecked());
                    matiere.setExam(ds);


                    TP tp = new TP();
                    tp.setExist(BtnTp.isChecked());
                    matiere.setTp(tp);


                if (checkInputs() == true) {

                    matiere.setName(Nom.getText().toString());
                    matiere.setCoef(Float.parseFloat(Coef.getText().toString()));


                    reff.child(String.valueOf(maxid+1)).setValue(matiere);
                    Toast.makeText(AddMatierActivity.this,"votre matiere a etait ajouter ",Toast.LENGTH_SHORT).show();
                    reset();

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
                Toast.makeText(AddMatierActivity.this, "Selectionnez les types d'epreuves", Toast.LENGTH_SHORT).show();
                result=false;

            }
            return result;
    }
    // menu configuration
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}