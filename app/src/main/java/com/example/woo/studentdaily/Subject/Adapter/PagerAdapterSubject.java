package com.example.woo.studentdaily.Subject.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.woo.studentdaily.Subject.Fragment.DocumentFragment;
import com.example.woo.studentdaily.Subject.Fragment.LecturersFragment;
import com.example.woo.studentdaily.Subject.Fragment.ScoreFragment;

public class PagerAdapterSubject extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapterSubject (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new DocumentFragment();
                break;
            case 1:
                frag = new ScoreFragment();
                break;
            case 2:
                frag = new LecturersFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
