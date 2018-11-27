package com.example.woo.studentdaily.Subject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.ClassYear;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;
import java.util.List;

public class EditClassSemesterYearActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private Toolbar toolbar;
    private EditText edtNameSubject, edtNameClass;
    private TextView tvYear;
    private Spinner spnSemester;
    private List<String> listSemester;
    private ArrayAdapter<String> adapterSemester;

    private ClassYear classYear;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class_semester_year);
        addToolbar();
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_save){
            processEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processEdit() {
        String nameSubject = edtNameSubject.getText().toString();
        String nameClass = edtNameClass.getText().toString();
        String year = tvYear.getText().toString();
        String nameSemester = spnSemester.getSelectedItem().toString();
        if (nameSubject.trim().equals(subject.getName()) && nameClass.trim().equals(classYear.getNameClass()) && year.equals(classYear.getYear()+"") && nameSemester.equals(classYear.getNameSemester())){
            finish();
        }else if (TextUtils.isEmpty(nameSubject)){
            Toast.makeText(this, "Chưa nhập tên môn học", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(nameClass)){
            Toast.makeText(this, "Chưa nhập tên lớp", Toast.LENGTH_SHORT).show();
        }else {

        }
    }

    private void addControls() {
        listSemester = new ArrayList<>();
        listSemester.add("Học kỳ 1");
        listSemester.add("Học kỳ 2");
        listSemester.add("Học kỳ 3");

        edtNameSubject = findViewById(R.id.edt_name_subject_edit);
        edtNameClass = findViewById(R.id.edt_name_class_edit);
        tvYear = findViewById(R.id.tv_school_year_edit);
        spnSemester = findViewById(R.id.spn_semester_edit);

        adapterSemester = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSemester
        );
        adapterSemester.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnSemester.setAdapter(adapterSemester);

        receiveAndSetInfClassYear();
    }

    private void receiveAndSetInfClassYear() {
        Intent mIntent = getIntent();
        classYear = (ClassYear) mIntent.getSerializableExtra("CLASS_YEAR");
        subject = (Subject) mIntent.getSerializableExtra("EDIT_SUBJECT");
        edtNameSubject.setText(subject.getName());
        edtNameClass.setText(classYear.getNameClass());
        tvYear.setText(classYear.getYear()+"");
        spnSemester.setSelection(listSemester.indexOf(classYear.getNameSemester()));
    }

    private void addEvents() {
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(tvYear, Integer.parseInt(tvYear.getText().toString()));
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
//        Log.i("value is",""+newVal);
    }

    public void show(final TextView tv, int setYear) {

        final Dialog d = new Dialog(EditClassSemesterYearActivity.this);
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
        btnSet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }


    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
