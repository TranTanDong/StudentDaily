package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.PagerAdapterSubject;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class SubjectContentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPagerSubject;
    private TabLayout tabLayoutSubject;
    private FloatingActionMenu btnMenu;
    private FloatingActionButton btnAddScore, btnAddScheduleStudy, btnAddScheduleTest;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnMenu = findViewById(R.id.floating_action_menu_subject);
        btnAddScore = findViewById(R.id.btn_add_score);
        btnAddScheduleStudy = findViewById(R.id.btn_add_schedule_study);
        btnAddScheduleTest = findViewById(R.id.btn_add_schedule_test);

        receiveDataIntent();
        tabLayoutSubject = findViewById(R.id.tab_layout_subject);
        viewPagerSubject = findViewById(R.id.view_pager_subject);
//        setupViewPager(viewPagerSubject);

        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("TÀI LIỆU"));
        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("ĐIỂM"));
        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("GIẢNG VIÊN"));
        Bundle bundle = new Bundle();
        bundle.putInt("ID_ST", id);

        viewPagerSubject.setAdapter(new PagerAdapterSubject(getSupportFragmentManager(), tabLayoutSubject.getTabCount(), bundle));
        viewPagerSubject.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutSubject));
        tabLayoutSubject.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerSubject.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

//    private void setupViewPager(final ViewPager viewPager) {
//        PagerAdapterSubject adapter = new PagerAdapterSubject(getSupportFragmentManager());
//
//        Bundle bundle =new Bundle();
//        bundle.putInt("ID_ST", id);
//
//        DocumentFragment documentFragment = new DocumentFragment();
//        ScoreFragment scoreFragment = new ScoreFragment();
//        LecturerFragment lecturerFragment = new LecturerFragment();
//
//        documentFragment.setArguments(bundle);
//        scoreFragment.setArguments(bundle);
//        lecturerFragment.setArguments(bundle);
//
//        adapter.addFrag(documentFragment,"TÀI LIỆU");
//        adapter.addFrag(scoreFragment,"ĐIỂM");
//        adapter.addFrag(lecturerFragment, "GIẢNG VIÊN");
//        viewPager.setAdapter(adapter);
//
//    }

    private void receiveDataIntent() {
        Intent nIntent = getIntent();
        String name = nIntent.getStringExtra("NAME_SUBJECT");
        id = nIntent.getIntExtra("ID_ST", -1);
        setTitle(name);
        Log.e("IDST", id+"");
    }

    private void addEvents() {
        btnAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectContentActivity.this, AddScoreActivity.class));
            }
        });

        btnAddScheduleStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectContentActivity.this, AddScheduleStudyActivity.class));
            }
        });

        btnAddScheduleTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubjectContentActivity.this, AddScheduleTestActivity.class));
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
