package com.example.woo.studentdaily.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.Adapter.PagerAdapterPlan;
import com.example.woo.studentdaily.Plan.AddEventActivity;
import com.example.woo.studentdaily.Plan.Fragment.AddPlanBottomDialogFragment;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment{
    private ViewPager viewPagerPlan;
    private TabLayout tabLayoutPlan;
    private FloatingActionButton btnAddEvent;
    private FloatingActionButton btnAddPlann;
    private FirebaseAuth mAuth;

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
                startActivity(new Intent(getActivity(), AddEventActivity.class));
            }
        });

        btnAddPlann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlanBottomDialogFragment addPlanBottomDialogFragment = new AddPlanBottomDialogFragment();
                addPlanBottomDialogFragment.show(getActivity().getSupportFragmentManager(), "Add Plan Fragment");
            }
        });

    }

    private void addControls(View view) {
        mAuth   = FirebaseAuth.getInstance();
        btnAddEvent = view.findViewById(R.id.btn_add_event_floating);
        btnAddPlann = view.findViewById(R.id.btn_add_plan);
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
