package com.example.woo.studentdaily.Plan;

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
                if (!TextUtils.isEmpty(namePlan)){
                    insertDataPlan(codeUser, namePlan, dayUpdate);
                }else Toast.makeText(getApplicationContext(), "Hãy nhập tên kế hoạch", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();
        edtNameNewPlan = findViewById(R.id.edt_add_new_plan);
        tvDayAddNewPlan = findViewById(R.id.tv_day_add_new_plan);
        btnOk   = findViewById(R.id.btnOk);
        btnCancel   = findViewById(R.id.btnCancel);

        tvDayAddNewPlan.setText(Common.f_ddmmy.format(Calendar.getInstance().getTime()));
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Kế hoạch mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void insertDataPlan(final String code, final String name, final String updateDay) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertPlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response+name);
                AddPlanActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Plan plan = new Plan();
                        plan.setCodeUser(mAuth.getCurrentUser().getUid());
                        plan.setName(edtNameNewPlan.getText().toString());
                        plan.setUpdateDay(Common.moveSlashTo(Common.f_ddmmy.format(Calendar.getInstance().getTime()), "/", "-"));
                        ArrayList<Plan> plans = Common.getListPlan(getApplicationContext());
                        plans.add(plan);
                        Common.setListPlan(plans, getApplicationContext());
                        MainActivity.loadDataMainPlan(getApplicationContext());
                        finish();
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
                hashMap.put("p_updateday", Common.moveSlashTo(updateDay, "/", "-"));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
