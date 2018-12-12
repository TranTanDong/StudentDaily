package com.example.woo.studentdaily.More;

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
import com.example.woo.studentdaily.More.Adapter.StudyAllAdapter;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.AddScheduleStudyActivity;
import com.example.woo.studentdaily.Subject.Model.Study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudyAllActivity extends AppCompatActivity implements StudyAllAdapter.IStudyAll {
    private Toolbar toolbar;
    private TextView tvNoStudyAll;
    private RecyclerView rcvStudyAll;
    private ArrayList<Study> studies;
    private StudyAllAdapter studyAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_all);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        tvNoStudyAll = findViewById(R.id.tv_no_study_all);
        rcvStudyAll = findViewById(R.id.rcv_study_all);
        studies = new ArrayList<>();

        setInfStudyAll();
        rcvStudyAll.setLayoutManager(new LinearLayoutManager(this));
        studyAllAdapter = new StudyAllAdapter(this, studies, Common.getListSubject(StudyAllActivity.this), this);
        rcvStudyAll.setAdapter(studyAllAdapter);
    }

    private void setInfStudyAll() {
        studies = Common.getListStudy(StudyAllActivity.this);

        if (studies.size() > 0){
            tvNoStudyAll.setVisibility(View.INVISIBLE);
        }else {
            tvNoStudyAll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setInfStudyAll();
        studyAllAdapter.notifyDataSetChanged();
    }

    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Tất cả lịch học");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClickItemStudyAll(final int position) {
        final String listAdd[] = {"Sửa lịch học", "Xóa lịch học"};
        AlertDialog.Builder builder = new AlertDialog.Builder(StudyAllActivity.this)
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Sửa lịch học")){
                            Intent mIntent = new Intent(StudyAllActivity.this, AddScheduleStudyActivity.class);
                            mIntent.putExtra("FLAG", "EDIT_STUDY");
                            mIntent.putExtra("STUDY", studies.get(position));
                            startActivity(mIntent);
                        }else if (listAdd[i].equals("Xóa lịch học")){
                            confirmDeletion(studies.get(position).getId());
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmDeletion(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudyAllActivity.this);
        builder.setMessage("Bạn có thật sự muốn xóa?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletionStudy(position);
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

    private void deletionStudy(final int position) {
        final Popup popup = new Popup(StudyAllActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(StudyAllActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteStudy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StudyAllActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_STUDY", response);
                StudyAllActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataStudy(StudyAllActivity.this);
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                setInfStudyAll();
                                studyAllAdapter.notifyDataSetChanged();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudyAllActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("sch_id", String.valueOf(position));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
