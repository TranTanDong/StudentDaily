package com.example.woo.studentdaily.Plan.Model;

import java.io.Serializable;

public class Plan implements Serializable {
    private int id;
    private String codeUser;
    private String name;
    private String updateDay;

    public Plan() {

    }

    public Plan(int id, String codeUser, String name, String updateDay) {
        this.id = id;
        this.codeUser = codeUser;
        this.name = name;
        this.updateDay = updateDay;
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

    public String getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }
}
