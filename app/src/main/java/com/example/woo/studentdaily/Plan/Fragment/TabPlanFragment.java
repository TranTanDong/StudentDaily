package com.example.woo.studentdaily.Plan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Plan.Adapter.EventAdapterPlan;
import com.example.woo.studentdaily.Plan.EventDetailsActivity;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TabPlanFragment extends Fragment {
    private TextView tvEventToday;
    private RecyclerView rcvEvent;
    private CalendarView cldEvent;
    private EventAdapterPlan eventAdapterPlan;
    private ArrayList<Event> events;

    public TabPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_plan, container, false);
        addControls(v);
        addEvents();

        return v;
    }

    private void addControls(View v) {
        tvEventToday = v.findViewById(R.id.tv_event_today);
        cldEvent     = v.findViewById(R.id.cld_event);
        rcvEvent     = v.findViewById(R.id.rcv_event);

        events = new ArrayList<>();
        events.add(new Event(1, 1, "Hack Camp", "Đoàn 30 Hotel", "07:00 SA", "05:00 CH", 100, 10, "Đi cùng đám bạn"));
        events.add(new Event(1, 1, "Go home", "ST", "10:00 SA", "05:00 CH", 100, 10, "Đi cùng đám bạn"));
        events.add(new Event(1, 1, "Picnic Camp", "586 Park", "03:00 CH", "05:00 CH", 100, 10, "Đi cùng đám bạn"));
        events.add(new Event(1, 1, "Shopping", "Coopmart Center", "07:00 CH", "05:00 CH", 100, 10, "Đi cùng đám bạn"));
        events.add(new Event(1, 1, "Cafe with friends", "Happy 4", "08:00 CH", "05:00 CH", 100, 10, "Đi cùng đám bạn"));

        rcvEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapterPlan = new EventAdapterPlan(getActivity(), events);
        rcvEvent.setAdapter(eventAdapterPlan);
    }

    private void addEvents() {

        tvEventToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventDetailsActivity.class));
            }
        });

        cldEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                String toDay = d + "/" + (m+1) + "/" + y;
                String getToDay = Common.f_dmy.format(Calendar.getInstance().getTime());
                if (toDay.equals(getToDay)){
                    tvEventToday.setText("Hôm nay");
                } else tvEventToday.setText(toDay);



            }
        });
    }



}
