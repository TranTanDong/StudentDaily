package com.example.woo.studentdaily.Diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.Diary.Adapter.PostDiaryAdapter;
import com.example.woo.studentdaily.Diary.Model.PostDiary;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentDiaryActivity extends AppCompatActivity implements PostDiaryAdapter.IPostDiary {
    private Toolbar toolbar;
    private RecyclerView rcvContentDiary;
    private TextView tvNoPost;
    private PostDiaryAdapter postDiaryAdapter;
    private ArrayList<PostDiary> postDiaries;


    private FloatingActionButton btnAddDiary;
    private int idDiary;

    public static int REQUEST_CODE_EDIT_POST = 13;
    public static int RESULT_CODE_EDIT_POST = 14;

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
                mIntent.putExtra("FLAG_POST", "ADD_POST");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_POST && resultCode == RESULT_CODE_EDIT_POST){
            setInfListPost();
            postDiaryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickDeletePostDiary(final int position) {
        final String listAdd[] = {"Sửa bài viết", "Xóa bài viết"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ContentDiaryActivity.this)
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Sửa bài viết")){
                            Intent mIntent = new Intent(ContentDiaryActivity.this, AddContentDiaryActivity.class);
                            mIntent.putExtra("FLAG_POST", "EDIT_POST");
                            mIntent.putExtra("POST", postDiaries.get(position));
                            startActivityForResult(mIntent, REQUEST_CODE_EDIT_POST);
                        }else if (listAdd[i].equals("Xóa bài viết")){
                            confirmDeletion(postDiaries.get(position).getId());
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmDeletion(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContentDiaryActivity.this);
        builder.setMessage("Bạn muốn xóa bài viết này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePost(id);
            }
        });
        builder.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePost(final int id) {
        final Popup popup = new Popup(ContentDiaryActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ContentDiaryActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeletePostDiary, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ContentDiaryActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_POST_DIARY", response);
                ContentDiaryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataPostDiary(ContentDiaryActivity.this);
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                setInfListPost();
                                postDiaryAdapter.notifyDataSetChanged();
                                popup.hide();

                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ContentDiaryActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("dd_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
