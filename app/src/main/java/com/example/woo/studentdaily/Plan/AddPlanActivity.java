package com.example.woo.studentdaily.Plan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddPlanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView cvStartDayEvent, cvStartTimeEvent, cvEndDayEvent, cvEndTimeEvent;
    private TextView tvStartDayEvent, tvStartTimeEvent, tvEndDayEvent, tvEndTimeEvent, tvRemindedEvent;
    private EditText edtNameEvent, edtPlaceEvent, edtDescribeEvent;
    private CheckBox cbFullDayEvent;
    private Spinner spnPlan;
    private ImageView btnAddNewPlan;
    private ArrayList<String> plans;
    private ArrayAdapter<String> adapterPlan;

    final boolean ArrayCheckedReminded[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private Calendar calendar = Calendar.getInstance();
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
        cvStartDayEvent = findViewById(R.id.cv_start_day_event);
        cvEndDayEvent   = findViewById(R.id.cv_end_day_event);

        cvStartTimeEvent = findViewById(R.id.cv_start_time_event);
        cvEndTimeEvent   = findViewById(R.id.cv_end_time_event);

        tvStartDayEvent = findViewById(R.id.tv_start_day_event);
        tvEndDayEvent   = findViewById(R.id.tv_end_day_event);

        tvStartTimeEvent = findViewById(R.id.tv_start_time_event);
        tvEndTimeEvent   = findViewById(R.id.tv_end_time_event);

        tvRemindedEvent = findViewById(R.id.tv_reminded_event);

        edtNameEvent     = findViewById(R.id.edt_name_event);
        edtPlaceEvent    = findViewById(R.id.edt_place_event);
        edtDescribeEvent = findViewById(R.id.edt_describe_event);

        cbFullDayEvent   = findViewById(R.id.cb_full_day_event);

        btnAddNewPlan    = findViewById(R.id.btn_add_new_plan);

        plans = new ArrayList<>();
        spnPlan = findViewById(R.id.spn_event_plan);

        adapterPlan = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                plans
        );
        adapterPlan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnPlan.setAdapter(adapterPlan);

//        String s = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //Gán giá trị mặt định
        String s = Common.f_eymmdd.format(calendar.getTime());
        tvStartDayEvent.setText(s);
        tvEndDayEvent.setText(tvStartDayEvent.getText().toString());
        tvStartTimeEvent.setText(stf.format(calendar.getTime()));

        String currentDateandTime = stf.format(new Date());

        Date date = null;
        try {
            date = stf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);

        tvEndTimeEvent.setText(stf.format(calendar.getTime()));
    }

    private void addEvents() {
        //Chọn ngày bắt đầu sự kiện
        cvStartDayEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay(tvStartDayEvent, AddPlanActivity.this);
            }
        });
        //Chọn ngày kết thúc sự kiện
        cvEndDayEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay(tvEndDayEvent, AddPlanActivity.this);
            }
        });
        //Chon thời gian bắt đầu
        cvStartTimeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime(tvStartTimeEvent, AddPlanActivity.this);
            }
        });
        //Chon thời gian kết thúc
        cvEndTimeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime(tvEndTimeEvent, AddPlanActivity.this);
            }
        });
        //Chọn mốc thời gian nhắc nhở
        tvRemindedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processReminded();
            }
        });
        //Xử lý checkbox
        cbFullDayEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cvStartTimeEvent.setVisibility(View.INVISIBLE);
                    cvEndTimeEvent.setVisibility(View.INVISIBLE);
                }else {
                    cvStartTimeEvent.setVisibility(View.VISIBLE);
                    cvEndTimeEvent.setVisibility(View.VISIBLE);
                }
            }
        });
        //Thêm kế hoạch mới
        btnAddNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAddNewPlan();
            }
        });
    }

    private void processAddNewPlan() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_new_plan, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        final TextInputEditText edtAddNewPlan;
        final TextView tvDayAddNewPlan;

        edtAddNewPlan = dialogView.findViewById(R.id.edt_add_new_plan);
        tvDayAddNewPlan = dialogView.findViewById(R.id.tv_day_add_new_plan);

        tvDayAddNewPlan.setText(Common.f_ddmmy.format(calendar.getTime()));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String plan = edtAddNewPlan.getText().toString();
                if (!plan.trim().isEmpty() && !plan.equals(" ")){
                    plans.add(plan);
                    adapterPlan.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                            ArrayCheckedReminded[which] = false;
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
                        tvRemindedEvent.setText(s.substring(0, s.length()-2));
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
                false
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
                String s = Common.f_eymmdd.format(calendar.getTime());
                tv_start_day_plan.setText(s);
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
