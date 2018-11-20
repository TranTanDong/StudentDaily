package com.example.woo.studentdaily.Subject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.woo.studentdaily.R;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleTestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnSubjectTest, spnFormTest;
    private TextView tvTimeTest;
    private EditText edtPlaceTest, edtNoteTest;
    private List<String> listFormTest;
    private ArrayAdapter<String> adapterFormTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_test);
        addToolbar();
        addControls();
        addEvents();
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

        adapterFormTest = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listFormTest
        );
        adapterFormTest.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnFormTest.setAdapter(adapterFormTest);
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
