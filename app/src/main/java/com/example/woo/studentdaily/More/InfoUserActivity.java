package com.example.woo.studentdaily.More;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNameTopicUser, tvNameUser, tvGenderUser, tvBirthdayUser, tvEmailUser;
    private CircleImageView imgImageUser;
    private Button btnChangeUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setInfUser();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void addControls() {
        imgImageUser    = findViewById(R.id.img_image_user);
        tvNameTopicUser = findViewById(R.id.tv_name_topic_user);
        tvNameUser      = findViewById(R.id.tv_name_user);
        tvGenderUser    = findViewById(R.id.tv_gender_user);
        tvBirthdayUser  = findViewById(R.id.tv_birthday_user);
        tvEmailUser     = findViewById(R.id.tv_email_user);
        btnChangeUser   = findViewById(R.id.btn_change_user);

        setInfUser();

    }

    private void setInfUser() {
        user = Common.getUser(getApplicationContext());
        tvNameTopicUser.setText(user.getName());
        tvNameUser.setText(user.getName());
        tvEmailUser.setText(user.getEmail());
        tvBirthdayUser.setText(Common.moveSlashTo(user.getBirthDay(), "-", "/"));
        if (user.getGender().equals("1")){
            tvGenderUser.setText("Nam");
        }else tvGenderUser.setText("Nữ");

        //Load hình
        Glide.with(getApplicationContext()).load(user.getImage())
                .apply(RequestOptions
                        .overrideOf(120, 120)
                        .placeholder(R.drawable.ic_account_circle)
                        .error(R.drawable.ic_account_circle)
                        .formatOf(DecodeFormat.PREFER_RGB_565)
                        .timeout(3000)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(imgImageUser);
        //Picasso.get().load(dsFruit.get(position).getHinh()).into(holder.img_fImage);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
