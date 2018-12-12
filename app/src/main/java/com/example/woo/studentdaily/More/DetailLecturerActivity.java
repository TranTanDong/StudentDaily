package com.example.woo.studentdaily.More;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.EditLecturerActivity;
import com.example.woo.studentdaily.Subject.Model.Lecturer;

public class DetailLecturerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvName, tvPhone, tvEmail, tvWeb;
    private Button btnUpdate;

    private Lecturer lecturer;

    private int REQUEST_CALL_PHONE = 0;
    public static int REQUEST_CODE_EDIT_LECTURER = 19;
    public static int RESULT_CODE_EDIT_LECTURER = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lecturer);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        tvName = findViewById(R.id.tv_name_lecturer_detail);
        tvPhone = findViewById(R.id.tv_phone_lecturer_detail);
        tvEmail = findViewById(R.id.tv_email_lecturer_detail);
        tvWeb = findViewById(R.id.tv_web_lecturer_detail);
        btnUpdate = findViewById(R.id.btn_update_lecturer_detail);

        Intent mIntent = getIntent();
        lecturer = (Lecturer) mIntent.getSerializableExtra("LECTURER");
        setInfLecturer(lecturer);

    }

    private void setInfLecturer(Lecturer lecturer) {

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
        Intent mIntent = new Intent(DetailLecturerActivity.this, EditLecturerActivity.class);
        mIntent.putExtra("LEC", lecturer);
        startActivityForResult(mIntent, REQUEST_CODE_EDIT_LECTURER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_LECTURER && resultCode == RESULT_CODE_EDIT_LECTURER){
            lecturer = (Lecturer) data.getSerializableExtra("LEC_RESULT");
            setInfLecturer(lecturer);
            addEvents();
        }
    }

    private void popupEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailLecturerActivity.this);
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
            Toast.makeText(DetailLecturerActivity.this, "Không có ứng dụng email nào được cài đặt.", Toast.LENGTH_SHORT).show();
        }
    }

    private void popupAccessWeb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailLecturerActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailLecturerActivity.this);
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
            Toast.makeText(DetailLecturerActivity.this, "Bạn chưa cho phép gọi", Toast.LENGTH_LONG).show();
        }
    }

    private void processCall() {
        int checkPermission = ContextCompat.checkSelfPermission(DetailLecturerActivity.this, android.Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailLecturerActivity.this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
            return;
        }
        Uri uri = Uri.parse("tel:"+tvPhone.getText().toString());
        //Log.i("TTD", uri.toString());
        Intent cIntent = new Intent(Intent.ACTION_DIAL);
        cIntent.setData(uri);
        if (ActivityCompat.checkSelfPermission(DetailLecturerActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Thông tin giảng viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
