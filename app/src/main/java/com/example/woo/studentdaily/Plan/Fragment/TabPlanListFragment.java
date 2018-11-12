package com.example.woo.studentdaily.Plan.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.Adapter.PlanAdapter;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPlanListFragment extends Fragment {
    private RecyclerView rcvPlan;
    public static PlanAdapter planAdapter;

    public TabPlanListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_plan_list, container, false);
        addControls(v);
        addEvents();
        return v;
    }

    private void addControls(View v) {
        rcvPlan = v.findViewById(R.id.rcv_plan);


        rcvPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        planAdapter = new PlanAdapter(getActivity(), MainActivity.mainPlans);
        rcvPlan.setAdapter(planAdapter);
    }

    private void addEvents() {

    }

}
