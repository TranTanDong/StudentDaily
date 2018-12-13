package com.example.woo.studentdaily.Plan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
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
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvStartTime, tvEndTime, tvName, tvPlace, tvDescribe, tvRemind, tvPriority;
    private SeekBar sbPriority;
    private Event event;

    public static int REQUEST_CODE_EVENT = 5;
    public static int RESULT_CODE_EVENT = 6;

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
                processDelete(event.getId());
                break;
            case R.id.btn_edit:
                processEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processEdit() {
        Intent mIntent = new Intent(EventDetailsActivity.this, AddEventActivity.class);
        mIntent.putExtra("FLAG_EVENT", "EDIT_EVENT");
        mIntent.putExtra("EVENT", event);
        startActivityForResult(mIntent, REQUEST_CODE_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EVENT && resultCode == RESULT_CODE_EVENT){
            finish();
        }
    }

    private void processDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
        builder.setMessage("Bạn có muốn xóa sự kiện này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteEvent(position);
            }
        });
        builder.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteEvent(final int id) {
        final Popup popup = new Popup(EventDetailsActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteEvent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("DATA_EVENT", response);

                LoadData.loadDataEvent(EventDetailsActivity.this);
                CountDownTimer timer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        popup.hide();
                        finish();
                    }
                };
                timer.start();
                Toast.makeText(EventDetailsActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventDetailsActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("e_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
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
        event = (Event)mIntent.getSerializableExtra("ITEM_EVENT");


        String sDay = Common.moveSlashTo(event.getStartTime().substring(0, 10), "-", "/");
        String sTime = event.getStartTime().substring(11, 16);

        String eDay = Common.moveSlashTo(event.getEndTime().substring(0, 10), "-", "/");
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
