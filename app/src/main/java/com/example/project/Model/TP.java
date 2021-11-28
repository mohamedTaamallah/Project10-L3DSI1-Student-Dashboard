package com.example.project.Model;

public class TP {

    boolean exist;
    float note;

    public TP() {
        exist = false;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean doExist) {
        this.exist = doExist;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TP{" +
                "exist=" + exist +
                ", note=" + note +
                '}';
    }
}
