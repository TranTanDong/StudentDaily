package com.example.woo.studentdaily.Main.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.woo.studentdaily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    android.support.v7.widget.Toolbar toolbar;
    RecyclerView rcv_listSubject;


    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        //Xử lý toolbar
//        rcv_listSubject = viewSubject.findViewById(R.id.rcv_listSubject);


        return view;
    }

    public static SubjectFragment newInstance() {

        Bundle args = new Bundle();

        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
