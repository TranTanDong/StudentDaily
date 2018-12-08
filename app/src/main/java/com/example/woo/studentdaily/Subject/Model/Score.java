package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Score implements Serializable {
    private double score;
    private int idst;
    private int idForm;
    private int idType;
    private String updateDay;
    private String note;

    public Score() {

    }

    public Score(double score, int idst, int idForm, int idType, String updateDay, String note) {
        this.score = score;
        this.idst = idst;
        this.idForm = idForm;
        this.idType = idType;
        this.updateDay = updateDay;
        this.note = note;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", idst=" + idst +
                ", idForm=" + idForm +
                ", idType=" + idType +
                ", updateDay='" + updateDay + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
