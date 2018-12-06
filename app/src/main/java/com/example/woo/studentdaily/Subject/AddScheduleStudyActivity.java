package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddScheduleStudyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnSubjectStudy, spnDayOfWeekStudy;
    private EditText edtPlaceStudy, edtLessonStudy;
    private ArrayList<String> listDayOfWeek, listSubject;
    private ArrayList<Subject> arrayListSubject;
    private ArrayAdapter<String> adapterDayOfWeek, adapterSubject;

    private String flag = "";
    private String nameSubject = "";
    private Study study = new Study();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_study);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_save){
            processSave();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        spnSubjectStudy = findViewById(R.id.spn_subject_study);
        spnDayOfWeekStudy = findViewById(R.id.spn_day_of_week_study);
        edtPlaceStudy = findViewById(R.id.edt_place_study);
        edtLessonStudy = findViewById(R.id.edt_lesson_study);

        listDayOfWeek = new ArrayList<>();
        listDayOfWeek.add("Thứ 2");
        listDayOfWeek.add("Thứ 3");
        listDayOfWeek.add("Thứ 4");
        listDayOfWeek.add("Thứ 5");
        listDayOfWeek.add("Thứ 6");
        listDayOfWeek.add("Thứ 7");
        listDayOfWeek.add("Chủ nhật");
        adapterDayOfWeek = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listDayOfWeek
        );
        adapterDayOfWeek.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnDayOfWeekStudy.setAdapter(adapterDayOfWeek);

        listSubject = new ArrayList<>();
        arrayListSubject = new ArrayList<>();
        setDataSubject();
        adapterSubject = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSubject
        );
        adapterSubject.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSubjectStudy.setAdapter(adapterSubject);

        Intent mIntent = getIntent();
        flag = mIntent.getStringExtra("FLAG");
        if (flag.equals("ADD_STUDY")){
            String nameSubject = mIntent.getStringExtra("NAME_SUBJECT");

            if (!nameSubject.isEmpty()){
                spnSubjectStudy.setSelection(listSubject.indexOf(nameSubject));
                spnSubjectStudy.setEnabled(false);
            }
        }else if (flag.equals("EDIT_STUDY")){
            setTitle("Sửa lịch học");
            study = (Study) mIntent.getSerializableExtra("STUDY");
            Log.e("DATA_STUDY", study.toString());
            for (Subject i : arrayListSubject){
                if (i.getId() == study.getIdSubject()){
                    nameSubject = i.getName();
                }
            }
            spnSubjectStudy.setSelection(listSubject.indexOf(nameSubject));
            spnSubjectStudy.setEnabled(false);
            spnDayOfWeekStudy.setSelection(listDayOfWeek.indexOf(study.getDayOfWeek()));
            edtPlaceStudy.setText(study.getPlace());
            edtLessonStudy.setText(study.getLesson());
        }

    }

    private void processSave() {
        int idst = 0;
        String nameSubject = spnSubjectStudy.getSelectedItem().toString();
        for (Subject i : arrayListSubject){
            if (i.getName().equals(nameSubject)){
                idst = i.getIdst();
            }
        }
        Log.e("ID_ST_ADD", idst+"");
        String dayOfWeek = spnDayOfWeekStudy.getSelectedItem().toString();
        String place = edtPlaceStudy.getText().toString();
        String lesson = edtLessonStudy.getText().toString();

        if (flag.equals("ADD_STUDY")){
            processAddStudy(idst, dayOfWeek, place, lesson);
        }else if (flag.equals("EDIT_STUDY")){
            Toast.makeText(this, "Edit Study ^^", Toast.LENGTH_SHORT).show();
            processEditStudy(idst, dayOfWeek, place, lesson);
        }else Toast.makeText(this, "No Flag", Toast.LENGTH_SHORT).show();

    }

    private boolean isExists(String dayOfWeek, String lesson){
        ArrayList<Study> listStudy = Common.getListStudy(getApplicationContext());
        for (Study study:listStudy){
            if (study.getDayOfWeek().equals(dayOfWeek) && study.getLesson().equals(lesson)){
                return true;
            }
        }
        return false;
    }

    private void processAddStudy(int idst, String dayOfWeek, String place, String lesson) {
        if (place.isEmpty()){
            Toast.makeText(this, "Chưa nhập phòng", Toast.LENGTH_SHORT).show();
        }else if (lesson.isEmpty()){
            Toast.makeText(this, "Chưa nhập tiết", Toast.LENGTH_SHORT).show();
        }else if (isExists(dayOfWeek, lesson)) {
            Toast.makeText(this, "Trùng lịch học", Toast.LENGTH_SHORT).show();
        } else{
            insertScheduleStudy(String.valueOf(idst), dayOfWeek, place, lesson);
        }
    }

    private void processEditStudy(int idst, String dayOfWeek, String place, String lesson) {
        if (place.isEmpty()){
            Toast.makeText(this, "Chưa nhập phòng", Toast.LENGTH_SHORT).show();
        }else if (lesson.isEmpty()){
            Toast.makeText(this, "Chưa nhập tiết", Toast.LENGTH_SHORT).show();
        }else if (study.getIdst() == idst && study.getDayOfWeek().equals(dayOfWeek) && study.getPlace().equals(place) && study.getLesson().equals(lesson)){
            finish();
        }else if (isExists(dayOfWeek, lesson)) {
            Toast.makeText(this, "Trùng lịch học", Toast.LENGTH_SHORT).show();
        }else{
            updateScheduleStudy(String.valueOf(study.getId()), String.valueOf(idst), dayOfWeek, place, lesson);
        }
    }

    private void updateScheduleStudy(final String id, final String idst, final String dayOfWeek, final String place, final String lesson) {
        final Popup popup = new Popup(AddScheduleStudyActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchUpdateStudy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_STUDY", response);
                AddScheduleStudyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataStudy(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                finish();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("sch_id", id);
                hashMap.put("sch_idstudy", idst);
                hashMap.put("sch_dayofweek", dayOfWeek);
                hashMap.put("sch_place", place);
                hashMap.put("sch_lesson", lesson);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void insertScheduleStudy(final String idst, final String dayOfWeek, final String place, final String lesson) {
        final Popup popup = new Popup(AddScheduleStudyActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertStudy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_STUDY", response);
                AddScheduleStudyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataStudy(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                finish();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("sch_idstudy", idst);
                hashMap.put("sch_dayofweek", dayOfWeek);
                hashMap.put("sch_place", place);
                hashMap.put("sch_lesson", lesson);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setDataSubject() {
        arrayListSubject = Common.getListSubject(getApplicationContext());
        for (Subject i : arrayListSubject){
            listSubject.add(i.getName());
        }
        Log.e("SUBJECT_SIZE", arrayListSubject.size()+"");
        Log.e("NAME_SUBJECT_SIZE", listSubject.size()+"");
    }

    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Lịch học mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
