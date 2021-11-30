package com.example.project.Model;

public class Control {

    boolean exist;
    float note;

    public Control() {
        exist = false;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Control{" +
                "exist=" + exist +
                ", note=" + note +
                '}';
    }
}
