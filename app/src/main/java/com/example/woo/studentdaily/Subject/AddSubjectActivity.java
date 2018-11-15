package com.example.woo.studentdaily.Subject;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.NumberPicker.*;

public class AddSubjectActivity extends AppCompatActivity implements OnValueChangeListener {
    private Toolbar toolbar;
    private ImageView btnColorSubject;
    private EditText edtNameSubject, edtNameClass;
    private TextView tvSchoolYear;
    private Spinner spnSemester;
    private TextInputEditText edtNameLecturer, edtPhoneLecturer, edtEmailLecturer, edtWebLecturer;
    private List<String> listSemester;
    private ArrayAdapter<String> adapterSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        addToolbar();
        addControls();
        addEvents();
    }

    private void addControls() {
        listSemester = new ArrayList<>();
        listSemester.add("Học kỳ 1");
        listSemester.add("Học kỳ 2");
        listSemester.add("Học kỳ 3");


        btnColorSubject = findViewById(R.id.btn_color_subject);
        edtNameSubject  = findViewById(R.id.edt_name_subject);
        edtNameClass    = findViewById(R.id.edt_name_class);
        edtNameLecturer = findViewById(R.id.edt_name_lecturer);
        edtEmailLecturer = findViewById(R.id.edt_email_lecturer);
        edtPhoneLecturer = findViewById(R.id.edt_phone_lecturer);
        edtWebLecturer   = findViewById(R.id.edt_web_lecturer);
        tvSchoolYear     = findViewById(R.id.tv_school_year);
        spnSemester      = findViewById(R.id.spn_semester);

        adapterSemester = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSemester
        );
        adapterSemester.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSemester.setAdapter(adapterSemester);
    }


    private void addEvents() {
        btnColorSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSubjectActivity.this, SubjectContentActivity.class));
            }
        });

        tvSchoolYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(tvSchoolYear, Integer.parseInt(tvSchoolYear.getText().toString()));
            }
        });
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Môn học mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
//        Log.i("value is",""+newVal);
    }

    public void show(final TextView tv, int setYear)
    {

        final Dialog d = new Dialog(AddSubjectActivity.this);
        d.setTitle("Năm học");
        d.setContentView(R.layout.dialog_year_picker);
        Button btnSet = d.findViewById(R.id.btn_set_sy);
        Button btnCancel = d.findViewById(R.id.btn_cancel_sy);
        final NumberPicker np = d.findViewById(R.id.np_school_year);
        np.setMaxValue(2100);
        np.setMinValue(2000);
        np.setValue(setYear);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        btnSet.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
}
