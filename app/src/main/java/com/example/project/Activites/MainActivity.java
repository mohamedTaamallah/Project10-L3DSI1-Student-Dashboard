package com.example.project.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project.Model.Matiere;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    long maxid = 0;
    EditText Nom;
    EditText Coef;
    CheckBox BtnTp,BtnExam,BtnDc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseReference reff  = FirebaseDatabase.getInstance().getReference().child("Etudiant");
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

                if(BtnDc.isChecked())
                {
                    matiere.setDc((true));
                }
                if(BtnExam.isChecked())
                {
                    matiere.setExam(true);
                }
                if(BtnTp.isChecked())
                {
                    matiere.setTp(true);
                }
                if (checkInputs() == true) {
                    matiere.setName(Nom.getText().toString());
                    matiere.setCoef(Float.parseFloat(Coef.getText().toString()));
                    matiere.setId(maxid);
                    reff.child(String.valueOf(maxid+1)).setValue(matiere);
                    Toast.makeText(MainActivity.this,"votre matiere a etait ajouter ",Toast.LENGTH_SHORT).show();
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
        if(!BtnDc.isChecked()&&!BtnTp.isChecked()&&!BtnExam.isChecked())
        {
            Toast.makeText(MainActivity.this, "Selectionnez les types d'epreuves", Toast.LENGTH_SHORT).show();
            result=false;

        }
        return result;
}
}