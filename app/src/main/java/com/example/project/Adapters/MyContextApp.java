package com.example.project.Adapters;

import android.app.Application;

import com.example.project.Model.Matiere;

import java.util.ArrayList;


public class MyContextApp extends Application {


    private Matiere matiere;

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.matiere = null;
    }

    public Matiere getMatiere() { return matiere; }

    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
}
