package com.example.woo.studentdaily.Subject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.woo.studentdaily.Plan.AddEventActivity;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddScheduleTestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnSubjectTest, spnFormTest;
    private TextView tvDayTest, tvTimeTest;
    private EditText edtPlaceTest, edtNoteTest;
    private ArrayList<String> listFormTest, listSubject;
    private ArrayAdapter<String> adapterFormTest, adapterSubject;
    private ArrayList<Subject> arrayListSubject;

    private String flag;
    private int id;
    private Test test;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_test);
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

    private void processSave() {
        int idst = 0;
        int idform = 0;
        String nameSubject = spnSubjectTest.getSelectedItem().toString();
        for (Subject i : arrayListSubject){
            if (i.getName().equals(nameSubject)){
                idst = i.getIdst();
            }
        }
        Log.e("ID_ST_ADD", idst+"");
        String form = spnFormTest.getSelectedItem().toString();
        switch (form){
            case "Trắc nghiệm":
                idform = 1;
                break;
            case "Tự luận":
                idform = 2;
                break;
            case "Thực hành":
                idform = 3;
                break;
            case "Trắc nghiệm và tự luận":
                idform = 4;
                break;
            case "Khác":
                idform = 5;
                break;
            default:
                idform = -1;
                break;
        }

        String daytest = tvDayTest.getText().toString();
        String timetest = tvTimeTest.getText().toString();
        String date = Common.moveSlashTo(daytest, "/", "-") + " " + timetest;
        String place = edtPlaceTest.getText().toString();
        String note = edtNoteTest.getText().toString();

        if (place.isEmpty()){
            Toast.makeText(this, "Chưa nhập phòng", Toast.LENGTH_SHORT).show();
        }else {
            if (flag.equals("ADD_TEST")){
                if (isExistsDayTest(date)){
                    Toast.makeText(this, "Trùng lịch thi", Toast.LENGTH_SHORT).show();
                }else insertScheduleTest(String.valueOf(idst), String.valueOf(idform), date, place, note);
            }else if (flag.equals("EDIT_TEST")){
                if (test.getIdst() == idst && test.getIdForm() == idform && test.getDayTest().substring(0, 16).equals(date) && test.getPlace().equals(place) && test.getNote().equals(note)){
                    setResult(DetailScheduleTestActivity.RESULT_CODE_TEST);
                    finish();
                }else updateScheduleTest(String.valueOf(id), String.valueOf(idst), String.valueOf(idform), date, place, note);
            }
        }
    }

    private boolean isExistsDayTest(String date){
        ArrayList<Test> listTest = Common.getListTest(getApplicationContext());
        for(Test test:listTest){
            if (test.getDayTest().substring(0, 16).equals(date)){
                return true;
            }
        }
        return false;
    }

    private void updateScheduleTest(final String id, final String idst, final String idform, final String date, final String place, final String note) {
        final Popup popup = new Popup(AddScheduleTestActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchUpdateTest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_TEST", response);
                AddScheduleTestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataTest(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                setResult(DetailScheduleTestActivity.RESULT_CODE_TEST);
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
                hashMap.put("ts_id", id);
                hashMap.put("ts_idstudy", idst);
                hashMap.put("ts_idform", idform);
                hashMap.put("ts_daytest", date);
                hashMap.put("ts_place", place);
                hashMap.put("ts_note", note);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void insertScheduleTest(final String s, final String idform, final String daytest, final String place, final String note) {
        final Popup popup = new Popup(AddScheduleTestActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertTest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_TEST", response);
                AddScheduleTestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataTest(getApplicationContext());
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
                hashMap.put("ts_idstudy", s);
                hashMap.put("ts_idform", idform);
                hashMap.put("ts_daytest", daytest);
                hashMap.put("ts_place", place);
                hashMap.put("ts_note", note);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        spnFormTest = findViewById(R.id.spn_form_test);
        spnSubjectTest = findViewById(R.id.spn_subject_test);
        tvDayTest = findViewById(R.id.tv_day_test);
        tvTimeTest = findViewById(R.id.tv_time_test);
        edtPlaceTest = findViewById(R.id.edt_place_test);
        edtNoteTest = findViewById(R.id.edt_note_test);

        tvDayTest.setText(Common.f_ddmmy.format(calendar.getTime()));
        tvTimeTest.setText(AddEventActivity.stf.format(calendar.getTime()));

        listFormTest = new ArrayList<>();
        listFormTest.add("Trắc nghiệm");
        listFormTest.add("Tự luận");
        listFormTest.add("Thực hành");
        listFormTest.add("Trắc nghiệm và tự luận");
        listFormTest.add("Khác");

        adapterFormTest = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listFormTest
        );
        adapterFormTest.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnFormTest.setAdapter(adapterFormTest);

        listSubject = new ArrayList<>();
        arrayListSubject = new ArrayList<>();
        setDataSubject();
        adapterSubject = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSubject
        );
        adapterSubject.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSubjectTest.setAdapter(adapterSubject);


        Intent mIntent = getIntent();
        flag = mIntent.getStringExtra("FLAG");
        if (flag.equals("ADD_TEST")){
            setTitle("Lịch kiểm tra mới");
            String nameSubject = mIntent.getStringExtra("NAME_SUBJECT");

            if (!nameSubject.isEmpty()){
                spnSubjectTest.setSelection(listSubject.indexOf(nameSubject));
                spnSubjectTest.setEnabled(false);
            }
        }else if (flag.equals("EDIT_TEST")){
            setTitle("Sửa lịch kiểm tra");
            String nameSubject = mIntent.getStringExtra("NAME_SUBJECT");
            test = (Test) mIntent.getSerializableExtra("TEST");
            id = test.getId();

            if (!nameSubject.isEmpty()){
                spnSubjectTest.setSelection(listSubject.indexOf(nameSubject));
                spnSubjectTest.setEnabled(false);
            }
            spnFormTest.setSelection(listFormTest.indexOf(Common.isForm(test.getIdForm())));
            tvTimeTest.setText(test.getDayTest().substring(11, 16));
            tvDayTest.setText(Common.moveSlashTo(test.getDayTest().substring(0, 10), "-", "/"));
            edtPlaceTest.setText(test.getPlace());
            edtNoteTest.setText(test.getNote());
        }
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
        tvDayTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDay(tvDayTest, AddScheduleTestActivity.this, calendar);
            }
        });

        tvTimeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processTime(tvTimeTest, AddScheduleTestActivity.this, calendar);
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Lịch kiểm tra mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void processTime(final TextView tvStartTime, Context context, final Calendar cal) {
        String arr[] = tvStartTime.getText().toString().split(":");
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(arr[1]));
        cal.set(Calendar.SECOND, 0);
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(cal.HOUR_OF_DAY, hourOfDay);
                cal.set(cal.MINUTE, minute);
                tvStartTime.setText(AddEventActivity.stf.format(cal.getTime()));
            }
        };

        TimePickerDialog timePickerDialog=new TimePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                callback,
                cal.get(cal.HOUR_OF_DAY),
                cal.get(cal.MINUTE),
                true
        );
        timePickerDialog.show();
    }


    private void processDay(final TextView tvStartDay, Context context, final Calendar cal) {
        String arr[] = tvStartDay.getText().toString().split("/");
        cal.set(Integer.parseInt(arr[2]), Integer.parseInt(arr[1])-1, Integer.parseInt(arr[0]));
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(cal.YEAR, year);
                cal.set(cal.MONTH, month);
                cal.set(cal.DAY_OF_MONTH, dayOfMonth);

                String s = Common.f_ddmmy.format(cal.getTime());
                tvStartDay.setText(s);
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                context,
                callback,
                cal.get(cal.YEAR),
                cal.get(cal.MONTH),
                cal.get(cal.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }
}
