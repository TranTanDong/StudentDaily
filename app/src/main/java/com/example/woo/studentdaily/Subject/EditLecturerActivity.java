package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
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
import com.example.woo.studentdaily.Subject.Model.Lecturer;

import java.util.HashMap;
import java.util.Map;

public class EditLecturerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText edtNameLecturer, edtPhoneLecturer, edtEmailLecturer, edtWebLecturer;
    private Lecturer lec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lecturer);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_save){
            processSave();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processSave() {
        String name = edtNameLecturer.getText().toString();
        String phone = edtPhoneLecturer.getText().toString();
        String email = edtEmailLecturer.getText().toString();
        String web = edtWebLecturer.getText().toString();
        if (name.equals(lec.getName()) && phone.equals(lec.getPhone()) && email.equals(lec.getEmail()) && web.equals(lec.getWeb())){
            finish();
        }else if (!name.isEmpty()){
            updateLec(lec.getId(), name, phone, email, web);
        }else Toast.makeText(this, "Hãy nhập vào tên giảng viên", Toast.LENGTH_SHORT).show();
    }

    private void updateLec(final int id, final String name, final String phone, final String email, final String web) {
        final Popup popup = new Popup(EditLecturerActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchUpdateLecturer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("UPDATE_LECTURER", response);
                if (response.equals("Success")){
                    LoadData.loadDataLecturer(getApplicationContext());
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
                    Toast.makeText(EditLecturerActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                }else Toast.makeText(EditLecturerActivity.this, "Lỗi Server", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditLecturerActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("l_id", String.valueOf(id));
                hashMap.put("l_name", name);
                hashMap.put("l_phone", phone);
                hashMap.put("l_email", email);
                hashMap.put("l_web", web);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtNameLecturer = findViewById(R.id.edt_name_lecturer_edit);
        edtPhoneLecturer = findViewById(R.id.edt_phone_lecturer_edit);
        edtEmailLecturer = findViewById(R.id.edt_email_lecturer_edit);
        edtWebLecturer = findViewById(R.id.edt_web_lecturer_edit);

        receiveAndSetInfLec();
    }

    private void receiveAndSetInfLec() {
        Intent mIntent = getIntent();
        lec = (Lecturer) mIntent.getSerializableExtra("LEC");
        edtNameLecturer.setText(lec.getName());
        edtPhoneLecturer.setText(lec.getPhone());
        edtEmailLecturer.setText(lec.getEmail());
        edtWebLecturer.setText(lec.getWeb());
    }

    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Cập nhật giảng viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
