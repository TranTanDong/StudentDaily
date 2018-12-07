package com.example.woo.studentdaily.Subject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailScheduleTestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvSubjectDetail, tvFormDetail, tvDayTestDetail, tvTimeTestDetail, tvPlaceDetail, tvNoteTestDetail;

    private Test test;
    public static int REQUEST_CODE_TEST = 3;
    public static int RESULT_CODE_TEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule_test);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        tvSubjectDetail = findViewById(R.id.tv_subject_test_detail);
        tvFormDetail = findViewById(R.id.tv_form_test_detail);
        tvDayTestDetail = findViewById(R.id.tv_day_test_detail);
        tvTimeTestDetail = findViewById(R.id.tv_time_test_detail);
        tvPlaceDetail = findViewById(R.id.tv_place_test_detail);
        tvNoteTestDetail = findViewById(R.id.tv_note_test_detail);

        Intent mIntent = getIntent();
        if (mIntent != null){
            String nameSubject = "";
            ArrayList<Subject> listSubject = Common.getListSubject(getApplicationContext());
            test = (Test) mIntent.getSerializableExtra("TEST");
            for (Subject subject:listSubject){
                if (subject.getId() == test.getIdSubject()){
                    nameSubject = subject.getName();
                }
            }
            tvSubjectDetail.setText(nameSubject);
            tvFormDetail.setText(Common.isForm(test.getIdForm()));
            tvDayTestDetail.setText(Common.moveSlashTo(test.getDayTest().substring(0, 10), "-", "/"));
            tvTimeTestDetail.setText(test.getDayTest().substring(11, 16));
            tvPlaceDetail.setText(test.getPlace());
            tvNoteTestDetail.setText(test.getNote());
        }

    }

    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btn_delete:
                processDelete();
                break;
            case R.id.btn_edit:
                processEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processEdit() {
        Intent mIntent = new Intent(DetailScheduleTestActivity.this, AddScheduleTestActivity.class);
        mIntent.putExtra("FLAG", "EDIT_TEST");
        mIntent.putExtra("NAME_SUBJECT", tvSubjectDetail.getText().toString());
        mIntent.putExtra("TEST", test);
        startActivityForResult(mIntent, REQUEST_CODE_TEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TEST && resultCode == RESULT_CODE_TEST){
            finish();
        }
    }

    private void processDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailScheduleTestActivity.this);
        builder.setMessage("Bạn có thật sự muốn xóa lịch thi này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTest(test.getId());
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

    private void deleteTest(final int id) {
        final Popup popup = new Popup(DetailScheduleTestActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteTest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DELETE_TEST", response);
                DetailScheduleTestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataTest(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                finish();
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
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("ts_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Chi tiết lịch thi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
