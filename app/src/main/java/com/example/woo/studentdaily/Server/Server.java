package com.example.woo.studentdaily.Server;

public class Server {

    public static String localhost = "192.168.49.68";
    public static String patchInsertUser = "http://" + localhost + "/server/insert_user.php";
    public static String patchSelectUser = "http://" + localhost + "/server/select_user.php";
    public static String patchUpdateUser = "http://" + localhost + "/server/update_user.php";
}
