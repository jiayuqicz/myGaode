package com.jiayuqicz.mygaode;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static long animateDuringTime;

    //记录当前的页面，解决重复点击相同的标签，导致页面的重新加载的bug
    private String currentFragment = null;
    private ViewPager pager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) {
            return;
        }
        initView();
    }

    public void initView() {

        if(findViewById(R.id.viewPager)!=null) {

            animateDuringTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            currentFragment = "mapFragment";

            pager = (ViewPager) findViewById(R.id.viewPager);
            pager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        }
    }

    public void selectWeatherFragment(View view) {

        if(currentFragment == "weatherFragment")return;

        pager.setCurrentItem(1);
        currentFragment = "weatherFragment";
    }

    public void selectSettingFragment(View view) {

        if(currentFragment == "settingFragment")return;
        pager.setCurrentItem(2);
        currentFragment = "settingFragment";
    }

    public void selectMapFragment(View view) {

        if(currentFragment == "MapFragment")return;

        pager.setCurrentItem(0);
        currentFragment = "MapFragment";
    }

    public void selectSearchFragment(View view) {
    }
}
