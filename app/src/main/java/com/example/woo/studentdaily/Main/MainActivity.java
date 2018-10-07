package com.example.woo.studentdaily.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.woo.studentdaily.Main.Fragment.DiaryFragment;
import com.example.woo.studentdaily.Main.Fragment.ExtendedFragment;
import com.example.woo.studentdaily.Main.Fragment.PlanFragment;
import com.example.woo.studentdaily.Main.Fragment.SubjectFragment;
import com.example.woo.studentdaily.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private BottomNavigationView mMainNav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            toolbar.getMenu().findItem(R.id.search_view).setVisible(true);
            toolbar.getMenu().findItem(R.id.add1).setVisible(true);
            toolbar.getMenu().findItem(R.id.more).setVisible(true);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Môn học");
                    toolbar.getMenu().findItem(R.id.more).setVisible(false);
                    fragment = SubjectFragment.newInstance();
                    break;
                case R.id.navigation_dashboard:
                    setTitle("Kế hoạch");
                    toolbar.getMenu().findItem(R.id.add1).setVisible(false);
                    fragment = PlanFragment.newInstance();
                    break;
                case R.id.navigation_notifications:
                    setTitle("Nhật ký");
                    toolbar.getMenu().findItem(R.id.more).setVisible(false);
                    fragment = DiaryFragment.newInstance();
                    break;
                case R.id.navigation_extended:
                    setTitle("Xem thêm");
                    toolbar.getMenu().findItem(R.id.more).setVisible(false);
                    toolbar.getMenu().findItem(R.id.search_view).setVisible(false);
                    toolbar.getMenu().findItem(R.id.add1).setVisible(false);
                    fragment = ExtendedFragment.newInstance();
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
        mMainNav     = findViewById(R.id.navigation);
        mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,PlanFragment.newInstance()).commit();
        setTitle("Kế hoạch");
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
