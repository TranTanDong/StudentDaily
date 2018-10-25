package com.example.woo.studentdaily.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnSignUp;
    private TextInputEditText edtFullName, edtEmail, edtPassword, edtRepassword;
    private FirebaseAuth mAuth;

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
        edtEmail        = findViewById(R.id.edt_su_email);
        edtPassword     = findViewById(R.id.edt_su_password);
        edtRepassword   = findViewById(R.id.edt_su_repassword);

    }

    private void signUp(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    showDialogResult("Đăng ký thành công", email);
                }else Toast.makeText(SignUpActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogResult(final String result, final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả đăng ký");
        builder.setMessage(result);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(result.equals("Đăng ký thành công")) {
                    Intent intent = getIntent();
                    intent.putExtra("EMAIL", email);
                    setResult(2, intent);
                    finish();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addEvents() {

         //Xử lý click "Đăng ký"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName    = edtFullName.getText().toString();
                String mEmail   = edtEmail.getText().toString();
                String mPass    = edtPassword.getText().toString();
                String mRepass  = edtRepassword.getText().toString();
                if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPass) || TextUtils.isEmpty(mRepass) || TextUtils.isEmpty(mName)){
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (!mPass.equals(mRepass)){
                    Toast.makeText(SignUpActivity.this, "Nhập lại mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                    edtPassword.setText(null);
                    edtRepassword.setText(null);
                }else signUp(mEmail, mPass);
            }
        });
    }



    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Tài khoản mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
