package com.example.woo.studentdaily.Plan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.woo.studentdaily.Main.MainActivity;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView cvStartDayEvent, cvStartTimeEvent, cvEndDayEvent, cvEndTimeEvent;
    private TextView tvStartDayEvent, tvStartTimeEvent, tvEndDayEvent, tvEndTimeEvent, tvRemindedEvent, tvPriorityPercent;
    private EditText edtNameEvent, edtPlaceEvent, edtDescribeEvent;
    private CheckBox cbFullDayEvent;
    private SeekBar sbPriority;
    private Spinner spnPlan;

    private int position = 1; //Vị trí click nhắc nhở

    private Map<String, Integer> mapPlan = new HashMap<String, Integer>();
    private ArrayList<String> plans;
    private ArrayAdapter<String> adapterPlan;

    private Calendar calendar = Calendar.getInstance();

    private Calendar calTo = Calendar.getInstance();
    private Calendar calFrom = Calendar.getInstance();

//    private SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
    public static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
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
            processSaveEvent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void processSaveEvent() {
        String nameEvent = edtNameEvent.getText().toString();
        int idPlan = mapPlan.get(spnPlan.getSelectedItem());
        String placeEvent = edtPlaceEvent.getText().toString();
        String dayStart = Common.moveSlashTo(tvStartDayEvent.getText().toString(), "/", "-");
        String timeStart = tvStartTimeEvent.getText().toString();
        String dayTimeStart = dayStart + " " + timeStart;
        String dayEnd = Common.moveSlashTo(tvEndDayEvent.getText().toString(), "/", "-");
        String timeEnd = tvEndTimeEvent.getText().toString();
        String dayTimeEnd = dayEnd + " " + timeEnd;
        int priority = this.sbPriority.getProgress();
        String s = tvRemindedEvent.getText().toString();
        int remind = Integer.parseInt(s.substring(0, s.indexOf(" ")));
        String describe = edtDescribeEvent.getText().toString();
        String ss = idPlan + "\n" + nameEvent + "\n" + placeEvent + "\n" + dayTimeStart + "\n" + dayTimeEnd + "\n" + priority + "\n" + remind + "\n" + describe;
        if (TextUtils.isEmpty(nameEvent)){
            Toast.makeText(this, "Hãy nhập tên sự kiện", Toast.LENGTH_SHORT).show();
        }else {
            Log.i("saveEvent", ss);
            insertDataEvent(idPlan, nameEvent, placeEvent, dayTimeStart, dayTimeEnd, priority, remind, describe);
        }
    }

    private void insertDataEvent(final int idPlan, final String nameEvent, final String placeEvent, final String dayTimeStart, final String dayTimeEnd, final int priority, final int remind, final String describe) {
        final Popup popup = new Popup(AddEventActivity.this);
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertEvent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("DATA_EVENT", response);

                LoadData.loadDataEvent(AddEventActivity.this);
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
                Toast.makeText(AddEventActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddEventActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("e_idplan", String.valueOf(idPlan));
                hashMap.put("e_name", nameEvent);
                hashMap.put("e_place", placeEvent);
                hashMap.put("e_starttime", dayTimeStart);
                hashMap.put("e_endtime", dayTimeEnd);
                hashMap.put("e_priority", String.valueOf(priority));
                hashMap.put("e_remind", String.valueOf(remind));
                hashMap.put("e_describe", describe);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
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
        sbPriority       = findViewById(R.id.sb_priority);
        tvPriorityPercent = findViewById(R.id.tv_priority_percent);

        plans = new ArrayList<>();
        loadDataPlan();
        spnPlan = findViewById(R.id.spn_event_plan);

        adapterPlan = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                plans
        );
        adapterPlan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnPlan.setAdapter(adapterPlan);

        setInfEvent();
    }

    private void setInfEvent() {
        //Gán giá trị mặt định
        calFrom.add(Calendar.HOUR_OF_DAY, 1);
        String s = Common.f_ddmmy.format(calendar.getTime());
        tvStartDayEvent.setText(s);
        tvEndDayEvent.setText(tvStartDayEvent.getText().toString());
        tvStartTimeEvent.setText(stf.format(calendar.getTime()));
        tvPriorityPercent.setText("30 %");

        tvEndTimeEvent.setText(stf.format(calFrom.getTime()));
    }

    private void loadDataPlan() {
        plans.clear();
        mapPlan.clear();
        ArrayList<Plan> listPlan = Common.getListPlan(getApplicationContext());
        for (int i = 0; i < listPlan.size(); i++){
            mapPlan.put(listPlan.get(i).getName(), listPlan.get(i).getId());
            plans.add(listPlan.get(i).getName());
        }
    }

    private void addEvents() {
        //Chọn kế hoạch
        spnPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = plans.get(i);
                int id = mapPlan.get(name);
                Toast.makeText(AddEventActivity.this, name + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Chọn ngày bắt đầu sự kiện
        cvStartDayEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay(tvStartDayEvent, AddEventActivity.this, calTo);
            }
        });
        //Chọn ngày kết thúc sự kiện
        cvEndDayEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay(tvEndDayEvent, AddEventActivity.this, calFrom);
            }
        });
        //Chon thời gian bắt đầu
        cvStartTimeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime(tvStartTimeEvent, AddEventActivity.this, calTo);
            }
        });
        //Chon thời gian kết thúc
        cvEndTimeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTime(tvEndTimeEvent, AddEventActivity.this, calFrom);
            }
        });
        //Chọn mốc thời gian nhắc nhở
        tvRemindedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processReminded(position);
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
        //Xử lý seekbar
        this.sbPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // Khi giá trị progress thay đổi.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                tvPriorityPercent.setText(progressValue + " %");
            }

            // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvPriorityPercent.setText(progress + " %");
            }
        });
    }


    private void processReminded(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this)
                .setTitle("Nhắc nhở")
                .setSingleChoiceItems(getResources().getStringArray(R.array.ArrayReminded), pos, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = which;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvRemindedEvent.setText(getResources().getStringArray(R.array.ArrayReminded)[position]);
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

    private void processTime(final TextView tvStartTimePlan, Context context, final Calendar cal) {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(calendar.MINUTE, minute);
                tvStartTimePlan.setText(stf.format(cal.getTime()));
            }
        };

        TimePickerDialog timePickerDialog=new TimePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                callback,
                cal.get(calendar.HOUR_OF_DAY),
                cal.get(calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }


    private void processDay(final TextView tvStartDayPlan, Context context, final Calendar cal) {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(calendar.YEAR, year);
                cal.set(calendar.MONTH, month);
                cal.set(calendar.DAY_OF_MONTH, dayOfMonth);
                String s = Common.f_ddmmy.format(cal.getTime());
                tvStartDayPlan.setText(s);
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                context,
                callback,
                cal.get(calendar.YEAR),
                cal.get(calendar.MONTH),
                cal.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }
}
