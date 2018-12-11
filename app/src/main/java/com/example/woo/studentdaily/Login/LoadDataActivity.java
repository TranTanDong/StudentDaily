package com.example.woo.studentdaily.Login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.R;

public class LoadDataActivity extends AppCompatActivity {
    private SeekBar sbLoad;
    private TextView tvLoad;
    int e = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        sbLoad = findViewById(R.id.sb_load_data);
        tvLoad = findViewById(R.id.tv_percent_load);

        loadDataAll();

        sbLoad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLoad.setText(i + " %");
                if (i == 100){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        CountDownTimer timer = new CountDownTimer(5000, 40) {
            @Override
            public void onTick(long l) {
                if (e < 100){
                    e++;
                }
                sbLoad.setProgress(e);


            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    private void loadDataAll() {
        LoadData.loadDataUser(getApplicationContext());
        LoadData.loadDataPlan(getApplicationContext());
        LoadData.loadDataEvent(getApplicationContext());
        LoadData.loadDataSubject(getApplicationContext());
        LoadData.loadDataClassYear(getApplicationContext());
        LoadData.loadDataLecturer(getApplicationContext());
        LoadData.loadDataTest(getApplicationContext());
        LoadData.loadDataStudy(getApplicationContext());
        LoadData.loadDataScore(getApplicationContext());
        LoadData.loadDataDiary(getApplicationContext());
        LoadData.loadDataPostDiary(getApplicationContext());
    }
}
