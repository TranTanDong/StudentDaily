package com.example.woo.studentdaily.More.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String code;
    private String name;
    private String image;
    private String email;
    private String gender;
    private String birthDay;

    public User() {

    }

    public User(String code, String name, String image, String email, String gender, String birthDay) {
        this.code = code;
        this.name = name;
        this.image = image;
        this.email = email;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", birthDay='" + birthDay + '\'' +
                '}';
    }
}
