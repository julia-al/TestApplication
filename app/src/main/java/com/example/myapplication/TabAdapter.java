package com.example.myapplication;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.fragment.FragmentAll;
import com.example.myapplication.fragment.FragmentAnalytics;
import com.example.myapplication.fragment.FragmentAndroid;
import com.example.myapplication.fragment.FragmentBackOffice;
import com.example.myapplication.fragment.FragmentBackend;
import com.example.myapplication.fragment.FragmentDesigner;
import com.example.myapplication.fragment.FragmentFrontend;
import com.example.myapplication.fragment.FragmentHR;
import com.example.myapplication.fragment.FragmentIOS;
import com.example.myapplication.fragment.FragmentManagment;
import com.example.myapplication.fragment.FragmentPR;
import com.example.myapplication.fragment.FragmentQA;
import com.example.myapplication.fragment.FragmentSupport;

public class TabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.t1,
            R.string.t2,
            R.string.t3,
            R.string.t4,
            R.string.t5,
            R.string.t6,
            R.string.t7,
            R.string.t8,
            R.string.t9,
            R.string.t10,
            R.string.t11,
            R.string.t12,
            R.string.t13
    };
    private final Context mContext;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentAll();
        }
        else if (position == 1){
            fragment = new FragmentDesigner();
        }
        else if (position == 2){
            fragment = new FragmentAnalytics();
        }
        else if (position == 3){
            fragment = new FragmentAndroid();
        }
        else if (position == 4){
            fragment = new FragmentIOS();
        }
        else if (position == 5){
            fragment = new FragmentManagment();
        }
        else if (position == 6){
            fragment = new FragmentFrontend();
        }
        else if (position == 7){
            fragment = new FragmentBackend();
        }
        else if (position == 8){
            fragment = new FragmentQA();
        }
        else if (position == 9){
            fragment = new FragmentBackOffice();
        }
        else if (position == 10){
            fragment = new FragmentHR();
        }
        else if (position == 11){
            fragment = new FragmentPR();
        }
        else if (position == 12){
            fragment = new FragmentSupport();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 13;
    }
}