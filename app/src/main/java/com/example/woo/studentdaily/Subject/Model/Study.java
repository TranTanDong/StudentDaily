package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Study implements Serializable {
    private int id;
    private int idSubject;
    private String dayOfWeek;
    private String lesson;
    private String place;
    private int idst;

    public Study() {

    }

    public Study(int id, int idSubject, String dayOfWeek, String lesson, String place, int idst) {
        this.id = id;
        this.idSubject = idSubject;
        this.dayOfWeek = dayOfWeek;
        this.lesson = lesson;
        this.place = place;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    @Override
    public String toString() {
        return "Study{" +
                "id=" + id +
                ", idSubject=" + idSubject +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", lesson='" + lesson + '\'' +
                ", place='" + place + '\'' +
                ", idst=" + idst +
                '}';
    }
}
