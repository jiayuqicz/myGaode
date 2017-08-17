package com.jiayuqicz.mygaode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by jiayuqicz on 2017/8/16 0016.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private MapFragment mapFragment = null;
    private WeatherFragment weatherFragment = null;
    private SettingFragment settingFragment = null;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        mapFragment = new MapFragment();
        weatherFragment = new WeatherFragment();
        settingFragment = new SettingFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mapFragment;
            case 1:
                return weatherFragment;
            case 2:
                return settingFragment;
        }
        return null;
    }
}
