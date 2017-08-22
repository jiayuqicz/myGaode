package com.jiayuqicz.mygaode.map;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.jiayuqicz.mygaode.search.SearchFragment;
import com.jiayuqicz.mygaode.setting.SettingFragment;
import com.jiayuqicz.mygaode.weather.WeatherFragment;


/**
 * Created by jiayuqicz on 2017/8/16 0016.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private MapFragment mapFragment = null;
    private WeatherFragment weatherFragment = null;
    private SettingFragment settingFragment = null;
    private SearchFragment searchFragment = null;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        mapFragment = new MapFragment();
        weatherFragment = new WeatherFragment();
        settingFragment = new SettingFragment();
        searchFragment = new SearchFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mapFragment;
            case 1:
                return searchFragment;
            case 2:
                return weatherFragment;
            case 3:
                return settingFragment;
        }
        return null;
    }
}
