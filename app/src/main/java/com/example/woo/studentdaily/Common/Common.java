package com.example.woo.studentdaily.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.woo.studentdaily.More.Model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public class Common {

    public static SimpleDateFormat f_ymmdd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat f_ymd = new SimpleDateFormat("yyyy-M-d");
    public static SimpleDateFormat f_eymmdd = new SimpleDateFormat("EEE, yyyy-MM-dd");
    public static SimpleDateFormat f_ymmddhh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static boolean isEmptyOrNull(String s){
        if (s == "" || s == null){
            return true;
        } else return false;
    }

    public static void setCurrentUser(@NonNull Context context, User user){
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString("U", gson.toJson(user));
        editor.apply();
    }

    public static User getUser (@NonNull Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        String user = sharedPref.getString("U", "");
        if (user == ""){
            return null;
        }
        else {
            Gson gson = new Gson();
            return gson.fromJson(user, User.class);
        }
    }

    public static void setPreferenceNull (@NonNull Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        editor.apply();

    }

    public static void removeKey(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
