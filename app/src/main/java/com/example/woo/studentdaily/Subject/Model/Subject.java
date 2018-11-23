package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Subject implements Serializable {
    private int id;
    private String name;
    private String createDay;

    public Subject() {

    }

    public Subject(int id, String name, String createDay) {
        this.id = id;
        this.name = name;
        this.createDay = createDay;
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
}
