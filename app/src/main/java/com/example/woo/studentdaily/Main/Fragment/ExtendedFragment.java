package com.example.woo.studentdaily.Main.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.woo.studentdaily.Login.LoginActivity;
import com.example.woo.studentdaily.Login.SetInfAccountActivity;
import com.example.woo.studentdaily.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExtendedFragment extends Fragment {


    public ExtendedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extended, container, false);
        LinearLayout btnLogout = view.findViewById(R.id.btn_log_out);
        LinearLayout btnDetailAccount = view.findViewById(R.id.lnl_inf_account);

        btnDetailAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetInfAccountActivity.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSigout();
            }
        });
        return view;
    }

    //Xử lý đăng xuất
    private void showSigout() {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                dialogInterface.dismiss();
                getActivity().finish();
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

    public static ExtendedFragment newInstance() {

        Bundle args = new Bundle();

        ExtendedFragment fragment = new ExtendedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
