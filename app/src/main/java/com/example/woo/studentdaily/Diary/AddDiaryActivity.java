package com.example.woo.studentdaily.Diary;

import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddDiaryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText edtNameNewDiary;
    private TextView tvDayAddNewDiary;
    private Button btnOkDiary, btnCancelDiary;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();
        edtNameNewDiary = findViewById(R.id.edt_add_new_diary);
        tvDayAddNewDiary = findViewById(R.id.tv_day_add_new_diary);
        btnOkDiary   = findViewById(R.id.btnOk_diary);
        btnCancelDiary   = findViewById(R.id.btnCancel_diary);

        tvDayAddNewDiary.setText(Common.f_ddmmy.format(Calendar.getInstance().getTime()));
    }

    private void addEvents() {
        btnCancelDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOkDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDiary = edtNameNewDiary.getText().toString();
                String codeUser = mAuth.getCurrentUser().getUid();
                if (!TextUtils.isEmpty(nameDiary)){
                    insertDataDiary(codeUser, nameDiary);
                }else Toast.makeText(getApplicationContext(), "Hãy nhập tên nhật ký", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Nhật ký mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void insertDataDiary(final String code, final String name) {
        final Popup popup = new Popup(AddDiaryActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertDiary, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_DIARY", response);
                AddDiaryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataDiary(AddDiaryActivity.this);
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
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("d_codeuser", code);
                hashMap.put("d_name", name);
                hashMap.put("d_dayupdate", Common.f_ymmddhh.format(Calendar.getInstance().getTime()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
