package com.example.project.Model;

public class date {
    private String id ;
    private String description ;
    private String date;
    private String time;


    public date() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public date(String id , String description, String time, String date) {
        this.id=id;
        this.description = description;
        this.date = date;
        this.time = time;
    }
    public date(String description, String time, String date) {
        this.description = description;
        this.date = date;
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
