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

import java.util.ArrayList;

public class ScoreFragment extends Fragment implements ScoreAdapter.IScore {
    private TextView tvScoreAVG, tvNoScore;
    private RecyclerView rcvNo1;
    private ArrayList<Score> listScore, listScoreNo1;
    private ScoreAdapter scoreAdapterNo1;

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
        tvNoScore = v.findViewById(R.id.tv_no_score);
        rcvNo1 = v.findViewById(R.id.rcv_score);

        listScoreNo1 = new ArrayList<>();
        setDataListScoreNo1();

        rcvNo1.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreAdapterNo1 = new ScoreAdapter(getActivity(), listScoreNo1, "No1", this);
        rcvNo1.setAdapter(scoreAdapterNo1);

    }

    private void addEvents() {

    }

    private void setDataListScoreNo1() {
        listScore = Common.getListScore(getContext());
        listScoreNo1.clear();
        for (Score score : listScore){
            if (score.getIdst() == idST){
                listScoreNo1.add(score);
            }
        }
        if (listScoreNo1.size() > 0){
            tvNoScore.setVisibility(View.INVISIBLE);
        }else tvNoScore.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataListScoreNo1();
        scoreAdapterNo1.notifyDataSetChanged();
    }

    @Override
    public void onClickEditScore(int position, String type) {
        Toast.makeText(getActivity(), "Edit ^^", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickDeleteScore(int position, String type) {
        Toast.makeText(getActivity(), "Delete ^^", Toast.LENGTH_SHORT).show();
    }
}
