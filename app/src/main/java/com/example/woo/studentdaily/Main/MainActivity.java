package com.example.woo.studentdaily.Main;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Main.Fragment.DiaryFragment;
import com.example.woo.studentdaily.Main.Fragment.MoreFragment;
import com.example.woo.studentdaily.Main.Fragment.PlanFragment;
import com.example.woo.studentdaily.Main.Fragment.SubjectFragment;
import com.example.woo.studentdaily.More.Model.User;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;
import com.example.woo.studentdaily.Subject.AddSubjectActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialSearchView searchView;

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
                    toolbar.getMenu().findItem(R.id.search_subject).setVisible(true);
                    toolbar.getMenu().findItem(R.id.search_diary).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_plan).setVisible(false);
                    toolbar.getMenu().findItem(R.id.add1).setVisible(true);
                    fragment = SubjectFragment.newInstance();
                    break;
                case R.id.navigation_plan:
                    setTitle("Kế hoạch");
                    toolbar.getMenu().findItem(R.id.search_subject).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_diary).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_plan).setVisible(true);
                    toolbar.getMenu().findItem(R.id.add1).setVisible(false);
                    fragment = PlanFragment.newInstance();
                    break;
                case R.id.navigation_diary:
                    setTitle("Nhật ký");
                    toolbar.getMenu().findItem(R.id.search_subject).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_diary).setVisible(true);
                    toolbar.getMenu().findItem(R.id.search_plan).setVisible(false);
                    toolbar.getMenu().findItem(R.id.add1).setVisible(false);
                    fragment = DiaryFragment.newInstance();
                    break;
                case R.id.navigation_more:
                    setTitle("Xem thêm");
                    toolbar.getMenu().findItem(R.id.search_subject).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_diary).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_plan).setVisible(false);
                    toolbar.getMenu().findItem(R.id.add1).setVisible(false);
                    fragment = MoreFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        loadDataUser();
        addEvents();
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
                                if (jsonObject.getString("gender").equals("1")){
                                    user.setGender(true);
                                }else user.setGender(false);
                                user.setBirthDay(jsonObject.getString("birthday"));
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Common.setCurrentUser(getApplicationContext(), user);
                                        Log.i("Checkdata", user.toString());
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
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}
