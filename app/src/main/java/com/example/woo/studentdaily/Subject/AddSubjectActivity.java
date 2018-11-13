package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.StudyAdapter;
import com.example.woo.studentdaily.Subject.Model.Study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSubjectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView btnColorSubject;
    private EditText edtNameSubject, edtNameClass;
    private TextView tvSchoolYear;
    private Spinner spnSemester;
    private TextInputEditText edtNameLecturer, edtPhoneLecturer, edtEmailLecturer, edtWebLecturer;
    private List<String> listSemester;
    private ArrayAdapter<String> adapterSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        listSemester = new ArrayList<>();
        listSemester.add("Học kỳ 1");
        listSemester.add("Học kỳ 2");
        listSemester.add("Học kỳ 3");


        btnColorSubject = findViewById(R.id.btn_color_subject);
        edtNameSubject  = findViewById(R.id.edt_name_subject);
        edtNameClass    = findViewById(R.id.edt_name_class);
        edtNameLecturer = findViewById(R.id.edt_name_lecturer);
        edtEmailLecturer = findViewById(R.id.edt_email_lecturer);
        edtPhoneLecturer = findViewById(R.id.edt_phone_lecturer);
        edtWebLecturer   = findViewById(R.id.edt_web_lecturer);
        tvSchoolYear     = findViewById(R.id.tv_school_year);
        spnSemester      = findViewById(R.id.spn_semester);

        adapterSemester = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSemester
        );
        adapterSemester.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSemester.setAdapter(adapterSemester);
    }


    private void addEvents() {
        btnColorSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSubjectActivity.this, SubjectContentActivity.class));
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Môn học mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
