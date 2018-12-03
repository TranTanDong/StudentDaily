package com.example.woo.studentdaily.Subject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleTestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnSubjectTest, spnFormTest;
    private TextView tvTimeTest;
    private EditText edtPlaceTest, edtNoteTest;
    private ArrayList<String> listFormTest, listSubject;
    private ArrayAdapter<String> adapterFormTest, adapterSubject;
    private ArrayList<Subject> arrayListSubject;


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

    }

    private void addControls() {
        spnFormTest = findViewById(R.id.spn_form_test);
        spnSubjectTest = findViewById(R.id.spn_subject_test);
        tvTimeTest = findViewById(R.id.tv_time_test);
        edtPlaceTest = findViewById(R.id.edt_place_test);
        edtNoteTest = findViewById(R.id.edt_note_test);

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
        setTitle("Lịch kiểm tra mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
