package com.example.woo.studentdaily.Common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.woo.studentdaily.Login.SignUpActivity;
import com.example.woo.studentdaily.More.Model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {

    public static SimpleDateFormat f_ddmmy = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat f_ymmdd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat f_dmy = new SimpleDateFormat("d/M/yyyy");
    public static SimpleDateFormat f_eymmdd = new SimpleDateFormat("EEE, yyyy-MM-dd");
    public static SimpleDateFormat f_ymmddhh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static String moveSlashTo(String s, String a, String b){
        if (s.contains(a)){
            String arr[] = s.split(a);
            return arr[2] + b + arr[1] + b + arr[0];
        }else return s;
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

    public static void processBirthDay(Context context, final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                textView.setText(Common.f_ddmmy.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                callback,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
