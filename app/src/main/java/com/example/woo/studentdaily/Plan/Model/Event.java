package com.example.woo.studentdaily.Plan.Model;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private int idPlan;
    private String name;
    private String place;
    private String startTime;
    private String endTime;
    private int priority;
    private int remind;
    private String describe;

    public Event() {

    }

    public Event(int id, int idPlan, String name, String place, String startTime, String endTime, int priority, int remind, String describe) {
        this.id = id;
        this.idPlan = idPlan;
        this.name = name;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.remind = remind;
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
