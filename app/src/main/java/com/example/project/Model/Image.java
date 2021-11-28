package com.example.project.Model;

public class Image {
    private String titre ;
    private String image ;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Image(String titre, String image) {
        if (titre.trim().equals("")) {
            titre = "No Name";
        }

        this.titre = titre;
        this.image = image;
    }
    public Image() {

    }
}
