package com.shyam.notifyapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shyam.notifyapp.Fragments.AskFragment;
import com.shyam.notifyapp.Fragments.JobsFragment;
import com.shyam.notifyapp.Fragments.MainFragment;
import com.shyam.notifyapp.Fragments.NewFragment;
import com.shyam.notifyapp.Fragments.ShowFragment;

/**
 * Created by shyam on 29/6/15.
 */


public class TabsPagerAdapter extends FragmentPagerAdapter {


    private String[] tabs = {"Top", "New", "Show", "Ask", "Jobs"};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new MainFragment();
            case 1:
                return new NewFragment();
            case 2:
                return new ShowFragment();
            case 3:
                return new AskFragment();
            case 4:
                return new JobsFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
}