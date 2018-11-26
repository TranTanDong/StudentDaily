package com.example.woo.studentdaily.Subject.Model;

import java.io.Serializable;

public class ClassYear implements Serializable {
    private int idClass;
    private String nameClass;

    private int idSemester;
    private String nameSemester;
    private int year;

    private int idst;

    public ClassYear() {

    }

    public ClassYear(int idClass, String nameClass, int idSemester, String nameSemester, int year, int idst) {
        this.idClass = idClass;
        this.nameClass = nameClass;
        this.idSemester = idSemester;
        this.nameSemester = nameSemester;
        this.year = year;
        this.idst = idst;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public int getIdSemester() {
        return idSemester;
    }

    public void setIdSemester(int idSemester) {
        this.idSemester = idSemester;
    }

    public String getNameSemester() {
        return nameSemester;
    }

    public void setNameSemester(String nameSemester) {
        this.nameSemester = nameSemester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    @Override
    public String toString() {
        return "ClassYear{" +
                "idClass=" + idClass +
                ", nameClass='" + nameClass + '\'' +
                ", idSemester=" + idSemester +
                ", nameSemester='" + nameSemester + '\'' +
                ", year=" + year +
                ", idst=" + idst +
                '}';
    }
}
