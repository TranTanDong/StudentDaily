package com.example.woo.studentdaily.Plan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Plan.Adapter.EventAdapterPlan;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlanDetailsActivity extends AppCompatActivity implements EventAdapterPlan.IEvent {
    private Toolbar toolbar;
    private RecyclerView rcvEvent;
    private TextView tvEventEmpty;
    private EventAdapterPlan eventAdapterPlan;
    private ArrayList<Event> events, listEvent;

    private int idPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_plan);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        Intent pIntent = getIntent();
        String name = pIntent.getStringExtra("NAME_PLAN");
        idPlan = pIntent.getIntExtra("ID_PLAN", -1);
        setTitle(name);

        listEvent = new ArrayList<>();
//        LoadData.loadDataEvent(getApplicationContext(), listEvent);
        listEvent = Common.getListEvent(getApplicationContext());

        tvEventEmpty = findViewById(R.id.tv_event_empty);
        rcvEvent = findViewById(R.id.rcv_event_detail_plan);
        events = new ArrayList<>();
        processDataEvent();

        rcvEvent.setLayoutManager(new LinearLayoutManager(this));
        eventAdapterPlan = new EventAdapterPlan(this, events, this);
        rcvEvent.setAdapter(eventAdapterPlan);
    }

    private void processDataEvent() {
        for (Event event : listEvent){
            if (event.getIdPlan() == idPlan){
                events.add(event);
                Log.e("Eventsss: ", event.toString());
            }
        }
    }


    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Danh sách sự kiện");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemClickEvent(int position) {
        Intent mIntent = new Intent(PlanDetailsActivity.this, EventDetailsActivity.class);
        mIntent.putExtra("ITEM_EVENT", events.get(position));
        startActivity(mIntent);
    }
}
