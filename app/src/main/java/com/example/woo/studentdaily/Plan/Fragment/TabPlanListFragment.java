package com.example.woo.studentdaily.Plan.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.Plan.Adapter.PlanAdapter;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPlanListFragment extends Fragment {
    private RecyclerView rcvPlan;
    private ArrayList<Plan> plans;
    private PlanAdapter planAdapter;

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

        plans = new ArrayList<>();
        plans.add(new Plan(1, "1", "Mua Smart Phone IPX", "03 thg 11"));
        plans.add(new Plan(1, "1", "Mua xe Exciter 150", "01 thg 11"));
        plans.add(new Plan(1, "1", "Travel", "05 thg 11"));
        plans.add(new Plan(1, "1", "Find my girl", "01 thg 11"));
        plans.add(new Plan(1, "1", "Build my house", "03 thg 11"));

        rcvPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        planAdapter = new PlanAdapter(getActivity(), plans);
        rcvPlan.setAdapter(planAdapter);
    }

    private void addEvents() {

    }

}
