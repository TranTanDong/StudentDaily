package com.example.woo.studentdaily.Plan;

import android.content.Intent;
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
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPlanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText edtNameNewPlan;
    private TextView tvDayAddNewPlan;
    private Button btnOk, btnCancel;
    private FirebaseAuth mAuth;

    private String Flag = "";
    private Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePlan = edtNameNewPlan.getText().toString();
                String codeUser = mAuth.getCurrentUser().getUid();
                String dayUpdate = tvDayAddNewPlan.getText().toString();
                if (Flag.equals("ADD_PLAN")){
                    if (!TextUtils.isEmpty(namePlan)){
                        insertDataPlan(codeUser, namePlan, dayUpdate);
                    }else Toast.makeText(getApplicationContext(), "Hãy nhập tên kế hoạch", Toast.LENGTH_SHORT).show();
                }else if (Flag.equals("EDIT_PLAN")){
                    if (namePlan.equals(plan.getName())){
                        finish();
                    }else if (!TextUtils.isEmpty(namePlan)){
                        editDataPlan(String.valueOf(plan.getId()), namePlan);
                    }else Toast.makeText(getApplicationContext(), "Hãy nhập tên kế hoạch", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    private void editDataPlan(final String id, final String namePlan) {
        final Popup popup = new Popup(AddPlanActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchUpdatePlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response);
                AddPlanActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataPlan(AddPlanActivity.this);
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
                hashMap.put("p_id", id);
                hashMap.put("p_name", namePlan);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();
        edtNameNewPlan = findViewById(R.id.edt_add_new_plan);
        tvDayAddNewPlan = findViewById(R.id.tv_day_add_new_plan);
        btnOk   = findViewById(R.id.btnOk);
        btnCancel   = findViewById(R.id.btnCancel);

        tvDayAddNewPlan.setText(Common.f_ddmmy.format(Calendar.getInstance().getTime()));

        Intent mIntent = getIntent();
        Flag = mIntent.getStringExtra("FLAG_PLAN");
        if (Flag.equals("EDIT_PLAN")){
            setTitle("Sửa kế hoạch");
            plan = (Plan) mIntent.getSerializableExtra("PLAN");
            edtNameNewPlan.setText(plan.getName());
        }else if (Flag.equals("ADD_PLAN")){
            setTitle("Kế hoạch mới");
        }
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Sửa kế hoạch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void insertDataPlan(final String code, final String name, final String updateDay) {
        final Popup popup = new Popup(AddPlanActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertPlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response+name);
                AddPlanActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataPlan(AddPlanActivity.this);
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
                hashMap.put("p_codeuser", code);
                hashMap.put("p_name", name);
                hashMap.put("p_updateday", Common.f_ymmddhh.format(Calendar.getInstance().getTime()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
