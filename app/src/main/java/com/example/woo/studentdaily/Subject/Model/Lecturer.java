package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Lecturer implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String web;
    private int idst;

    public Lecturer() {

    }

    public Lecturer(int id, String name, String phone, String email, String web, int idst) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.web = web;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", idst=" + idst +
                '}';
    }
}
