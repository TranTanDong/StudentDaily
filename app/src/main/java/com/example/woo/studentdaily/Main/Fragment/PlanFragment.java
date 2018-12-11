package com.example.woo.studentdaily.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.Plan.Adapter.PagerAdapterPlan;
import com.example.woo.studentdaily.Plan.AddEventActivity;
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment{
    private ViewPager viewPagerPlan;
    private TabLayout tabLayoutPlan;
    private FloatingActionMenu btnMenuPlan;
    private FloatingActionButton btnAddEvent;
    private FloatingActionButton btnAddPlan;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_plan, container, false);
        addControls(v);
        addEvents();
        return v;
    }


    private void addEvents() {
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), AddEventActivity.class);
                mIntent.putExtra("FLAG_EVENT", "ADD_EVENT");
                startActivity(mIntent);
            }
        });

        btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), AddPlanActivity.class);
                mIntent.putExtra("FLAG_PLAN", "ADD_PLAN");
                startActivity(mIntent);
            }
        });

    }

    private void addControls(View view) {
        btnAddEvent = view.findViewById(R.id.btn_add_event_floating);
        btnAddPlan = view.findViewById(R.id.btn_add_plan);
        btnMenuPlan = view.findViewById(R.id.floating_action_menu);
        tabLayoutPlan = view.findViewById(R.id.tab_layout_plan);
        tabLayoutPlan.addTab(tabLayoutPlan.newTab().setText("SỰ KIỆN"));
        tabLayoutPlan.addTab(tabLayoutPlan.newTab().setText("KẾ HOẠCH"));

        viewPagerPlan = view.findViewById(R.id.view_pager_plan);
        viewPagerPlan.setAdapter(new PagerAdapterPlan(getFragmentManager(), tabLayoutPlan.getTabCount()));
        viewPagerPlan.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutPlan));
        tabLayoutPlan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerPlan.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static PlanFragment newInstance() {

        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
