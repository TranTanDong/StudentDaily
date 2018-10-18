package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Study implements Serializable {
    private int id;
    private int idHoc;
    private String thu;
    private String tiet;
    private String phongHoc;

    public Study() {

    }

    public Study(int id, int idHoc, String thu, String tiet, String phongHoc) {
        this.id = id;
        this.idHoc = idHoc;
        this.thu = thu;
        this.tiet = tiet;
        this.phongHoc = phongHoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHoc() {
        return idHoc;
    }

    public void setIdHoc(int idHoc) {
        this.idHoc = idHoc;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getTiet() {
        return tiet;
    }

    public void setTiet(String tiet) {
        this.tiet = tiet;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }
}
