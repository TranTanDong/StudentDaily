package com.example.woo.studentdaily.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvToSignUp;
    private TextInputEditText edtEmail, edtPassword;
    public static boolean mFlag = true;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, mFlag);
    }

    private void updateUI(FirebaseUser currentUser, boolean mFlag) {
        if (currentUser != null && mFlag == true){
            startActivity(new Intent(LoginActivity.this, SetInfAccountActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2 && data != null){
            String mEmail = data.getStringExtra("EMAIL");
            mFlag = false;
            edtEmail.setText(mEmail);
            Toast.makeText(this, "Nhập mật khẩu để đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        mAuth       = FirebaseAuth.getInstance();
        btnLogin    = findViewById(R.id.btn_li_login);
        tvToSignUp  = findViewById(R.id.tv_to_sign_up);
        edtEmail    = findViewById(R.id.edt_li_email);
        edtPassword = findViewById(R.id.edt_li_password);
    }

    private void addEvents() {
        //Nút "Đăng nhập"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               processLogin();
            }
        });

        //Tới Activity "Đăng ký"
        tvToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, SignUpActivity.class), 1);
            }
        });
    }

    private void processLogin() {
        String mEmail = edtEmail.getText().toString();
        String mPass  = edtPassword.getText().toString();
        if (TextUtils.isEmpty(mEmail)){
            Toast.makeText(this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(mPass)){
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user, true);
                    }else
                        Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
