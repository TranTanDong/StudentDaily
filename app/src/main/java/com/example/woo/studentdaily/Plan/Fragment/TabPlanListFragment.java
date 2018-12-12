package com.example.woo.studentdaily.Plan.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.Diary.AddDiaryActivity;
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.Adapter.PlanAdapter;
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.Plan.PlanDetailsActivity;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.AddSubjectActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPlanListFragment extends Fragment implements PlanAdapter.IPlan{
    private RecyclerView rcvPlan;
    private TextView tvNoPlan;
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
        tvNoPlan = v.findViewById(R.id.tv_no_plan);
        rcvPlan = v.findViewById(R.id.rcv_plan);
        listPlan = new ArrayList<>();
        listPlan = Common.getListPlan(getActivity());


        rcvPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        planAdapter = new PlanAdapter(getActivity(), listPlan, this);
        rcvPlan.setAdapter(planAdapter);
        setDataListPlan();
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
        setDataListPlan();
    }

    private void setDataListPlan(){
        listPlan.clear();
        listPlan = Common.getListPlan(getActivity());
        if (listPlan.size() > 0){
            tvNoPlan.setVisibility(View.INVISIBLE);
        }else tvNoPlan.setVisibility(View.VISIBLE);
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

    @Override
    public void onItemLongClickPlan(final int position) {
        final String listAdd[] = {"Sửa kế hoạch", "Xóa kế hoạch"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(listPlan.get(position).getName())
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Sửa kế hoạch")){
                            Intent mIntent = new Intent(getActivity(), AddPlanActivity.class);
                            mIntent.putExtra("FLAG_PLAN", "EDIT_PLAN");
                            mIntent.putExtra("PLAN", listPlan.get(position));
                            startActivity(mIntent);
                        }else if (listAdd[i].equals("Xóa kế hoạch")){
                            confirmDeletion(listPlan.get(position).getId());
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmDeletion(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có thật sự muốn xóa?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePlan(id);
            }
        });
        builder.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePlan(final int id) {
        final Popup popup = new Popup(getActivity());
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeletePlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataPlan(getActivity());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                setDataListPlan();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("p_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
