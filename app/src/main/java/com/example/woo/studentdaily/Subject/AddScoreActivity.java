package com.example.woo.studentdaily.Subject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddScoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtScore, edtNote;
    private TextView tvSubject;
    private Spinner spnForm, spnTyle;
    private List<String> listForm, listTyle;
    private ArrayAdapter<String> adapterTyle, adapterForm;

    private String flag;

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
            processSave();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        tvSubject = findViewById(R.id.tv_subject_score);
        edtScore = findViewById(R.id.edt_score);
        edtNote = findViewById(R.id.edt_note_score);
        spnForm  = findViewById(R.id.spn_form_score);
        spnTyle = findViewById(R.id.spn_tyle_score);

        listForm = new ArrayList<>();
        listForm.add("Trắc nghiệm");
        listForm.add("Tự luận");
        listForm.add("Thực hành");
        listForm.add("Trắc nghiệm và tự luận");
        listForm.add("Khác");

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


        Intent mIntent = getIntent();
        flag = mIntent.getStringExtra("FLAG");
        if (flag.equals("ADD_SCORE")){
            setTitle("Thêm điểm");
            String nameSubject = mIntent.getStringExtra("NAME_SUBJECT");

            if (!nameSubject.isEmpty()){
                tvSubject.setText(nameSubject);
            }
        }else if (flag.equals("EDIT_SCORE")){
            setTitle("Sửa điểm");
            String nameSubject = mIntent.getStringExtra("NAME_SUBJECT");

            if (!nameSubject.isEmpty()){
                tvSubject.setText(nameSubject);
            }
        }
    }

    private void processSave() {
        int idst = 0;
        int idform = 0;
        String nameSubject = tvSubject.getText().toString();
        ArrayList<Subject> arrayListSubject = Common.getListSubject(getApplicationContext());
        for (Subject i : arrayListSubject){
            if (i.getName().equals(nameSubject)){
                idst = i.getIdst();
            }
        }
        Log.e("ID_ST_ADD", idst+"");
        String form = spnForm.getSelectedItem().toString();
        idform = Common.idForm(form);

        String score = edtScore.getText().toString();
        String type = spnTyle.getSelectedItem().toString();
        int idType = idType(type);
        String updateday = Common.f_ymmddhh.format(Calendar.getInstance().getTime());
        String note = edtNote.getText().toString();

        if (score.isEmpty()){
            Toast.makeText(this, "Chưa nhập điểm", Toast.LENGTH_SHORT).show();
        }else if (Float.parseFloat(score) < 0 || Float.parseFloat(score) > 10) {
            Toast.makeText(this, "Điểm phải >=0 và <=10", Toast.LENGTH_SHORT).show();
        }else {
            if (flag.equals("ADD_SCORE")){
                insertScore(score, note, updateday, String.valueOf(idst), String.valueOf(idType), String.valueOf(idform));
            }else if (flag.equals("EDIT_SCORE")){
//                if (test.getIdst() == idst && test.getIdForm() == idform && test.getDayTest().substring(0, 16).equals(date) && test.getPlace().equals(place) && test.getNote().equals(note)){
//                    setResult(DetailScheduleTestActivity.RESULT_CODE_TEST);
//                    finish();
//                }else updateScheduleTest(String.valueOf(id), String.valueOf(idst), String.valueOf(idform), date, place, note);
            }
        }
    }

    private void insertScore(final String score, final String note, final String updateday, final String idst, final String idtype, final String idform) {
        Toast.makeText(this, "Okê:   "+score+note+updateday+idst+idtype+idform, Toast.LENGTH_SHORT).show();
        final Popup popup = new Popup(AddScoreActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertScore, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.e("DATA_SCORE", response);
                AddScoreActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataScore(getApplicationContext());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                popup.hide();
                                finish();
                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("sco_score", score);
                hashMap.put("sco_note", note);
                hashMap.put("sco_updateday", updateday);
                hashMap.put("sco_idstudy", idst);
                hashMap.put("sco_idtos", idtype);
                hashMap.put("sco_idform", idform);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private int idType(String type){
        switch (type){
            case "Hệ số 1":return 1;
            case "Hệ số 2":return 2;
            case "Hệ số 3":return 3;
            default:return -1;
        }
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
