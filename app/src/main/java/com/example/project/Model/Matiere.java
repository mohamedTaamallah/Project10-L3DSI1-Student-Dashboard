package com.example.project.Model;

public class Matiere {
    private String id ;
    private String name;
    private float  coef;
    private TP Tp  ;
    private Examen Exam ;
    private Control Dc ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Matiere (){}

    public Matiere(String name, float coef, TP tp, Examen exam, Control dc) {
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

    public TP getTp() {
        return Tp;
    }

    public void setTp(TP tp) {
        Tp = tp;
    }

    public Examen getExam() {
        return Exam;
    }

    public void setExam(Examen exam) {
        Exam = exam;
    }

    public Control getDc() {
        return Dc;
    }

    public void setDc(Control dc) {
        Dc = dc;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", coef=" + coef +
                ", Tp=" + Tp +
                ", Exam=" + Exam +
                ", Dc=" + Dc +
                '}';
    }
}
