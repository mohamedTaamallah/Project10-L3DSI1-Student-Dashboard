package com.example.project.Adapters;

import android.app.Application;

import com.example.project.Model.Etudiant;
import com.example.project.Model.Matiere;

import java.util.ArrayList;


public class MyContextApp extends Application {


    private Matiere matiere;
    private String uid;
    Etudiant etudiant;


    @Override
    public void onCreate()
    {
        super.onCreate();
        this.matiere = null;
        this.uid = "";
        this.etudiant = null;
    }

    public Matiere getMatiere() { return matiere; }

    public void setMatiere(Matiere matiere) { this.matiere = matiere; }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Etudiant getEtudiant() {return etudiant;}

    public void setEtudiant(Etudiant etudiant) {this.etudiant = etudiant;}
}
