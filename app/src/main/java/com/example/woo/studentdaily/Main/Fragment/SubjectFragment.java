package com.example.woo.studentdaily.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.SubjectAdapter;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Lecturer;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;
import com.example.woo.studentdaily.Subject.SubjectContentActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment implements SubjectAdapter.ISubject {
    private RecyclerView rcvListSubject;
    private TextView tvNoSubject;
    private SubjectAdapter subjectAdapter;
    private ArrayList<Subject> listSubject;
    private ArrayList<Lecturer> listLecturer;
    private ArrayList<ClassYear> listClassYear;
    private ArrayList<Study> listStudy;
    private ArrayList<Test> listTest;


    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subject, container, false);
        addControls(v);
        addEvents();
        return v;
    }

    private void addControls(View v) {
        tvNoSubject = v.findViewById(R.id.tv_no_subject);
        rcvListSubject = v.findViewById(R.id.rcv_list_subject);
        listSubject = new ArrayList<>();



        rcvListSubject.setLayoutManager(new LinearLayoutManager(getActivity()));
        subjectAdapter = new SubjectAdapter(getActivity(), listSubject, this);
        rcvListSubject.setAdapter(subjectAdapter);
        setInfSubject();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LOG_Resume", "LOG_Resume");
        setInfSubject();
    }

    private void setInfSubject(){
        listSubject.clear();
        listSubject = Common.getListSubject(getActivity());
        if (listSubject.size() > 0){
            tvNoSubject.setVisibility(View.INVISIBLE);
        }else tvNoSubject.setVisibility(View.VISIBLE);
        Log.e("listSubjectSize", listSubject.size() + "");
        subjectAdapter.refreshAdapter(listSubject);
    }

    private void addEvents() {

    }

    public static SubjectFragment newInstance() {

        Bundle args = new Bundle();

        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClickSubject(int position) {
        Intent mIntent = new Intent(getActivity(), SubjectContentActivity.class);
        Subject subject = listSubject.get(position);
        mIntent.putExtra("SUBJECT", subject);
        startActivity(mIntent);
    }

}
