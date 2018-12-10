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
    private int idDiary;

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
        idDiary = mIntent.getIntExtra("ID_DIARY", -1);

        btnAddDiary = findViewById(R.id.btn_add_diary);

    }

    private void addEvents() {
        btnAddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ContentDiaryActivity.this, AddContentDiaryActivity.class);
                mIntent.putExtra("FLAG", "ADD_CONTENT_DIARY");
                mIntent.putExtra("ID_DIARY", idDiary);
                startActivity(mIntent);
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
