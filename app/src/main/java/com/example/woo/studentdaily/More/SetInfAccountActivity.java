package com.example.woo.studentdaily.More;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetInfAccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnUpdateInf;
    private RadioGroup radioGroupSet;
    private RadioButton radioMale, radioFemale;
    private EditText edtNameSet;
    private TextView tvBirthdaySet, tvEmailSet;
    private CircleImageView imgImageSet;

    private int isFemale = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inf_account);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnUpdateInf    = findViewById(R.id.btn_update_inf);
        edtNameSet      = findViewById(R.id.edt_name_set);
        radioGroupSet   = findViewById(R.id.radio_group_set);
        radioFemale     = findViewById(R.id.radio_female_set);
        radioMale       = findViewById(R.id.radio_male_set);
        tvBirthdaySet   = findViewById(R.id.tv_birthday_set);
        tvEmailSet      = findViewById(R.id.tv_email_set);
        imgImageSet     = findViewById(R.id.img_user_set);

        User user = Common.getUser(getApplicationContext());
        edtNameSet.setText(user.getName());
        tvEmailSet.setText(user.getEmail());
        tvBirthdaySet.setText(user.getBirthDay());
        if (user.isGender()){
            radioFemale.isChecked();
        }else radioFemale.isChecked();
    }

    private void addEvents() {
        btnUpdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Xử lý radio giới tính
        // 0 false nữ
        // 1 true nam
        radioGroupSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                if (id == R.id.radio_male){
                    isFemale = 0;
                }else isFemale = 1;
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
