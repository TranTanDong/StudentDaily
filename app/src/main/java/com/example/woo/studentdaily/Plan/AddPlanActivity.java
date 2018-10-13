package com.example.woo.studentdaily.Plan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.woo.studentdaily.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPlanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView cvStartDayPlan, cvStartTimePlan;
    private TextView tvStartDayPlan, tvStartTimePlan, tvReminded;
    private Spinner spnPlan;

    final boolean ArrayCheckedReminded[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
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
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        setTitle("Sự kiện mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addControls() {
        cvStartDayPlan = findViewById(R.id.cv_start_day_plan);
        cvStartTimePlan = findViewById(R.id.cv_start_time_plan);
        tvStartDayPlan = findViewById(R.id.tv_start_day_plan);
        tvStartTimePlan = findViewById(R.id.tv_start_time_plan);
        tvReminded  = findViewById(R.id.tv_reminded);
        spnPlan = findViewById(R.id.spn_plan);

        ArrayAdapter<String> adapterPlan = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.ArrayReminded)
        );
        adapterPlan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnPlan.setAdapter(adapterPlan);


        String s = sdf.format(calendar.getTime());
        String strArr[] = s.split("-");
        tvStartDayPlan.setText("Thứ x, "+strArr[0]+" thg "+ strArr[1] + " "+strArr[2]);

        tvStartTimePlan.setText(stf.format(calendar.getTime()));
    }

    private void addEvents() {

        cvStartDayPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay(tvStartDayPlan, AddPlanActivity.this);
            }
        });

        cvStartTimePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime(tvStartTimePlan, AddPlanActivity.this);
            }
        });

        tvReminded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processReminded();
            }
        });
    }

    private void processReminded() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlanActivity.this)
                .setTitle("Nhắc nhở")
                .setMultiChoiceItems(R.array.ArrayReminded, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        int tmp = 0;
                        for (int i=0; i < ArrayCheckedReminded.length;i++){
                            if (ArrayCheckedReminded[i] == true){
                               tmp++;
                            }
                        }
                        if (tmp>4){
                            Toast.makeText(getApplicationContext(), "Đã chọn sô lượng nhắc nhỡ tối đa", Toast.LENGTH_SHORT).show();
                        }else {
                            ArrayCheckedReminded[which] = isChecked;
                        }

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = "";
                        for (int i=0; i < 14; i++){
                            if (ArrayCheckedReminded[i] == true){
                                s = s + getResources().getStringArray(R.array.ArrayReminded)[i] + ", ";
                            }
                        }
                        tvReminded.setText(s.substring(0, s.length()-2));
                        s = null;
                        for (int i=0; i < ArrayCheckedReminded.length;i++){
                            if (ArrayCheckedReminded[i] == true){
                                ArrayCheckedReminded[i] = false;
                            }
                        }

                        dialog.dismiss();
                    }
                }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void processTime(final TextView tv_start_time_plan, Context context) {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(calendar.MINUTE, minute);
                tv_start_time_plan.setText(stf.format(calendar.getTime()));
            }
        };

        TimePickerDialog timePickerDialog=new TimePickerDialog(
                context,
                callback,
                calendar.get(calendar.HOUR_OF_DAY),
                calendar.get(calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }


    private void processDay(final TextView tv_start_day_plan, Context context) {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                String s = sdf.format(calendar.getTime());
                String strArr[] = s.split("-");
                tv_start_day_plan.setText("Thứ x, "+strArr[0]+" thg "+ strArr[1] + " "+strArr[2]);
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                context,
                callback,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
