package com.example.woo.studentdaily.Diary.Model;

import java.io.Serializable;

public class PostDiary implements Serializable {
    private int id;
    private int idDiary;
    private String content;
    private String dayCreate;
    private String attach;

    public PostDiary() {

    }

    public PostDiary(int id, int idDiary, String content, String dayCreate, String attach) {
        this.id = id;
        this.idDiary = idDiary;
        this.content = content;
        this.dayCreate = dayCreate;
        this.attach = attach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDiary() {
        return idDiary;
    }

    public void setIdDiary(int idDiary) {
        this.idDiary = idDiary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(String dayCreate) {
        this.dayCreate = dayCreate;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "PostDiary{" +
                "id=" + id +
                ", idDiary=" + idDiary +
                ", content='" + content + '\'' +
                ", dayCreate='" + dayCreate + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }
}
