package com.shyam.notifyapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.shyam.notifyapp.Adapter.TabsPagerAdapter;
import com.shyam.notifyapp.R;
import com.shyam.notifyapp.SlidingTab.SlidingTabLayout;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private SlidingTabLayout slidingTabLayout;
    // Tab titles
    private String[] tabs = {"Top", "New", "Show", "Ask", "Jobs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Parse
        Parse.initialize(this, "FthICdP4p9W1gfi6PSvfMciZHST2qhS8XCofGVkx", "jIGtqqyTU83dsypctUihGXHRbXP3tF6U5UzGJbem");
        ParseInstallation.getCurrentInstallation().saveInBackground();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }
}