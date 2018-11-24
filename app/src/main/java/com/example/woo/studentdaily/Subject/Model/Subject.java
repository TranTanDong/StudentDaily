package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Subject implements Serializable {
    private int id;
    private String name;
    private String createDay;
    private int idst;

    public Subject() {

    }

    public Subject(int id, String name, String createDay, int idst) {
        this.id = id;
        this.name = name;
        this.createDay = createDay;
        this.idst = idst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDay='" + createDay + '\'' +
                ", idst=" + idst +
                '}';
    }
}
