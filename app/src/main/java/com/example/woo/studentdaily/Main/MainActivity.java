package com.example.woo.studentdaily.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.woo.studentdaily.Main.Fragment.DiaryFragment;
import com.example.woo.studentdaily.Main.Fragment.MoreFragment;
import com.example.woo.studentdaily.Main.Fragment.PlanFragment;
import com.example.woo.studentdaily.Main.Fragment.SubjectFragment;
import com.example.woo.studentdaily.More.Model.User;
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
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AddPlanBottomDialogFragment.BottomSheetListener {
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    public static ArrayList<Plan> mainPlans = new ArrayList<>();

    private User user;
    private FirebaseAuth mAuth;


    private BottomNavigationView mMainNav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_subject:
                    setTitle("Môn học");
                    setToolbar(true, false, false, true);
                    fragment = SubjectFragment.newInstance();
                    break;
                case R.id.navigation_plan:
                    setTitle("Kế hoạch");
                    setToolbar(false, false, true, false);
                    fragment = PlanFragment.newInstance();
                    break;
                case R.id.navigation_diary:
                    setTitle("Nhật ký");
                    setToolbar(false, true, false, false);
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
        loadDataUser();
        loadDataMainPlan(getApplicationContext());
        addEvents();
    }

    public static void loadDataMainPlan(final Context context) {
        mainPlans.clear();
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectPlan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Plan plan = new Plan();
                                plan.setId(jsonObject.getInt("id"));
                                plan.setCodeUser(jsonObject.getString("codeuser"));
                                plan.setName(jsonObject.getString("name"));
                                plan.setUpdateDay(jsonObject.getString("updateday"));
                                mainPlans.add(plan);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(context, "Oke loaded Plan", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Plan", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    private void addEvents() {

    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();
        user = new User();

        mMainNav     = findViewById(R.id.navigation);
        mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(mMainNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,PlanFragment.newInstance()).commit();
        setTitle("Kế hoạch");
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
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
            startActivity(new Intent(MainActivity.this, AddSubjectActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataUser() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectUser, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = mAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("code").equals(code)){
                                user.setCode(jsonObject.getString("code"));
                                user.setName(jsonObject.getString("name"));
                                user.setImage(jsonObject.getString("image"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setGender(jsonObject.getString("gender"));
                                user.setBirthDay(jsonObject.getString("birthday"));
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Common.setCurrentUser(getApplicationContext(), user);
                                        Log.i("CheckData", user.toString());
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Oke I'm here", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error User", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void insertDataPlan(final String code, final String name, final String updateDay) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchInsertPlan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_PLAN", response+name);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDataMainPlan(getApplicationContext());
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
                hashMap.put("p_codeuser", code);
                hashMap.put("p_name", name);
                hashMap.put("p_updateday", Common.moveSlashTo(updateDay, "/", "-"));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void sendDataPlan(String namePlan, String dayUpdate) {
        String code = mAuth.getCurrentUser().getUid();
        insertDataPlan(code, namePlan, dayUpdate);
    }
}
