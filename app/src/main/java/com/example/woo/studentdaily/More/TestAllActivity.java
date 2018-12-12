package com.example.woo.studentdaily.More;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.TestAdapter;
import com.example.woo.studentdaily.Subject.DetailScheduleTestActivity;
import com.example.woo.studentdaily.Subject.Model.Test;

import java.util.ArrayList;

public class TestAllActivity extends AppCompatActivity implements TestAdapter.ITest {
    private Toolbar toolbar;
    private TextView tvNoTestAll;
    private RecyclerView rcvTestAll;
    private TestAdapter testAllAdapter;
    private ArrayList<Test> tests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_all);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        tvNoTestAll = findViewById(R.id.tv_no_test_all);
        rcvTestAll = findViewById(R.id.rcv_test_all);
        tests = new ArrayList<>();
        setInfTestAll();
        rcvTestAll.setLayoutManager(new LinearLayoutManager(this));
        testAllAdapter = new TestAdapter(this, tests, Common.getListSubject(TestAllActivity.this), this);
        rcvTestAll.setAdapter(testAllAdapter);


    }

    private void setInfTestAll() {
        tests = Common.getListTest(TestAllActivity.this);

        if (tests.size() > 0){
            tvNoTestAll.setVisibility(View.INVISIBLE);
        }else {
            tvNoTestAll.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setInfTestAll();
        testAllAdapter.notifyDataSetChanged();
    }


    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Tất cả lịch thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClickItemTest(int position) {
        Intent mIntent = new Intent(TestAllActivity.this, DetailScheduleTestActivity.class);
        mIntent.putExtra("TEST", tests.get(position));
        startActivity(mIntent);
    }
}
