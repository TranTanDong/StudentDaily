package com.example.woo.studentdaily.Common;

import java.text.SimpleDateFormat;

public class Common {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static boolean isEmptyOrNull(String s){
        if (s == "" || s == null){
            return true;
        } else return false;
    }
}
