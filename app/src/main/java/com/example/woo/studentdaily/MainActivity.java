package com.example.woo.studentdaily;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private BottomNavigationView mMainNav;

    private SubjectFragment subjectFragment;
    private PlanFragment planFragment;
    private DailyFragment dailyFragment;
    private ExtendedFragment extendedFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(subjectFragment);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(planFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(dailyFragment);
                    return true;
                case R.id.navigation_extended:
                    setFragment(extendedFragment);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectFragment  = new SubjectFragment();
        planFragment     = new PlanFragment();
        dailyFragment    = new DailyFragment();
        extendedFragment = new ExtendedFragment();

        mFrameLayout = findViewById(R.id.main_frame);
        mMainNav     = findViewById(R.id.navigation);
        mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
