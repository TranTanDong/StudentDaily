package com.example.woo.studentdaily.Plan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;

public class EventDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvStartTime, tvEndTime, tvName, tvPlace, tvDescribe, tvRemind, tvPriority;
    private SeekBar sbPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btn_delete:
                processDelete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processDelete() {

    }

    private void addControls() {
        tvStartTime = findViewById(R.id.tv_start_day_detail);
        tvEndTime = findViewById(R.id.tv_end_day_detail);
        tvName = findViewById(R.id.tv_name_event_detail);
        tvPlace = findViewById(R.id.tv_place_event_detail);
        tvDescribe = findViewById(R.id.tv_describe_event_detail);
        tvRemind = findViewById(R.id.tv_remind_event_detail);
        tvPriority = findViewById(R.id.tv_priority_event_detail);
        sbPriority = findViewById(R.id.sb_priority_event_detail);

        setInfEvent();
    }

    private void setInfEvent() {
        Intent mIntent = getIntent();
        Event event = (Event)mIntent.getSerializableExtra("ITEM_EVENT");


        String sDay = event.getStartTime().substring(0, 10);
        String sTime = event.getStartTime().substring(11, 16);

        String eDay = event.getEndTime().substring(0, 10);
        String eTime = event.getEndTime().substring(11, 16);
        if (sDay.equals(eDay)){
            tvStartTime.setText(Common.moveSlashTo(sDay, "-", "/"));
            tvEndTime.setText(sTime + " - " + eTime);
        }else {
            tvStartTime.setText("Từ " + sTime + " " + sDay);
            tvEndTime.setText("Đến " + eTime + " " + eDay);
        }
        tvName.setText(event.getName());
        tvPlace.setText(event.getPlace());
        tvRemind.setText(event.getRemind() + " phút");
        tvDescribe.setText(event.getDescribe());
        tvPriority.setText(event.getPriority()+" %");
        sbPriority.setProgress(event.getPriority());
        sbPriority.setEnabled(false);
    }

    private void addEvents() {


    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Chi tiết sự kiện");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
