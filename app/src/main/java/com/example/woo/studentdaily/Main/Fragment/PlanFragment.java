package com.example.woo.studentdaily.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.Login.LoginActivity;
import com.example.woo.studentdaily.Plan.Adapter.PagerAdapter;
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton btn_add_plan;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_plan, container, false);
        btn_add_plan = view.findViewById(R.id.btn_add_plan);
        btn_add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("CƠ BẢN"));
        tabLayout.addTab(tabLayout.newTab().setText("TOÀN BỘ"));

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    public static PlanFragment newInstance() {

        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
