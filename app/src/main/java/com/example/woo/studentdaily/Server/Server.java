package com.example.woo.studentdaily.Server;

public class Server {

    public static String localhost = "192.168.2.50";
    public static String patchInsertUser = "http://" + localhost + "/server/insert_user.php";
    public static String patchSelectUser = "http://" + localhost + "/server/select_user.php";
    public static String patchUpdateUser = "http://" + localhost + "/server/update_user.php";

    public static String patchInsertPlan = "http://" + localhost + "/server/insert_plan.php";
    public static String patchSelectPlan = "http://" + localhost + "/server/select_plan.php";

    public static String patchInsertEvent = "http://" + localhost + "/server/insert_event.php";
    public static String patchSelectEvent = "http://" + localhost + "/server/select_event.php";
}
