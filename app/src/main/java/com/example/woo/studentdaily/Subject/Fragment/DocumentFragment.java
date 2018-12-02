package com.example.woo.studentdaily.Subject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.StudyAdapter;
import com.example.woo.studentdaily.Subject.Adapter.TestAdapter;
import com.example.woo.studentdaily.Subject.EditClassSemesterYearActivity;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;


import java.util.ArrayList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment {
    private TextView tvSemester, tvYear, tvClass, tvNoStudy;
    private ImageView btnEdit;
    private int idST;
    private ArrayList<ClassYear> classYears;
    private ClassYear classYear;
    private Subject subject;
    private ArrayList<Study> studies;
    private ArrayList<Study> listStudy;
    private StudyAdapter studyAdapter;

    private ArrayList<Test> tests;
    private ArrayList<Test> listTest;
    private TestAdapter testAdapter;

    public static int CODE_REQUEST_EDIT = 1;
    public static int CODE_RESULT_EDIT = 2;
    public DocumentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_document, container, false);
        Bundle bundle = getArguments();
        idST = bundle.getInt("ID_ST");
        subject = (Subject) bundle.getSerializable("OB_SUBJECT");
        addControls(v);
        addEvents();
        return v;
    }

    private void addEvents() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processEdit();
            }
        });
    }

    private void processEdit() {
        Intent mIntent = new Intent(getActivity(), EditClassSemesterYearActivity.class);
        mIntent.putExtra("CLASS_YEAR", classYear);
        mIntent.putExtra("EDIT_SUBJECT", subject);
        startActivityForResult(mIntent, CODE_REQUEST_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST_EDIT && resultCode == CODE_RESULT_EDIT){
            String title = data.getStringExtra("TITLE");
            getActivity().setTitle(title);
        }
    }

    private void addControls(View v) {
        tvSemester = v.findViewById(R.id.tv_semester);
        tvYear = v.findViewById(R.id.tv_year);
        tvClass = v.findViewById(R.id.tv_class);
        tvNoStudy = v.findViewById(R.id.tv_no_study);
        btnEdit = v.findViewById(R.id.btn_edit_class_year);

        classYear = new ClassYear();
        classYears = new ArrayList<>();

        studies = new ArrayList<>();
        listStudy = new ArrayList<>();

        tests = new ArrayList<>();
        listTest = new ArrayList<>();

        setInfClassYear();
        //Add data Study
        setDataListStudy();

        RecyclerView rcvStudy = v.findViewById(R.id.rcv_study);
        rcvStudy.setLayoutManager(new LinearLayoutManager(getActivity()));
        studyAdapter = new StudyAdapter(getActivity(), studies);
        rcvStudy.setAdapter(studyAdapter);

        //Add data Test
        setDataListTest();

        RecyclerView rcvTest = v.findViewById(R.id.rcv_test);
        rcvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        testAdapter = new TestAdapter(getActivity(), tests, Common.getListSubject(getActivity()));
        rcvTest.setAdapter(testAdapter);

        //Add data "Loại tài liệu"
        List<Object> styleDocument = new ArrayList<>();
        styleDocument.add("Tất cả");
        styleDocument.add("Hình ảnh");
        styleDocument.add("File");
        styleDocument.add("Text");


    }

    private void setDataListStudy() {
        listStudy = Common.getListStudy(getContext());
        studies.clear();
        for (Study i : listStudy){
            if (i.getIdst() == idST){
                studies.add(i);
            }
        }
        if (studies.size() > 0){
            tvNoStudy.setVisibility(View.INVISIBLE);
        }else tvNoStudy.setVisibility(View.VISIBLE);
    }

    private void setDataListTest() {
        listTest = Common.getListTest(getContext());
        tests.clear();
        for (Test i : listTest){
            if (i.getIdst() == idST){
                tests.add(i);
            }
        }
        if (tests.size() > 0){
           // tvNoStudy.setVisibility(View.INVISIBLE);
        }else {
            //tvNoStudy.setVisibility(View.VISIBLE);
        }
    }

    private void setInfClassYear() {
        classYears = Common.getListClassYear(getActivity());
        for (ClassYear i:classYears) {
            if (i.getIdst() == idST) {
                classYear = i;
                tvYear.setText(i.getYear()+"");
                tvClass.setText(i.getNameClass());
                tvSemester.setText(i.getNameSemester());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfClassYear();
        setDataListStudy();
        studyAdapter.notifyDataSetChanged();
    }
}
