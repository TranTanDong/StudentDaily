package com.example.woo.studentdaily.Subject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.ScoreAdapter;
import com.example.woo.studentdaily.Subject.Model.Score;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        setDataListScoreNo1();

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

    private void setDataListScoreNo1() {
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
        setDataListScoreNo1();
        scoreAdapterNo1.notifyDataSetChanged();
        scoreAdapterNo2.notifyDataSetChanged();
        scoreAdapterNo3.notifyDataSetChanged();

    }

    @Override
    public void onClickEditScore(int position, String type) {
        switch (type){
            case "No1":
                Toast.makeText(getActivity(), "Edit No1", Toast.LENGTH_SHORT).show();
                break;
            case "No2":
                Toast.makeText(getActivity(), "Edit No2", Toast.LENGTH_SHORT).show();
                break;
            case "No3":
                Toast.makeText(getActivity(), "Edit No3", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onClickDeleteScore(int position, String type) {
        switch (type){
            case "No1":
                Toast.makeText(getActivity(), "Delete No1", Toast.LENGTH_SHORT).show();
                break;
            case "No2":
                Toast.makeText(getActivity(), "Delete No2", Toast.LENGTH_SHORT).show();
                break;
            case "No3":
                Toast.makeText(getActivity(), "Delete No3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
