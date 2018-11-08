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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Login.LoginActivity;
import com.example.woo.studentdaily.More.InfoUserActivity;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    private LinearLayout btnLogout;
    private LinearLayout btnDetailAccount;
    private CircleImageView imgImageMore;
    private TextView tvNameMore;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setInfUserFragment();
    }

    private void addControls(View view) {
        btnLogout        = view.findViewById(R.id.btn_log_out);
        btnDetailAccount = view.findViewById(R.id.lnl_inf_account);
        imgImageMore = view.findViewById(R.id.img_image_more);
        tvNameMore       = view.findViewById(R.id.tv_name_more);


        //setInfUserFragment();
    }

    private void setInfUserFragment() {
        User user = Common.getUser(getActivity());
        tvNameMore.setText(user.getName());
        //Load hình
        Glide.with(getActivity()).load(user.getImage())
                .apply(RequestOptions
                        .overrideOf(120, 120)
                        .placeholder(R.drawable.ic_account_circle)
                        .error(R.drawable.ic_account_circle)
                        .formatOf(DecodeFormat.PREFER_RGB_565)
                        .timeout(3000)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(imgImageMore);
    }

    private void addEvents() {
        btnDetailAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), InfoUserActivity.class);
                //mIntent.putExtra("USER", user);
                startActivity(mIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSigout();
            }
        });
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
                Common.setPreferenceNull(getActivity());
                startActivity(new Intent(getActivity(), LoginActivity.class));
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

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
