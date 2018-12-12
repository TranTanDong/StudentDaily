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
import android.widget.ImageView;
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
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Adapter.StudyAdapter;
import com.example.woo.studentdaily.Subject.Adapter.TestAdapter;
import com.example.woo.studentdaily.Subject.AddScheduleStudyActivity;
import com.example.woo.studentdaily.Subject.DetailScheduleTestActivity;
import com.example.woo.studentdaily.Subject.EditClassSemesterYearActivity;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment implements StudyAdapter.IStudy, TestAdapter.ITest {
    private TextView tvSemester, tvYear, tvClass, tvNoStudy, tvNoTest;
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
        tvNoTest = v.findViewById(R.id.tv_no_test);
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
        studyAdapter = new StudyAdapter(getActivity(), studies, this);
        rcvStudy.setAdapter(studyAdapter);

        //Add data Test
        setDataListTest();

        RecyclerView rcvTest = v.findViewById(R.id.rcv_test);
        rcvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        testAdapter = new TestAdapter(getActivity(), tests, Common.getListSubject(getActivity()), this);
        rcvTest.setAdapter(testAdapter);

    }

    private void setDataListStudy() {
        listStudy = Common.getListStudy(getContext());
        studies.clear();
        for (Study study : listStudy){
            if (study.getIdst() == idST){
                studies.add(study);
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
            tvNoTest.setVisibility(View.INVISIBLE);
        }else {
            tvNoTest.setVisibility(View.VISIBLE);
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
        setDataListTest();
        studyAdapter.notifyDataSetChanged();
        testAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItemStudy(final int position) {
        final String listAdd[] = {"Sửa lịch học", "Xóa lịch học"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Sửa lịch học")){
                            Intent mIntent = new Intent(getActivity(), AddScheduleStudyActivity.class);
                            mIntent.putExtra("FLAG", "EDIT_STUDY");
                            mIntent.putExtra("STUDY", studies.get(position));
                            startActivity(mIntent);
                        }else if (listAdd[i].equals("Xóa lịch học")){
                            confirmDeletion(studies.get(position).getId());
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmDeletion(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có thật sự muốn xóa?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletionStudy(position);
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

    private void deletionStudy(final int position) {
        final Popup popup = new Popup(getActivity());
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteStudy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCHEDULE_STUDY", response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataStudy(getActivity());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                setDataListStudy();
                                studyAdapter.notifyDataSetChanged();
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
                hashMap.put("sch_id", String.valueOf(position));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClickItemTest(int position) {
        Intent mIntent = new Intent(getActivity(), DetailScheduleTestActivity.class);
        mIntent.putExtra("TEST", tests.get(position));
        startActivity(mIntent);
    }
}
