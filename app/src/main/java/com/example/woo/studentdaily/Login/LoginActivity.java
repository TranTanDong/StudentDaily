package com.example.woo.studentdaily.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Main.MainActivity;
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
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    public static boolean mFlag = true;
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

    //Nếu đã đăng nhập trước và đã cập nhật thông tin
    private void updateUI(FirebaseUser currentUser, boolean mFlag) {
        if (currentUser != null && mFlag == true){
                Intent mIntent = new Intent(LoginActivity.this, LoadDataActivity.class);
                startActivity(mIntent);
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
            Toast.makeText(this, getResources().getString(R.string.let_enter_password), Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        mAuth       = FirebaseAuth.getInstance();
        btnLogin    = findViewById(R.id.btn_li_login);
        tvToSignUp  = findViewById(R.id.tv_to_sign_up);
        edtEmail    = findViewById(R.id.edt_li_email);
        edtPassword = findViewById(R.id.edt_li_password);
        progressDialog = new ProgressDialog(this);

        tvToSignUp.setPaintFlags(tvToSignUp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
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
        String mEmail = edtEmail.getText().toString().trim();
        String mPass  = edtPassword.getText().toString();
        if (TextUtils.isEmpty(mEmail)){
            Toast.makeText(this, getResources().getString(R.string.email_not_yet), Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(mPass)){
            Toast.makeText(this, getResources().getString(R.string.password_not_yet), Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setMessage(getResources().getString(R.string.authenticating_account));
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.hide();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user, true);
                    }else {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.email_or_password_fail), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
