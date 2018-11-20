package com.example.woo.studentdaily.Subject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.woo.studentdaily.R;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleStudyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnSubjectStudy, spnDayOfWeekStudy;
    private EditText edtPlaceStudy, edtLessonStudy;
    private List<String> listDayOfWeek;
    private ArrayAdapter<String> adapterDayOfWeek;

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
