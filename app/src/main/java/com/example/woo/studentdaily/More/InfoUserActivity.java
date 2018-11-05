package com.example.woo.studentdaily.More;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNameTopicUser, tvNameUser, tvGenderUser, tvBirthdayUser, tvEmailUser;
    private CircleImageView imgImageUser;
    private Button btnChangeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        imgImageUser    = findViewById(R.id.img_image_user);
        tvNameTopicUser = findViewById(R.id.tv_name_topic_user);
        tvNameUser      = findViewById(R.id.tv_name_user);
        tvGenderUser    = findViewById(R.id.tv_gender_user);
        tvBirthdayUser  = findViewById(R.id.tv_birthday_user);
        tvEmailUser     = findViewById(R.id.tv_email_user);
        btnChangeUser   = findViewById(R.id.btn_change_user);

        User user = Common.getUser(getApplicationContext());
        tvNameTopicUser.setText(user.getName());
        tvNameUser.setText(user.getName());
        tvEmailUser.setText(user.getEmail());
        tvBirthdayUser.setText(user.getBirthDay());
        if (user.isGender()){
            tvGenderUser.setText("Nam");
        }else tvGenderUser.setText("Nữ");

        if (!user.getImage().isEmpty()){

        }
    }

    private void addEvents() {
        //Tới trang đổi thông tin User
        btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processChangeUser(); //Xử lý truyền dữ liệu qua trang thay đổi thông tin User
            }
        });
    }

    private void processChangeUser() {
        startActivity(new Intent(this, SetInfAccountActivity.class));
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.info_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
