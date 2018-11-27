package com.example.woo.studentdaily.Subject.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.EditLecturerActivity;
import com.example.woo.studentdaily.Subject.Model.Lecturer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturerFragment extends Fragment {
    private TextView tvName, tvPhone, tvEmail, tvWeb;
    private Button btnUpdate;
    private int idST;
    private ArrayList<Lecturer> lecturers;
    private Lecturer lec;
    private int REQUEST_CALL_PHONE = 0;

    public LecturerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lecturers, container, false);
        Bundle bundle = getArguments();
        idST = bundle.getInt("ID_ST");
        addControls(v);
        addEvents();
        return v;
    }

    private void addControls(View v) {
        tvName = v.findViewById(R.id.tv_name_lecturer);
        tvPhone = v.findViewById(R.id.tv_phone_lecturer);
        tvEmail = v.findViewById(R.id.tv_email_lecturer);
        tvWeb = v.findViewById(R.id.tv_web_lecturer);
        btnUpdate = v.findViewById(R.id.btn_update_lecturer);
        lec = new Lecturer();
        lecturers = new ArrayList<>();
        setInfLecturer();
    }

    private void setInfLecturer() {
        lecturers = Common.getListLecturer(getActivity());
        for (Lecturer lecturer:lecturers){
            if (lecturer.getIdst() == idST){
                lec = lecturer;
                tvName.setText(lecturer.getName());
                if (lecturer.getPhone().isEmpty()){
                    tvPhone.setText("<Chưa có SĐT>");
                }else {
                    tvPhone.setText(lecturer.getPhone());
                    tvPhone.setPaintFlags(tvPhone.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                }

                if (lecturer.getEmail().isEmpty()){
                    tvEmail.setText("<Chưa có Email>");
                }else {
                    tvEmail.setText(lecturer.getEmail());
                    tvEmail.setPaintFlags(tvEmail.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                }

                if (lecturer.getWeb().isEmpty()){
                    tvWeb.setText("<Chưa có Web>");
                }else {
                    tvWeb.setText(lecturer.getWeb());
                    tvWeb.setPaintFlags(tvWeb.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfLecturer();
    }

    private void addEvents() {
        if (!tvPhone.getText().equals("<Chưa có SĐT>")){
            tvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupCall();
                }
            });
        }

        if (!tvEmail.getText().equals("<Chưa có Email>")){
            tvEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupEmail();
                }
            });
        }

        if (!tvWeb.getText().equals("<Chưa có Web>")){
            tvWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupAccessWeb();
                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processUpdate();
            }
        });
    }

    private void processUpdate() {
        Intent mIntent = new Intent(getActivity(), EditLecturerActivity.class);
        mIntent.putExtra("LEC", lec);
        startActivity(mIntent);
    }

    private void popupEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn muốn gửi email đến "+tvEmail.getText());
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail();
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

    protected void sendEmail() {
        String email = tvEmail.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

        try {
            startActivity(Intent.createChooser(intent, "Gửi email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Không có ứng dụng email nào được cài đặt.", Toast.LENGTH_SHORT).show();
        }
    }

    private void popupAccessWeb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn muốn truy cập "+tvWeb.getText());
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                processWeb();
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

    private void processWeb() {
        String url = tvWeb.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void popupCall() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn muốn gọi "+tvPhone.getText());
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                processCall();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            processCall();
        }
        else {
            Toast.makeText(getActivity(), "Bạn chưa cho phép gọi", Toast.LENGTH_LONG).show();
        }
    }

    private void processCall() {
        int checkPermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
            return;
        }
        Uri uri = Uri.parse("tel:"+tvPhone.getText().toString());
        //Log.i("TTD", uri.toString());
        Intent cIntent = new Intent(Intent.ACTION_DIAL);
        cIntent.setData(uri);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(cIntent);
    }

}
