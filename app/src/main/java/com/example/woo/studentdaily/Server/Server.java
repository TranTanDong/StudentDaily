package com.example.woo.studentdaily.Server;

public class Server {

    public static String localhost = "10.13.128.6";
    public static String patchInsertUser = "http://" + localhost + "/server/insert_user.php";
    public static String patchSelectUser = "http://" + localhost + "/server/select_user.php";
    public static String patchUpdateUser = "http://" + localhost + "/server/update_user.php";

    public static String patchInsertPlan = "http://" + localhost + "/server/insert_plan.php";
    public static String patchSelectPlan = "http://" + localhost + "/server/select_plan.php";
    public static String patchDeletePlan = "http://" + localhost + "/server/delete_plan.php";
    public static String patchUpdatePlan = "http://" + localhost + "/server/update_plan.php";

    public static String patchInsertDiary = "http://" + localhost + "/server/insert_diary.php";
    public static String patchSelectDiary = "http://" + localhost + "/server/select_diary.php";
    public static String patchUpdateDiary = "http://" + localhost + "/server/update_diary.php";
    public static String patchDeleteDiary = "http://" + localhost + "/server/delete_diary.php";

    public static String patchInsertEvent = "http://" + localhost + "/server/insert_event.php";
    public static String patchSelectEvent = "http://" + localhost + "/server/select_event.php";
    public static String patchUpdateEvent = "http://" + localhost + "/server/update_event.php";
    public static String patchDeleteEvent = "http://" + localhost + "/server/delete_event.php";

    public static String patchInsertSubject = "http://" + localhost + "/server/insert_schoolyear_semester_class_subject_lecturer_study.php";
    public static String patchSelectSubject = "http://" + localhost + "/server/select_subject.php";
    public static String patchSelectLecturer = "http://" + localhost + "/server/select_lecturer.php";
    public static String patchUpdateLecturer = "http://" + localhost + "/server/update_lecturer.php";
    public static String patchSelectClassYear = "http://" + localhost + "/server/select_class_year.php";
    public static String patchUpdateClassYear = "http://" + localhost + "/server/update_subject_schoolyear_semester_class.php";
    public static String patchDeleteSubjectAll = "http://" + localhost + "/server/delete_subject_all.php";

    public static String patchInsertStudy = "http://" + localhost + "/server/insert_schedule_study.php";
    public static String patchSelectStudy = "http://" + localhost + "/server/select_study.php";
    public static String patchUpdateStudy = "http://" + localhost + "/server/update_study.php";
    public static String patchDeleteStudy = "http://" + localhost + "/server/delete_study.php";

    public static String patchSelectTest = "http://" + localhost + "/server/select_test.php";
    public static String patchInsertTest = "http://" + localhost + "/server/insert_test.php";
    public static String patchUpdateTest = "http://" + localhost + "/server/update_test.php";
    public static String patchDeleteTest = "http://" + localhost + "/server/delete_test.php";

    public static String patchSelectScore = "http://" + localhost + "/server/select_score.php";
    public static String patchInsertScore = "http://" + localhost + "/server/insert_score.php";
    public static String patchUpdateScore = "http://" + localhost + "/server/update_score.php";
    public static String patchDeleteScore = "http://" + localhost + "/server/delete_score.php";

    public static String patchSelectPostDiary = "http://" + localhost + "/server/select_post_diary.php";
    public static String patchInsertPostDiary = "http://" + localhost + "/server/insert_post_diary.php";
    public static String patchUpdatePostDiary = "http://" + localhost + "/server/update_post_diary.php";
    public static String patchDeletePostDiary = "http://" + localhost + "/server/delete_post_diary.php";

}