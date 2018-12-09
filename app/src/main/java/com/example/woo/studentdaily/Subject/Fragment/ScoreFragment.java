package com.example.woo.studentdaily.Subject.Fragment;


import android.app.AlertDialog;
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
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Adapter.ScoreAdapter;
import com.example.woo.studentdaily.Subject.AddScoreActivity;
import com.example.woo.studentdaily.Subject.Model.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreFragment extends Fragment implements ScoreAdapter.IScore {
    private TextView tvScoreAVG, tvNoScoreNo1, tvNoScoreNo2, tvNoScoreNo3;
    private RecyclerView rcvNo1, rcvNo2, rcvNo3;
    private ArrayList<Score> listScore, listScoreNo1, listScoreNo2, listScoreNo3;
    private ScoreAdapter scoreAdapterNo1, scoreAdapterNo2, scoreAdapterNo3;

    private int idST;

    public ScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score, container, false);
        Bundle bundle = getArguments();
        idST = bundle.getInt("ID_ST");
        addControls(v);
        addEvents();
        return v;
    }

    private void addControls(View v) {
        tvScoreAVG = v.findViewById(R.id.tv_score_avg);
        tvNoScoreNo1 = v.findViewById(R.id.tv_no_score_no_1);
        tvNoScoreNo2 = v.findViewById(R.id.tv_no_score_no_2);
        tvNoScoreNo3 = v.findViewById(R.id.tv_no_score_no_3);
        rcvNo1 = v.findViewById(R.id.rcv_score_no_1);
        rcvNo2 = v.findViewById(R.id.rcv_score_no_2);
        rcvNo3 = v.findViewById(R.id.rcv_score_no_3);

        listScore = new ArrayList<>();
        listScoreNo1 = new ArrayList<>();
        listScoreNo2 = new ArrayList<>();
        listScoreNo3 = new ArrayList<>();
        setDataListScore();

        rcvNo1.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreAdapterNo1 = new ScoreAdapter(getActivity(), listScoreNo1, "No1", this);
        rcvNo1.setAdapter(scoreAdapterNo1);

        rcvNo2.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreAdapterNo2 = new ScoreAdapter(getActivity(), listScoreNo2, "No2", this);
        rcvNo2.setAdapter(scoreAdapterNo2);

        rcvNo3.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreAdapterNo3 = new ScoreAdapter(getActivity(), listScoreNo3, "No3", this);
        rcvNo3.setAdapter(scoreAdapterNo3);

    }

    private void addEvents() {

    }

    private void setDataListScore() {
        listScoreNo1.clear();
        listScoreNo2.clear();
        listScoreNo3.clear();
        listScore.clear();
        double avg = 0.0d;
        double point = 0.0d;
        int coefficient = 0;
        listScore = Common.getListScore(getContext());
        listScoreNo1.clear();
        for (Score score : listScore){
            if (score.getIdst() == idST){
                switch (score.getIdType()){
                    case 1:
                        listScoreNo1.add(score);
                        point += score.getScore();
                        coefficient += 1;
                        break;
                    case 2:
                        listScoreNo2.add(score);
                        point += score.getScore()*2;
                        coefficient += 2;
                        break;
                    case 3:
                        listScoreNo3.add(score);
                        point += score.getScore()*3;
                        coefficient += 3;
                        break;
                }

            }
        }

        avg = point/coefficient;
        tvScoreAVG.setText(Common.d_double.format(avg));

        if (listScoreNo1.size() > 0){
            tvNoScoreNo1.setVisibility(View.INVISIBLE);
        }else tvNoScoreNo1.setVisibility(View.VISIBLE);

        if (listScoreNo2.size() > 0){
            tvNoScoreNo2.setVisibility(View.INVISIBLE);
        }else tvNoScoreNo2.setVisibility(View.VISIBLE);

        if (listScoreNo3.size() > 0){
            tvNoScoreNo3.setVisibility(View.INVISIBLE);
        }else tvNoScoreNo3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataListScore();
        scoreAdapterNo1.notifyDataSetChanged();
        scoreAdapterNo2.notifyDataSetChanged();
        scoreAdapterNo3.notifyDataSetChanged();

    }

    @Override
    public void onClickEditScore(int position, String type) {
        switch (type){
            case "No1":
                Toast.makeText(getActivity(), "Edit No1", Toast.LENGTH_SHORT).show();
                processEditScore(listScoreNo1.get(position));
                break;
            case "No2":
                processEditScore(listScoreNo2.get(position));
                Toast.makeText(getActivity(), "Edit No2", Toast.LENGTH_SHORT).show();
                break;
            case "No3":
                processEditScore(listScoreNo3.get(position));
                Toast.makeText(getActivity(), "Edit No3", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void processEditScore(Score score){
        Intent mIntent = new Intent(getActivity(), AddScoreActivity.class);
        mIntent.putExtra("FLAG", "EDIT_SCORE");
        mIntent.putExtra("SCORE", score);
        startActivity(mIntent);
    }

    @Override
    public void onClickDeleteScore(int position, String type) {
        switch (type){
            case "No1":
                processDelete(listScoreNo1.get(position).getId());
                Toast.makeText(getActivity(), "Delete No1", Toast.LENGTH_SHORT).show();
                break;
            case "No2":
                processDelete(listScoreNo2.get(position).getId());
                Toast.makeText(getActivity(), "Delete No2", Toast.LENGTH_SHORT).show();
                break;
            case "No3":
                processDelete(listScoreNo3.get(position).getId());
                Toast.makeText(getActivity(), "Delete No3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void processDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có thật sự muốn xóa điểm này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteScore(position);
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

    private void deleteScore(final int id) {
        final Popup popup = new Popup(getActivity());
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteScore, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DELETE_SCORE", response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataScore(getActivity());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                setDataListScore();
                                scoreAdapterNo1.notifyDataSetChanged();
                                scoreAdapterNo2.notifyDataSetChanged();
                                scoreAdapterNo3.notifyDataSetChanged();
                                popup.hide();
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
                hashMap.put("sco_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
