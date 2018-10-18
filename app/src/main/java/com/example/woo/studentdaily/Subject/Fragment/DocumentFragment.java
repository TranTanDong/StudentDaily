package com.example.woo.studentdaily.Subject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Adapter.StudyAdapter;
import com.example.woo.studentdaily.Subject.Adapter.TestAdapter;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Test;
import com.github.florent37.expansionpanel.ExpansionLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment {
    public DocumentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_document, container, false);


        //Add data Study
        ArrayList<Study> studies = new ArrayList<Study>();
        studies.add(new Study(1, 1, "Thứ 2", "Tiết 1", "202/C1"));
        studies.add(new Study(2, 2, "Thứ 3", "Tiết 1", "203/C1"));
        studies.add(new Study(3, 3, "Thứ 4", "Tiết 1", "204/C1"));
        studies.add(new Study(4, 4, "Thứ 5", "Tiết 1", "205/C1"));

        Log.i("LOG", studies.get(0).getPhongHoc());

        RecyclerView rcvStudy = v.findViewById(R.id.rcv_study);
        rcvStudy.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudyAdapter studyAdapter = new StudyAdapter(getActivity(), studies);
        rcvStudy.setAdapter(studyAdapter);

        //Add data "Lịch thi"
        ArrayList<Test> testList = new ArrayList<>();
        testList.add(new Test(1, 1, 1, "Thi Toán", "22/10/2018", "Đâu cũng được", "Thi tốt nha"));
        testList.add(new Test(2, 2, 2, "Thi Lý", "23/10/2018", "Đâu cũng được", "Thi tốt nha"));
        testList.add(new Test(3, 3, 3, "Thi Hóa", "24/10/2018", "Đâu cũng được", "Thi tốt nha"));
        testList.add(new Test(4, 4, 4, "Thi Sinh", "25/10/2018", "Đâu cũng được", "Thi tốt nha"));
        testList.add(new Test(5, 5, 5, "Thi Sử", "26/10/2018", "Đâu cũng được", "Thi tốt nha"));

        RecyclerView rcvTest = v.findViewById(R.id.rcv_test);
        rcvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        TestAdapter testAdapter = new TestAdapter(getActivity(), testList);
        rcvTest.setAdapter(testAdapter);

        //Add data "Loại tài liệu"
        List<Object> styleDocument = new ArrayList<>();
        styleDocument.add("Tất cả");
        styleDocument.add("Hình ảnh");
        styleDocument.add("File");
        styleDocument.add("Text");
        return v;
    }

}
