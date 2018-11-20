package com.example.woo.studentdaily.Subject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.woo.studentdaily.R;

import java.util.ArrayList;
import java.util.List;

public class AddScoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtScore;
    private Spinner spnSubject, spnForm, spnTyle;
    private List<String> listForm, listTyle, listSubject;
    private ArrayAdapter<String> adapterTyle, adapterForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
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

        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        edtScore = findViewById(R.id.edt_score);
        spnForm  = findViewById(R.id.spn_form_score);
        spnTyle = findViewById(R.id.spn_tyle_score);
        spnSubject = findViewById(R.id.spn_subject_score);

        listForm = new ArrayList<>();
        listForm.add("Trắc nghiệm");
        listForm.add("Tự luận");
        listForm.add("Thực hành");
        listForm.add("Trắc nghiệm và tự luận");

        listTyle = new ArrayList<>();
        listTyle.add("Hệ số 1");
        listTyle.add("Hệ số 2");
        listTyle.add("Hệ số 3");

        adapterTyle = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listTyle
        );
        adapterTyle.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnTyle.setAdapter(adapterTyle);

        adapterForm = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listForm
        );
        adapterForm.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnForm.setAdapter(adapterForm);

    }

    private void addEvents() {

    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Thêm điểm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
