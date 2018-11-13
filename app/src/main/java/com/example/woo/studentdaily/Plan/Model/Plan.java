package com.example.woo.studentdaily.Plan.Model;

import android.os.Parcel;
import android.os.Parcelable;

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

    protected Plan(Parcel in) {
        id = in.readInt();
        codeUser = in.readString();
        name = in.readString();
        updateDay = in.readString();
    }

//    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
//        @Override
//        public Plan createFromParcel(Parcel in) {
//            return new Plan(in);
//        }
//
//        @Override
//        public Plan[] newArray(int size) {
//            return new Plan[size];
//        }
//    };

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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(id);
//        parcel.writeString(codeUser);
//        parcel.writeString(name);
//        parcel.writeString(updateDay);
//    }
}
