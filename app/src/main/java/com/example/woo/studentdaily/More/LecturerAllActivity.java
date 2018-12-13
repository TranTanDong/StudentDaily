package com.example.woo.studentdaily.More;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.More.Adapter.LecturerAdapter;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Lecturer;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import me.aflak.libraries.LecturerFilter;
import me.aflak.utils.Condition;

public class LecturerAllActivity extends AppCompatActivity implements LecturerAdapter.ILecturer {
    private Toolbar toolbar;
    private TextView tvNoLecturer;
    private RecyclerView rcvLecturer;
    private ArrayList<Lecturer> lecturers;
    private LecturerAdapter lecturerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_all);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_only, menu);
        MenuItem item = menu.findItem(R.id.search_only);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lecturerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void addControls() {
        tvNoLecturer = findViewById(R.id.tv_no_lecturer_all);
        rcvLecturer = findViewById(R.id.rcv_lecturer_all);
        lecturers = new ArrayList<>();

        setInfLecturer();
        rcvLecturer.setLayoutManager(new LinearLayoutManager(this));
        lecturerAdapter = new LecturerAdapter(this, lecturers, this);
        rcvLecturer.setAdapter(lecturerAdapter);

    }


    private void setInfLecturer() {
        lecturers = Common.getListLecturer(LecturerAllActivity.this);

        if (lecturers.size() > 0){
            tvNoLecturer.setVisibility(View.INVISIBLE);

        }else {
            tvNoLecturer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setInfLecturer();
        lecturerAdapter.refreshAdapter(lecturers);
    }

    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Tất cả giảng viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClickItemLecturer(int position) {
        Intent mIntent = new Intent(LecturerAllActivity.this, DetailLecturerActivity.class);
        mIntent.putExtra("LECTURER", lecturers.get(position));
        startActivity(mIntent);
    }
}
