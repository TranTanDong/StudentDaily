package com.example.woo.studentdaily.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.R;

public class SetInfAccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnUpdateInf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inf_account);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnUpdateInf = findViewById(R.id.btn_update_inf);
    }

    private void addEvents() {
        btnUpdateInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetInfAccountActivity.this, MainActivity.class));
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
