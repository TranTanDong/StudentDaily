package com.example.woo.studentdaily.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.woo.studentdaily.Plan.Adapter.PagerAdapterPlan;
import com.example.woo.studentdaily.Plan.AddEventActivity;
import com.example.woo.studentdaily.R;
import com.github.clans.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private ViewPager viewPagerPlan;
    private TabLayout tabLayoutPlan;
    private FloatingActionButton btnAddEvent;
    private FloatingActionButton btnAddPlann;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_plan, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddEventActivity.class));
            }
        });

        btnAddPlann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Từ từ sẽ có ^^", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addControls(View view) {
        btnAddEvent = view.findViewById(R.id.btn_add_event_floating);
        btnAddPlann = view.findViewById(R.id.btn_add_new_plan);
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
