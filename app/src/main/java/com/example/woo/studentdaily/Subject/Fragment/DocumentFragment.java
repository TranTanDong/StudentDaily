package com.example.woo.studentdaily.Subject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woo.studentdaily.R;
import com.github.florent37.expansionpanel.ExpansionLayout;

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
//        ExpansionLayout expansionLayout = v.findViewById(R.id.filter_expand);
//        expansionLayout.addListener(new ExpansionLayout.Listener() {
//            @Override
//            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
//
//            }
//        });

        return v;
    }

}
