package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Test implements Serializable {
    private int id;
    private int idSubject;
    private int idForm;
    private String dayTest;
    private String place;
    private String note;
    private int idst;

    public Test() {
    }

    public Test(int id, int idSubject, int idForm, String dayTest, String place, String note, int idst) {
        this.id = id;
        this.idSubject = idSubject;
        this.idForm = idForm;
        this.dayTest = dayTest;
        this.place = place;
        this.note = note;
        this.idst = idst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public String getDayTest() {
        return dayTest;
    }

    public void setDayTest(String dayTest) {
        this.dayTest = dayTest;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", idSubject=" + idSubject +
                ", idForm=" + idForm +
                ", dayTest='" + dayTest + '\'' +
                ", place='" + place + '\'' +
                ", note='" + note + '\'' +
                ", idst=" + idst +
                '}';
    }
}
