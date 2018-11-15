package com.example.woo.studentdaily.Diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.woo.studentdaily.R;
import com.github.clans.fab.FloatingActionButton;

public class ContentDiaryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton btnAddDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_diary);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        Intent mIntent = getIntent();
        String nameDiary = mIntent.getStringExtra("NAME_DIARY");
        setTitle(nameDiary);

        btnAddDiary = findViewById(R.id.btn_add_diary);

    }

    private void addEvents() {
        btnAddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContentDiaryActivity.this, AddContentDiaryActivity.class));
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Tên nhât ký");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
