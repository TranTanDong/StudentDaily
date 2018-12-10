package com.example.woo.studentdaily.Main.Fragment;


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
import com.example.woo.studentdaily.Diary.Adapter.DiaryAdapter;
import com.example.woo.studentdaily.Diary.ContentDiaryActivity;
import com.example.woo.studentdaily.Diary.Model.Diary;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment implements DiaryAdapter.IDiary{
    private RecyclerView rcvDiary;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> listDiary;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        addControls(v);
        addEvents();

        return v;
    }

    private void addControls(View v) {
        rcvDiary = v.findViewById(R.id.rcv_diary);
        listDiary = new ArrayList<>();
        if (listDiary.size() <= 0){
            LoadData.loadDataDiary(getActivity());
        }
        listDiary = Common.getListDiary(getActivity());

//        listDiary.add(new Diary(1, "", "Đã biết sẽ có ngày hôm qua", "11/11/2018"));
//        listDiary.add(new Diary(2, "", "Đã lâu không gặp", "01/11/2018"));
//        listDiary.add(new Diary(3, "", "Ai đằng sau lưng em", "12/11/2018"));
//        listDiary.add(new Diary(4, "", "Anh chẳng phải người em yêu", "13/11/2018"));
//        listDiary.add(new Diary(5, "", "Sắp tới sẽ là ai", "14/11/2018"));

        rcvDiary.setLayoutManager(new LinearLayoutManager(getActivity()));
        diaryAdapter = new DiaryAdapter(getActivity(), listDiary, this);
        rcvDiary.setAdapter(diaryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        listDiary.clear();
        listDiary = Common.getListDiary(getActivity());
        Log.i("listDiarySize", listDiary.size() + "");
        diaryAdapter.refreshAdapter(listDiary);
    }

    private void addEvents() {

    }

    public static DiaryFragment newInstance() {

        Bundle args = new Bundle();

        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClickDiary(int position) {
        Intent mIntent = new Intent(getActivity(), ContentDiaryActivity.class);
        mIntent.putExtra("NAME_DIARY", listDiary.get(position).getName());
        mIntent.putExtra("ID_DIARY", listDiary.get(position).getId());
        startActivity(mIntent);
    }
}
