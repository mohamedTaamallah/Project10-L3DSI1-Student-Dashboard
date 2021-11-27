package com.example.project.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Control;
import com.example.project.Model.Examen;
import com.example.project.Model.Matiere;
import com.example.project.Model.TP;
import com.example.project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNotesActivity extends AppCompatActivity {

    MyContextApp app;
    Matiere matiere;
    Button btnEdit;
    TextView txtNoteTP, txtNoteDC, txtNoteDS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        setTitle("Modifier les Notes");
        app = (MyContextApp) getApplicationContext();
        matiere = app.getMatiere();

        DatabaseReference reff  = FirebaseDatabase.getInstance().getReference("Etudiant").child(app.getUid());

        btnEdit = (Button) findViewById(R.id.btnEdit);
        txtNoteTP = (TextView)findViewById(R.id.txtNoteTP);
        txtNoteDC = (TextView)findViewById(R.id.txtNoteDC);
        txtNoteDS = (TextView)findViewById(R.id.txtNoteDS);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    Control dc = setUpDC(matiere);
                    TP tp = setUpTP(matiere);
                    Examen ds = setUpDS(matiere);

                    Matiere mat = new Matiere(matiere.getName(), matiere.getCoef(), tp, ds, dc);

                    System.out.println(matiere.getId() + mat);
                    reff.child(String.valueOf(matiere.getId())).setValue(mat);

                    Toast.makeText(EditNotesActivity.this, "Modification avec success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditNotesActivity.this, "Verifier les notes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!matiere.getTp().isExist()){
            ((RelativeLayout)findViewById(R.id.input_TP)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.viewTP)).setVisibility(View.GONE);
        }
        if(!matiere.getDc().isExist()){
            ((RelativeLayout)findViewById(R.id.input_controle)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.viewDC)).setVisibility(View.GONE);
        }
        if(!matiere.getExam().isExist()){
            ((RelativeLayout)findViewById(R.id.input_Exam)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.viewTP)).setVisibility(View.GONE);
        }
    }

    public TP setUpTP(@NonNull Matiere m) {
        TP tp = new TP();
        tp.setExist(m.getTp().isExist());
        String txtTP = txtNoteTP.getText().toString();
        if(!txtTP.isEmpty()) {
            tp.setNote(Float.parseFloat(txtTP));
        }
        return tp;
    }
    public Control setUpDC(@NonNull Matiere m) {
        String txtDC = txtNoteDC.getText().toString();

        Control dc = new Control();
        dc.setExist(m.getDc().isExist());
        if(!txtDC.isEmpty()) {
            dc.setNote(Float.parseFloat(txtDC));
        }
        return dc;
    }
    public Examen setUpDS(@NonNull Matiere m) {
        String txtDS = txtNoteDS.getText().toString();
        Examen ds = new Examen();
        ds.setExist(m.getExam().isExist());
        if(!txtDS.isEmpty()) {
            ds.setNote(Float.parseFloat(txtDS));
        }

        return ds;
    }

    private boolean checkInput() {
        boolean Valid = true;



        if(!txtNoteTP.getText().toString().isEmpty()) {
            float txtTP = Float.parseFloat(txtNoteTP.getText().toString());
            if(txtTP>20f || txtTP<0f) {
                Valid = false;
            }
        }else Valid = false;
        if(!txtNoteDC.getText().toString().isEmpty()) {
            float txtDC = Float.parseFloat(txtNoteDC.getText().toString());
            if(txtDC>20f || txtDC<0f) {
                Valid = false;
            }
        }else Valid = false;
         if(!txtNoteDS.getText().toString().isEmpty()) {
             float txtDS = Float.parseFloat(txtNoteDS.getText().toString());
             if (txtDS > 20f || txtDS < 0f) {
                 Valid = false;
             }
         }else Valid = false;
        return Valid;
    }
}