package com.example.woo.studentdaily.Diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Diary.Adapter.PostDiaryAdapter;
import com.example.woo.studentdaily.Diary.Model.PostDiary;
import com.example.woo.studentdaily.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class ContentDiaryActivity extends AppCompatActivity implements PostDiaryAdapter.IPostDiary {
    private Toolbar toolbar;
    private RecyclerView rcvContentDiary;
    private TextView tvNoPost;
    private PostDiaryAdapter postDiaryAdapter;
    private ArrayList<PostDiary> postDiaries;


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
        rcvContentDiary = findViewById(R.id.rcv_content_diary);
        tvNoPost = findViewById(R.id.tv_no_post);
        postDiaries = new ArrayList<>();
        setInfListPost();

        rcvContentDiary.setLayoutManager(new LinearLayoutManager(this));
        postDiaryAdapter = new PostDiaryAdapter(this, postDiaries, this);
        rcvContentDiary.setAdapter(postDiaryAdapter);

    }

    private void setInfListPost() {
        postDiaries.clear();
        ArrayList<PostDiary> arrayListPost = Common.getListPostDiary(getApplicationContext());
        for (PostDiary postDiary:arrayListPost){
            if (postDiary.getIdDiary() == idDiary){
                postDiaries.add(postDiary);
            }
        }

        if (postDiaries.size() > 0){
            tvNoPost.setVisibility(View.INVISIBLE);
        }else tvNoPost.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfListPost();
        postDiaryAdapter.notifyDataSetChanged();
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

    @Override
    public void onClickDeletePostDiary(int position) {

    }
}
