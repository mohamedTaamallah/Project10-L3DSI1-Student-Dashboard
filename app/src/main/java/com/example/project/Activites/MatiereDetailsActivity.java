package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project.Adapters.MyContextApp;
import com.example.project.R;

public class MatiereDetailsActivity extends AppCompatActivity {

    MyContextApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matiere_details);
        app = (MyContextApp) getApplicationContext();

        setTitle("");
        ((TextView)findViewById(R.id.matName)).setText(app.getMatiere().getName());

        System.out.println("--------------------------------");
        System.out.println("Nom Matiere: "+app.getMatiere().getName());
        System.out.println("Mat ID: "+ app.getMatiere().getId());
        System.out.println("--------------------------------");
    }
}