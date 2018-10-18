package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.StudyAdapter;
import com.example.woo.studentdaily.Subject.Model.Study;

import java.util.ArrayList;

public class AddSubjectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView btnColorSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnColorSubject = findViewById(R.id.btn_color_subject);
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
