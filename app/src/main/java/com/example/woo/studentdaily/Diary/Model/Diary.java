package com.example.woo.studentdaily.Diary.Model;

import java.io.Serializable;

public class Diary implements Serializable {
    private int id;
    private String codeUser;
    private String name;
    private String createDay;

    public Diary() {

    }

    public Diary(int id, String codeUser, String name, String createDay) {
        this.id = id;
        this.codeUser = codeUser;
        this.name = name;
        this.createDay = createDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(String codeUser) {
        this.codeUser = codeUser;
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

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", codeUser='" + codeUser + '\'' +
                ", name='" + name + '\'' +
                ", createDay='" + createDay + '\'' +
                '}';
    }
}
