package com.example.project.Model;

public class Notepad {
    private String id ;
    private String texte;

    public Notepad() {
    }

    public Notepad(String texte) {
        this.texte = texte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public String toString() {
        return "Notepad{" +
                "id='" + id + '\'' +
                ", texte='" + texte + '\'' +
                '}';
    }
}
