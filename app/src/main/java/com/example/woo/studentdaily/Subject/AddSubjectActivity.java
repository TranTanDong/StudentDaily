package com.example.woo.studentdaily.Subject;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.NumberPicker.*;

public class AddSubjectActivity extends AppCompatActivity implements OnValueChangeListener {
    private Toolbar toolbar;
    private ImageView btnColorSubject;
    private EditText edtNameSubject, edtNameClass;
    private TextView tvSchoolYear;
    private Spinner spnSemester;
    private TextInputEditText edtNameLecturer, edtPhoneLecturer, edtEmailLecturer, edtWebLecturer;
    private List<String> listSemester;
    private ArrayAdapter<String> adapterSemester;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        listSemester = new ArrayList<>();
        listSemester.add("Học kỳ 1");
        listSemester.add("Học kỳ 2");
        listSemester.add("Học kỳ 3");

        mAuth = FirebaseAuth.getInstance();
        btnColorSubject = findViewById(R.id.btn_color_subject);
        edtNameSubject  = findViewById(R.id.edt_name_subject);
        edtNameClass    = findViewById(R.id.edt_name_class);
        edtNameLecturer = findViewById(R.id.edt_name_lecturer);
        edtEmailLecturer = findViewById(R.id.edt_email_lecturer);
        edtPhoneLecturer = findViewById(R.id.edt_phone_lecturer);
        edtWebLecturer   = findViewById(R.id.edt_web_lecturer);
        tvSchoolYear     = findViewById(R.id.tv_school_year);
        spnSemester      = findViewById(R.id.spn_semester);

        adapterSemester = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSemester
        );
        adapterSemester.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSemester.setAdapter(adapterSemester);
    }


    private void addEvents() {
        btnColorSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSubjectActivity.this, SubjectContentActivity.class));
            }
        });

        tvSchoolYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(tvSchoolYear, Integer.parseInt(tvSchoolYear.getText().toString()));
            }
        });
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
            String code = mAuth.getCurrentUser().getUid();
            String nameSJ = edtNameSubject.getText().toString();
            String nameClass = edtNameClass.getText().toString();
            String year = tvSchoolYear.getText().toString();
            String nameSemester = spnSemester.getSelectedItem().toString();
            String nameLec = edtNameLecturer.getText().toString();
            String phoneLec = edtPhoneLecturer.getText().toString();
            String emailLec = edtEmailLecturer.getText().toString();
            String webLec = edtWebLecturer.getText().toString();
            if (TextUtils.isEmpty(nameSJ)){
                Toast.makeText(this, "Chưa nhập tên môn học", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(nameClass)){
                Toast.makeText(this, "Chưa nhập tên lớp", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(nameLec)){
                Toast.makeText(this, "Chưa nhập tên giảng viên", Toast.LENGTH_SHORT).show();
            }else {
                insertDataSubject(code, nameSJ, Common.f_ymmdd.format(Calendar.getInstance().getTime()), year, nameSemester, nameLec, phoneLec, emailLec, webLec, nameClass);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Môn học mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
//        Log.i("value is",""+newVal);
    }

    public void show(final TextView tv, int setYear) {

        final Dialog d = new Dialog(AddSubjectActivity.this);
        d.setTitle("Năm học");
        d.setContentView(R.layout.dialog_year_picker);
        Button btnSet = d.findViewById(R.id.btn_set_sy);
        Button btnCancel = d.findViewById(R.id.btn_cancel_sy);
        final NumberPicker np = d.findViewById(R.id.np_school_year);
        np.setMaxValue(2100);
        np.setMinValue(2000);
        np.setValue(setYear);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        btnSet.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }

    private void insertDataSubject(final String code, final String nameSJ, final String createDaySJ, final String year, final String nameSemester, final String nameLec, final String phoneLec, final String emailLec, final String webLec, final String nameClass) {
        final Popup popup = new Popup(AddSubjectActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertSubject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SUBJECT", response);
                AddSubjectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataSubject(getApplicationContext());
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
                hashMap.put("u_code", code);
                hashMap.put("s_name", nameSJ);
                hashMap.put("s_createday", createDaySJ);
                hashMap.put("sy_id", year);
                hashMap.put("sm_name", nameSemester);
                hashMap.put("l_name", nameLec);
                hashMap.put("l_phone", phoneLec);
                hashMap.put("l_email", emailLec);
                hashMap.put("l_web", webLec);
                hashMap.put("c_name", nameClass);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
