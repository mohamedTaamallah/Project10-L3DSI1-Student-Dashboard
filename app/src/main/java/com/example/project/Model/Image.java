package com.example.project.Model;

public class Image {
    private String titre ;
    private byte[] image ;
    private String image_id;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Image(String image_id,String titre, byte[] image) {
        if (titre.trim().equals("")) {
            titre = "No Name";
        }
        this.image_id=image_id;
        this.titre = titre;
        this.image = image;
    }
    public Image(String image_id) {
        this.image_id=image_id;

    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
}
