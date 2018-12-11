package com.example.woo.studentdaily.Main;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Diary.AddDiaryActivity;
import com.example.woo.studentdaily.Main.Fragment.DiaryFragment;
import com.example.woo.studentdaily.Main.Fragment.MoreFragment;
import com.example.woo.studentdaily.Main.Fragment.PlanFragment;
import com.example.woo.studentdaily.Main.Fragment.SubjectFragment;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.Plan.AddEventActivity;
import com.example.woo.studentdaily.Plan.Fragment.AddPlanBottomDialogFragment;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.AddSubjectActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private BottomNavigationView mMainNav;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    private RelativeLayout container;
    private boolean internetConnected=true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_subject:
                    setTitle("Môn học");
                    setToolbar(false, false, false, true);
                    fragment = SubjectFragment.newInstance();
                    break;
                case R.id.navigation_plan:
                    setTitle("Kế hoạch");
                    setToolbar(false, false, false, false);
                    fragment = PlanFragment.newInstance();
                    break;
                case R.id.navigation_diary:
                    setTitle("Nhật ký");
                    setToolbar(false, false, false, true);
                    fragment = DiaryFragment.newInstance();
                    break;
                case R.id.navigation_more:
                    setTitle("Xem thêm");
                    setToolbar(false, false, false, false);
                    fragment = MoreFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
            return true;
        }
    };

    private void setToolbar(boolean b, boolean b1, boolean b2, boolean b3) {
        toolbar.getMenu().findItem(R.id.search_subject).setVisible(b);
        toolbar.getMenu().findItem(R.id.search_diary).setVisible(b1);
        toolbar.getMenu().findItem(R.id.search_plan).setVisible(b2);
        toolbar.getMenu().findItem(R.id.add1).setVisible(b3);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        //LoadData.loadDataUser(MainActivity.this);
        addEvents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void addEvents() {

    }

    private void addControls() {

        container = findViewById(R.id.container);
        mMainNav     = findViewById(R.id.navigation);
        mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(mMainNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,PlanFragment.newInstance()).commit();
        setTitle("Kế hoạch");
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        //Gửi dữ liệu mainPlan qua TabPlanListFragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_plan);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add1){
            //startActivity(new Intent(MainActivity.this, AddSubjectActivity.class));
            addSubjectAndDiary();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSubjectAndDiary() {
        final String listAdd[] = {"Thêm môn học", "Thêm nhật ký"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Thêm môn học")){
                            startActivity(new Intent(MainActivity.this, AddSubjectActivity.class));
                        }else if (listAdd[i].equals("Thêm nhật ký")){
                            startActivity(new Intent(MainActivity.this, AddDiaryActivity.class));
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    private void loadDataUser() {
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectUser, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null){
//                    String code = mAuth.getCurrentUser().getUid();
//                    for (int i=0; i<response.length(); i++){
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            if (jsonObject.getString("code").equals(code)){
//                                user.setCode(jsonObject.getString("code"));
//                                user.setName(jsonObject.getString("name"));
//                                user.setImage(jsonObject.getString("image"));
//                                user.setEmail(jsonObject.getString("email"));
//                                user.setGender(jsonObject.getString("gender"));
//                                user.setBirthDay(jsonObject.getString("birthday"));
//                                MainActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Common.setCurrentUser(getApplicationContext(), user);
//                                        Log.e("CheckData", user.toString());
//                                    }
//                                });
//                                Toast.makeText(getApplicationContext(), "Load User success", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error User", error.toString());
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(jsonArrayRequest);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status,false);
        }
    };

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    private void setSnackbarMessage(String status,boolean showBar) {
        String internetStatus="";
        if(status.equalsIgnoreCase("Wifi enabled")||status.equalsIgnoreCase("Mobile data enabled")){
            internetStatus="Internet đã kết nối";
        }else {
            internetStatus="Không có kết nối Internet";
        }
        snackbar = Snackbar
                .make(container, internetStatus, Snackbar.LENGTH_INDEFINITE)
                .setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if(internetStatus.equalsIgnoreCase("Không có kết nối Internet")){
            if(internetConnected){
                snackbar.show();
                internetConnected=false;
            }
        }else{
            if(!internetConnected){
                internetConnected=true;
                snackbar.show();
                CountDownTimer Timer = new CountDownTimer(5000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        snackbar.dismiss();
                    }
                };
                Timer.start();
            }
        }
    }
}
