package com.example.project.Model;

public class Matiere {
    private String id ;
    private String name;
    private float  coef;
    private boolean Tp  ;
    private boolean Exam ;
    private boolean Dc ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Matiere (){}

    public Matiere(String name, float coef, Boolean tp, Boolean exam, Boolean dc) {
        this.name = name;
        this.coef = coef;
        Tp = tp;
        Exam = exam;
        Dc = dc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public boolean getTp() {
        return Tp;
    }

    public void setTp(boolean tp) {
        Tp = tp;
    }

    public boolean getExam() {
        return Exam;
    }

    public void setExam(boolean exam) {
        Exam = exam;
    }

    public boolean getDc() {
        return Dc;
    }

    public void setDc(boolean dc) {
        Dc = dc;
    }
}
