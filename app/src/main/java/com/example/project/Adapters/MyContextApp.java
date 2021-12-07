package com.example.project.Adapters;

import android.app.Application;

import com.example.project.Model.Etudiant;
import com.example.project.Model.Matiere;
import com.example.project.Model.Notepad;

import java.util.ArrayList;


public class MyContextApp extends Application {


    private Matiere matiere;
    private String uid;
    Etudiant etudiant;
    Notepad notepad;

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.matiere = null;
        this.uid = "";
        this.etudiant = null;
        this.notepad = null;
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

    public Notepad getNotepad() {return notepad;}

    public void setNotepad(Notepad notepad) { this.notepad = notepad; }
}
