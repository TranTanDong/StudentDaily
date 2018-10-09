package com.example.woo.studentdaily.Plan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.woo.studentdaily.Plan.Fragment.TabPlanFragment;
import com.example.woo.studentdaily.Plan.Fragment.TabPlanListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new TabPlanFragment();
                break;
            case 1:
                frag = new TabPlanListFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position){
//            case 0:
//                title = "One";
//                break;
//            case 1:
//                title = "Two";
//                break;
//        }
//        return title;
//    }
}
