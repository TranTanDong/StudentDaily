package com.example.woo.studentdaily.Plan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Plan.EventDetailsActivity;
import com.example.woo.studentdaily.R;

public class TabPlanFragment extends Fragment {
    private TextView tvToday;
    public TabPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_plan, container, false);
        tvToday = v.findViewById(R.id.tv_today);
        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventDetailsActivity.class));
            }
        });
        return v;
    }

}
