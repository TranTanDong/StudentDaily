package com.example.woo.studentdaily.Common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.woo.studentdaily.Diary.Model.Diary;
import com.example.woo.studentdaily.Diary.Model.PostDiary;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Lecturer;
import com.example.woo.studentdaily.Subject.Model.Score;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Common {

    public static SimpleDateFormat f_ddmmy = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat f_ymmdd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat f_dmy = new SimpleDateFormat("d/M/yyyy");
    public static SimpleDateFormat f_eymmdd = new SimpleDateFormat("EEE, yyyy-MM-dd");
    public static SimpleDateFormat f_ymmddhh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static DecimalFormat d_double = new DecimalFormat("##.#");

    public static final String PREFERENCE = "USER";
    private static final String KEY_USER = "KEY_USER";
    private static final String LIST_PLAN = "LIST_PLAN";
    private static final String LIST_EVENT = "LIST_EVENT";
    private static final String LIST_SUBJECT = "LIST_SUBJECT";
    private static final String LIST_LECTURER = "LIST_LECTURER";
    private static final String LIST_CLASS_YEAR = "LIST_CLASS_YEAR";
    private static final String LIST_STUDY = "LIST_STUDY";
    private static final String LIST_TEST = "LIST_TEST";
    private static final String LIST_DIARY = "LIST_DIARY";
    private static final String LIST_SCORE = "LIST_SCORE";
    private static final String LIST_POST_DIARY = "LIST_POST_DIARY";


    public static String moveSlashTo(String s, String a, String b){
        if (s.contains(a)){
            String arr[] = s.split(a);
            return arr[2] + b + arr[1] + b + arr[0];
        }else return s;
    }

    public static ArrayList<Score> getListScore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Score> listScore = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_SCORE, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Score>>(){}.getType();
            listScore = gson.fromJson(serializedObject, type);
        }
        return listScore;
    }

    public static void setListScore(ArrayList<Score> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_SCORE, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Diary> getListDiary(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Diary> listDiary = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_DIARY, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Diary>>(){}.getType();
            listDiary = gson.fromJson(serializedObject, type);
        }
        return listDiary;
    }

    public static void setListDiary(ArrayList<Diary> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_DIARY, gson.toJson(list));
        editor.apply();
    }

    public static void setCurrentUser(@NonNull Context context, User user){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(KEY_USER, gson.toJson(user));
        editor.apply();
    }

    public static User getUser (@NonNull Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        String user = sharedPref.getString(KEY_USER, "");
        if (user == ""){
            return null;
        }
        else {
            Gson gson = new Gson();
            return gson.fromJson(user, User.class);
        }
    }

    public static ArrayList<Lecturer> getListLecturer(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Lecturer> listLecturer = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_LECTURER, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Lecturer>>(){}.getType();
            listLecturer = gson.fromJson(serializedObject, type);
        }
        return listLecturer;
    }

    public static void setListLecturer(ArrayList<Lecturer> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_LECTURER, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Study> getListStudy(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Study> listStudy = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_STUDY, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Study>>(){}.getType();
            listStudy = gson.fromJson(serializedObject, type);
        }
        return listStudy;
    }

    public static void setListStudy(ArrayList<Study> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_STUDY, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Test> getListTest(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Test> listTest = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_TEST, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Test>>(){}.getType();
            listTest = gson.fromJson(serializedObject, type);
        }
        return listTest;
    }

    public static void setListTest(ArrayList<Test> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_TEST, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<PostDiary> getListPostDiary(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<PostDiary> listPostDiary = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_POST_DIARY, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<PostDiary>>(){}.getType();
            listPostDiary = gson.fromJson(serializedObject, type);
        }
        return listPostDiary;
    }

    public static void setListPostDiary(ArrayList<PostDiary> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_POST_DIARY, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<ClassYear> getListClassYear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<ClassYear> listClassYear = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_CLASS_YEAR, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<ClassYear>>(){}.getType();
            listClassYear = gson.fromJson(serializedObject, type);
        }
        return listClassYear;
    }

    public static void setListClassYear(ArrayList<ClassYear> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_CLASS_YEAR, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Subject> getListSubject(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Subject> listSubject = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_SUBJECT, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Subject>>(){}.getType();
            listSubject = gson.fromJson(serializedObject, type);
        }
        return listSubject;
    }

    public static void setListSubject(ArrayList<Subject> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_SUBJECT, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Plan> getListPlan(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Plan> listPlan = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_PLAN, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Plan>>(){}.getType();
            listPlan = gson.fromJson(serializedObject, type);
        }
        return listPlan;
    }

    public static void setListPlan(ArrayList<Plan> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_PLAN, gson.toJson(list));
        editor.apply();
    }

    public static void setListEvent(ArrayList<Event> list, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(LIST_EVENT, gson.toJson(list));
        editor.apply();
    }

    public static ArrayList<Event> getListEvent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        ArrayList<Event> listEvent = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(LIST_EVENT, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Event>>(){}.getType();
            listEvent = gson.fromJson(serializedObject, type);
        }
        return listEvent;
    }

    public static void setPreferenceNull (@NonNull Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        editor.apply();

    }

    public static void processBirthDay(Context context, final TextView textView, final String birthDay) {
        String arr[] = birthDay.split("/");
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(arr[2]), Integer.parseInt(arr[1])-1,Integer.parseInt(arr[0]));
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


    public static String stringForm(int position){
        switch (position){
            case 1: return "Trắc nghiệm";
            case 2: return "Tự luận";
            case 3: return "Thực hành";
            case 4: return "Trắc nghiệm và tự luận";
            case 5: return "Khác";
            default: return "";
        }
    }

    public static int idForm(String form){
        switch (form){
            case "Trắc nghiệm":
                return 1;
            case "Tự luận":
                return 2;
            case "Thực hành":
                return 3;
            case "Trắc nghiệm và tự luận":
                return 4;
            case "Khác":
                return 5;
            default:
                return -1;
        }
    }

    public static String stringSubject(Context context, int idst){
        ArrayList<Subject> subjects = getListSubject(context);
        for (Subject subject:subjects){
            if (subject.getIdst() == idst){
                return subject.getName();
            }
        }
        return "";
    }

    public static String stringType(int position){
        switch (position){
            case 1: return "Hệ số 1";
            case 2: return "Hệ số 2";
            case 3: return "Hệ số 3";
            default: return "";
        }
    }

    public static int idType(String type){
        switch (type){
            case "Hệ số 1":
                return 1;
            case "Hệ số 2":
                return 2;
            case "Hệ số 3":
                return 3;
            default:
                return -1;
        }
    }

    public static String stringPlan(Context context, int position){
        ArrayList<Plan> plans = getListPlan(context);
        for (Plan plan:plans){
            if (plan.getId() == position){
                return plan.getName();
            }
        }
        return "";
    }

    public static String moveDay(String createDay) {
        if (createDay.contains(" ")){
            String startTime[] = createDay.split(" ");
            String day = Common.moveSlashTo(startTime[0], "-", "/");
            String time = startTime[1];
            return day + " " + time;
        }else return createDay;
    }
}
