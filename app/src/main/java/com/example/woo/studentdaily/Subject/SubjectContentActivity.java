package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.PagerAdapterSubject;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class SubjectContentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPagerSubject;
    private TabLayout tabLayoutSubject;
    private FloatingActionMenu btnMenu;
    private FloatingActionButton btnAddScore, btnAddScheduleStudy, btnAddScheduleTest;
    private Subject subject = new Subject();
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_update, menu);
        MenuItem item =menu.findItem(R.id.btn_edit);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btn_delete:
                processDelete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void processDelete() {

    }

    private void addControls() {
        btnMenu = findViewById(R.id.floating_action_menu_subject);
        btnAddScore = findViewById(R.id.btn_add_score);
        btnAddScheduleStudy = findViewById(R.id.btn_add_schedule_study);
        btnAddScheduleTest = findViewById(R.id.btn_add_schedule_test);

        receiveDataIntent();
        tabLayoutSubject = findViewById(R.id.tab_layout_subject);
        viewPagerSubject = findViewById(R.id.view_pager_subject);

        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("TÀI LIỆU"));
        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("ĐIỂM"));
        tabLayoutSubject.addTab(tabLayoutSubject.newTab().setText("GIẢNG VIÊN"));
        Bundle bundle = new Bundle();
        bundle.putInt("ID_ST", id);
        bundle.putSerializable("OB_SUBJECT", subject);

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


    private void receiveDataIntent() {
        Intent nIntent = getIntent();
        subject = (Subject) nIntent.getSerializableExtra("SUBJECT");
        id = subject.getIdst();
        setTitle(subject.getName());
        Log.e("ID_ST", subject.getIdst()+"");
    }

    private void addEvents() {
        btnAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SubjectContentActivity.this, AddScoreActivity.class);
                mIntent.putExtra("FLAG", "ADD_SCORE");
                mIntent.putExtra("NAME_SUBJECT", subject.getName());
                startActivity(mIntent);
            }
        });

        btnAddScheduleStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SubjectContentActivity.this, AddScheduleStudyActivity.class);
                mIntent.putExtra("FLAG", "ADD_STUDY");
                mIntent.putExtra("NAME_SUBJECT", subject.getName());
                startActivity(mIntent);
            }
        });

        btnAddScheduleTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SubjectContentActivity.this, AddScheduleTestActivity.class);
                mIntent.putExtra("FLAG", "ADD_TEST");
                mIntent.putExtra("NAME_SUBJECT", subject.getName());
                startActivity(mIntent);
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
