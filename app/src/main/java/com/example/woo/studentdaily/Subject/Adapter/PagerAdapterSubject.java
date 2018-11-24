package com.example.woo.studentdaily.Subject.Adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.woo.studentdaily.Subject.Fragment.DocumentFragment;
import com.example.woo.studentdaily.Subject.Fragment.LecturerFragment;
import com.example.woo.studentdaily.Subject.Fragment.ScoreFragment;

import java.util.ArrayList;

public class PagerAdapterSubject extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Bundle bundle;

    public PagerAdapterSubject (FragmentManager fm, int NumOfTabs, Bundle bundle) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new DocumentFragment();
                frag.setArguments(bundle);
                break;
            case 1:
                frag = new ScoreFragment();
                frag.setArguments(bundle);
                break;
            case 2:
                frag = new LecturerFragment();
                frag.setArguments(bundle);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

//    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
//    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
//
//    public PagerAdapterSubject(FragmentManager manager) {
//        super(manager);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return mFragmentList.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//    public void addFrag(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }
}
