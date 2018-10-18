package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class Test implements Serializable {
    private int id;
    private int idHoc;
    private int idHinhThuc;
    private String ten;
    private String ngay;
    private String diaDiem;
    private String ghiChu;

    public Test() {
    }

    public Test(int id, int idHoc, int idHinhThuc, String ten, String ngay, String diaDiem, String ghiChu) {
        this.id = id;
        this.idHoc = idHoc;
        this.idHinhThuc = idHinhThuc;
        this.ten = ten;
        this.ngay = ngay;
        this.diaDiem = diaDiem;
        this.ghiChu = ghiChu;
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

    public int getIdHinhThuc() {
        return idHinhThuc;
    }

    public void setIdHinhThuc(int idHinhThuc) {
        this.idHinhThuc = idHinhThuc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
