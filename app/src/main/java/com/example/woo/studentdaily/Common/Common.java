package com.example.woo.studentdaily.Common;

import java.text.SimpleDateFormat;

public class Common {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat noZero = new SimpleDateFormat("yyyy-M-d");
    public static SimpleDateFormat fullDay = new SimpleDateFormat("EEE, yyyy-MM-dd");
    public static boolean isEmptyOrNull(String s){
        if (s == "" || s == null){
            return true;
        } else return false;
    }
}
