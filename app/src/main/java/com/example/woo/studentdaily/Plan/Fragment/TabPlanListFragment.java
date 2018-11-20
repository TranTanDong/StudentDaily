package com.example.woo.studentdaily.Plan.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.Adapter.PlanAdapter;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.Plan.PlanDetailsActivity;
import com.example.woo.studentdaily.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPlanListFragment extends Fragment implements PlanAdapter.IPlan{
    private RecyclerView rcvPlan;
    private PlanAdapter planAdapter;
    private ArrayList<Plan> listPlan;

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
        listPlan = new ArrayList<>();
        LoadData.loadDataPlan(getActivity(), listPlan);
        listPlan = Common.getListPlan(getActivity());

        rcvPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        planAdapter = new PlanAdapter(getActivity(), listPlan, this);
        rcvPlan.setAdapter(planAdapter);
    }

    private void addEvents() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("LOG_ATTACH", "LOG_ATTACH");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LOG_Start", "LOG_Start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LOG_Resume", "LOG_Resume");
        listPlan.clear();
        listPlan = Common.getListPlan(getActivity());
        Log.i("listPlanSize", listPlan.size() + "");
        planAdapter.refreshAdapter(listPlan);
    }

    @Override
    public void onItemClickPlan(int position) {
        Intent mIntent = new Intent(getActivity(), PlanDetailsActivity.class);
        mIntent.putExtra("ID_PLAN", listPlan.get(position).getId());
        mIntent.putExtra("NAME_PLAN", listPlan.get(position).getName());
        startActivity(mIntent);
    }
}
