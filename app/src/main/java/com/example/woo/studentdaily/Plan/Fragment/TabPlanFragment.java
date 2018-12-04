package com.example.woo.studentdaily.Plan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Plan.Adapter.EventAdapterPlan;
import com.example.woo.studentdaily.Plan.EventDetailsActivity;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.Plan.PlanDetailsActivity;
import com.example.woo.studentdaily.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TabPlanFragment extends Fragment implements EventAdapterPlan.IEvent {
    private TextView tvEventToday, tvNoEvent;
    private RecyclerView rcvEvent;
    private CalendarView cldEvent;
    private EventAdapterPlan eventAdapterPlan;
    private ArrayList<Event> events, arrayListEvent;

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
        tvNoEvent    = v.findViewById(R.id.tv_no_event);
        cldEvent     = v.findViewById(R.id.cld_event);
        rcvEvent     = v.findViewById(R.id.rcv_event);
        arrayListEvent = new ArrayList<>();
        if (arrayListEvent.size() <= 0){
            LoadData.loadDataEvent(getActivity());
        }
        arrayListEvent = Common.getListEvent(getActivity());

        events = new ArrayList<>();

        rcvEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapterPlan = new EventAdapterPlan(getActivity(), events, this);
        rcvEvent.setAdapter(eventAdapterPlan);
        setListEventInDate(Calendar.getInstance());
    }

    private void addEvents() {
        cldEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                Calendar cal = Calendar.getInstance();
                cal.set(y, m, d);
                setListEventInDate(cal);
            }
        });

    }

    private void setListEventInDate(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        String toDay = Common.f_dmy.format(cal.getTime());
        String getToDay = Common.f_dmy.format(Calendar.getInstance().getTime());
        if (toDay.equals(getToDay)){
            tvEventToday.setText("Hôm nay");
        } else tvEventToday.setText(toDay);

        ArrayList<Event> arrayList = new ArrayList<>();
        arrayListEvent = Common.getListEvent(getActivity());
        for (Event event : arrayListEvent){
            Date dateStart = null;
            try {
                dateStart = Common.f_ymmdd.parse(event.getStartTime().substring(0, 10));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(cal.getTime().compareTo(dateStart) == 0) {
                arrayList.add(event);
            }
        }
        if (arrayList.size() > 0){
            tvNoEvent.setVisibility(View.INVISIBLE);
        }else {
            tvNoEvent.setVisibility(View.VISIBLE);
        }
        eventAdapterPlan.refreshAdapter(arrayList);
    }


    @Override
    public void onItemClickEvent(int position) {
        Intent mIntent = new Intent(getActivity(), EventDetailsActivity.class);
        mIntent.putExtra("ITEM_EVENT", events.get(position));
        startActivity(mIntent);
    }
}
