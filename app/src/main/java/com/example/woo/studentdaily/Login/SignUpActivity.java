package com.example.woo.studentdaily.Login;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
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
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnSignUp;
    private TextInputEditText edtFullName, edtEmail, edtPassword, edtRepassword;
    private TextView tvBirthDay;
    private RadioGroup radioGroup;
    private int isFemale = 1;

    private FirebaseAuth mAuth;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();

        btnSignUp       = findViewById(R.id.btn_su_sign_up);
        edtFullName     = findViewById(R.id.edt_su_full_name);
        tvBirthDay      = findViewById(R.id.tv_su_birthday);
        edtEmail        = findViewById(R.id.edt_su_email);
        edtPassword     = findViewById(R.id.edt_su_password);
        edtRepassword   = findViewById(R.id.edt_su_repassword);

        radioGroup      = findViewById(R.id.radio_group);

        tvBirthDay.setText(Common.sdf.format(calendar.getTime()));
    }

    private void signUp(final String email, String password, final String fullName, final String birthDay, final int isFemale) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final String code = mAuth.getCurrentUser().getUid();
                    String name = fullName;
                    insertDataUser(code, name, email, isFemale, birthDay);
                    showDialogResult(getResources().getString(R.string.sign_up_success), email);
                    Log.i("ViewData", code + email + fullName + birthDay + isFemale);
                }else Toast.makeText(SignUpActivity.this, getResources().getString(R.string.email_incorrect_formatting), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertDataUser(final String code, final String name, final String email, final int isFemale, final String birthDay) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUpActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA", response+name);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("u_code", code);
                hashMap.put("u_name", name);
                hashMap.put("u_email", email);
                hashMap.put("u_sex", String.valueOf(isFemale));
                hashMap.put("u_birthday", birthDay);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showDialogResult(final String result, final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả đăng ký");
        builder.setMessage(result);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                intent.putExtra("EMAIL", email);
                setResult(2, intent);
                finish();

                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addEvents() {
        //Xử lý radio giới tính
        // 0 false nữ
        // 1 true nam
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                if (id == R.id.radio_male){
                    isFemale = 0;
                }else isFemale = 1;
            }
        });

        // Xử lý chọn ngày sinh
        tvBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processBirthDay();
            }
        });

        // Xử lý click "Đăng ký"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName     = edtFullName.getText().toString();
                String mEmail    = edtEmail.getText().toString();
                String mPass     = edtPassword.getText().toString();
                String mRepass   = edtRepassword.getText().toString();
                String mBirthDay = tvBirthDay.getText().toString();

                if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPass) || TextUtils.isEmpty(mRepass) || TextUtils.isEmpty(mName)){
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_full_inf), Toast.LENGTH_SHORT).show();
                }else if (edtPassword.getText().length() < 6) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.password_min_six_char), Toast.LENGTH_SHORT).show();
                }else if (!mPass.equals(mRepass)){
                        Toast.makeText(SignUpActivity.this, getResources().getString(R.string.re_password_failed), Toast.LENGTH_SHORT).show();
                        edtPassword.setText(null);
                        edtRepassword.setText(null);
                }else signUp(mEmail, mPass, mName, mBirthDay, isFemale);
            }
        });
    }

    private void processBirthDay() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                tvBirthDay.setText(Common.sdf.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                SignUpActivity.this,
                callback,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.new_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
