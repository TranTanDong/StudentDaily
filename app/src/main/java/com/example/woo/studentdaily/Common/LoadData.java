package com.example.woo.studentdaily.Common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Diary.Model.Diary;
import com.example.woo.studentdaily.Diary.Model.PostDiary;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Lecturer;
import com.example.woo.studentdaily.Subject.Model.Score;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadData {
    public static void loadDataPlan(final Context context) {
        final ArrayList<Plan> plans = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectPlan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Plan plan = new Plan();
                                plan.setId(jsonObject.getInt("id"));
                                plan.setCodeUser(jsonObject.getString("codeuser"));
                                plan.setName(jsonObject.getString("name"));
                                plan.setUpdateDay(jsonObject.getString("updateday"));
                                plans.add(0, plan);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListPlan(plans, context);
                    Log.e("Toast", "Load plan success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Plan", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataDiary(final Context context) {
        final ArrayList<Diary> diaries = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectDiary, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Diary diary = new Diary();
                                diary.setId(jsonObject.getInt("id"));
                                diary.setCodeUser(jsonObject.getString("codeuser"));
                                diary.setName(jsonObject.getString("name"));
                                diary.setCreateDay(jsonObject.getString("updateday"));
                                diaries.add(0, diary);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListDiary(diaries, context);
                    Log.e("Toast", "Load Diary success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Diary", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataPostDiary(final Context context) {
        final ArrayList<PostDiary> postDiaries = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectPostDiary, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                PostDiary postDiary = new PostDiary();
                                postDiary.setId(jsonObject.getInt("id"));
                                postDiary.setIdDiary(jsonObject.getInt("iddiary"));
                                postDiary.setContent(jsonObject.getString("content"));
                                postDiary.setDayCreate(jsonObject.getString("daycreate"));
                                postDiary.setAttach(jsonObject.getString("attach"));
                                postDiaries.add(0, postDiary);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListPostDiary(postDiaries, context);
                    Log.e("Toast", "Load Post Diary success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Post Diary", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataEvent(final Context context) {
        final ArrayList<Event> events = new ArrayList<>();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectEvent, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = mAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Event event = new Event();
                                event.setId(jsonObject.getInt("id"));
                                event.setIdPlan(jsonObject.getInt("idplan"));
                                event.setName(jsonObject.getString("name"));
                                event.setPlace(jsonObject.getString("place"));
                                event.setStartTime(jsonObject.getString("starttime"));
                                event.setEndTime(jsonObject.getString("endtime"));
                                event.setPriority(jsonObject.getInt("priority"));
                                event.setRemind(jsonObject.getInt("remind"));
                                event.setDescribe(jsonObject.getString("describe"));
                                events.add(event);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListEvent(events, context);
                    Log.e("Toast", "Load Event success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Event", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataSubject(final Context context) {
        final ArrayList<Subject> subjects = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectSubject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Subject subject = new Subject();
                                subject.setId(jsonObject.getInt("id"));
                                subject.setName(jsonObject.getString("name"));
                                subject.setCreateDay(jsonObject.getString("createday"));
                                subject.setIdst(jsonObject.getInt("idst"));
                                subjects.add(0, subject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListSubject(subjects, context);
                    Log.e("Toast", "Load Subject success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Subject", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataLecturer(final Context context) {
        final ArrayList<Lecturer> lecturers = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectLecturer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Lecturer lecturer = new Lecturer();
                                lecturer.setId(jsonObject.getInt("id"));
                                lecturer.setName(jsonObject.getString("name"));
                                lecturer.setPhone(jsonObject.getString("phone"));
                                lecturer.setEmail(jsonObject.getString("email"));
                                lecturer.setWeb(jsonObject.getString("web"));
                                lecturer.setIdst(jsonObject.getInt("idst"));
                                lecturers.add(0, lecturer);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListLecturer(lecturers, context);
                    Log.e("Toast", "Load Lecturer success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Lecturer", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataClassYear(final Context context) {
        final ArrayList<ClassYear> classYears = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectClassYear, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                ClassYear classYear = new ClassYear();
                                classYear.setIdClass(jsonObject.getInt("idclass"));
                                classYear.setNameClass(jsonObject.getString("nameclass"));
                                classYear.setIdSemester(jsonObject.getInt("idsemester"));
                                classYear.setNameSemester(jsonObject.getString("namesemester"));
                                classYear.setYear(jsonObject.getInt("year"));
                                classYear.setIdst(jsonObject.getInt("idst"));
                                classYears.add(classYear);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListClassYear(classYears, context);
                    Log.e("Toast", "Load ClassYear success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Class Year", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataStudy(final Context context) {
        final ArrayList<Study> studies = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectStudy, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Study study = new Study();
                                study.setId(jsonObject.getInt("id"));
                                study.setDayOfWeek(jsonObject.getString("dayofweek"));
                                study.setPlace(jsonObject.getString("place"));
                                study.setLesson(jsonObject.getString("lesson"));
                                study.setIdSubject(jsonObject.getInt("idsubject"));
                                study.setIdst(jsonObject.getInt("idst"));
                                studies.add(study);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListStudy(studies, context);
                    Log.e("Toast", "Load Study success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Study", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataTest(final Context context) {
        final ArrayList<Test> tests = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectTest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Test test = new Test();
                                test.setId(jsonObject.getInt("id"));
                                test.setDayTest(jsonObject.getString("daytest"));
                                test.setPlace(jsonObject.getString("place"));
                                test.setIdForm(jsonObject.getInt("idform"));
                                test.setNote(jsonObject.getString("note"));
                                test.setIdSubject(jsonObject.getInt("idsubject"));
                                test.setIdst(jsonObject.getInt("idst"));
                                tests.add(test);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListTest(tests, context);
                    Log.e("Toast", "Load Test success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Test", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataScore(final Context context) {
        final ArrayList<Score> scores = new ArrayList<>();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectScore, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Score score = new Score();
                                score.setId(jsonObject.getInt("id"));
                                score.setScore(jsonObject.getDouble("score"));
                                score.setNote(jsonObject.getString("note"));
                                score.setUpdateDay(jsonObject.getString("updateday"));
                                score.setIdForm(jsonObject.getInt("idform"));
                                score.setIdType(jsonObject.getInt("idtos"));
                                score.setIdst(jsonObject.getInt("idst"));
                                scores.add(score);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListScore(scores, context);
                    Log.e("Toast", "Load Score success");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Score", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataUser(final Context context) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectUser, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = mAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("code").equals(code)){
                                final User user = new User();
                                user.setCode(jsonObject.getString("code"));
                                user.setName(jsonObject.getString("name"));
                                user.setImage(jsonObject.getString("image"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setGender(jsonObject.getString("gender"));
                                user.setBirthDay(jsonObject.getString("birthday"));

                                Common.setCurrentUser(context, user);
                                Log.e("CheckData", user.toString());
                                Log.e("Toast", "Load User success");
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error User", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

}
