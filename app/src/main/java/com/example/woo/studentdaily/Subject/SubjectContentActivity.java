package com.example.woo.studentdaily.Subject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Adapter.PagerAdapterSubject;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;
import java.util.Map;

public class SubjectContentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPagerSubject;
    private TabLayout tabLayoutSubject;
    private FloatingActionMenu btnMenu;
    private FloatingActionButton btnAddScore, btnAddScheduleStudy, btnAddScheduleTest;
    private Subject subject = new Subject();
    private int idst;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SubjectContentActivity.this);
        builder.setMessage("Bạn có muốn xóa tất cả thông tin của môn học này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSubjectAll(idst);
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

    private void deleteSubjectAll(final int id) {
        final Popup popup = new Popup(SubjectContentActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(SubjectContentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteSubjectAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SubjectContentActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response);
                SubjectContentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataSubject(getApplicationContext());
                        LoadData.loadDataScore(getApplicationContext());
                        LoadData.loadDataStudy(getApplicationContext());
                        LoadData.loadDataTest(getApplicationContext());
                        LoadData.loadDataLecturer(getApplicationContext());
                        LoadData.loadDataClassYear(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                finish();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SubjectContentActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("st_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
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
        bundle.putInt("ID_ST", idst);
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
        idst = subject.getIdst();
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
