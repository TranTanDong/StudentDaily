package com.example.woo.studentdaily.Server;

public class Server {

    public static String localhost = "192.168.1.14";
    public static String patchInsertUser = "http://" + localhost + "/server/insert_user.php";
    public static String patchSelectUser = "http://" + localhost + "/server/select_user.php";
    public static String patchUpdateUser = "http://" + localhost + "/server/update_user.php";

    public static String patchInsertPlan = "http://" + localhost + "/server/insert_plan.php";
    public static String patchSelectPlan = "http://" + localhost + "/server/select_plan.php";

    public static String patchInsertEvent = "http://" + localhost + "/server/insert_event.php";
    public static String patchSelectEvent = "http://" + localhost + "/server/select_event.php";

    public static String patchInsertSubject = "http://" + localhost + "/server/insert_schoolyear_semester_class_subject_lecturer_study.php";
    public static String patchSelectSubject = "http://" + localhost + "/server/select_subject.php";

    public static String patchSelectLecturer = "http://" + localhost + "/server/select_lecturer.php";
    public static String patchUpdateLecturer = "http://" + localhost + "/server/update_lecturer.php";

    public static String patchSelectClassYear = "http://" + localhost + "/server/select_class_year.php";
    public static String patchUpdateClassYear = "http://" + localhost + "/server/update_subject_schoolyear_semester_class.php";

    public static String patchSelectStudy = "http://" + localhost + "/server/select_study.php";
    public static String patchUpdateStudy = "http://" + localhost + "/server/update_study.php";
}
