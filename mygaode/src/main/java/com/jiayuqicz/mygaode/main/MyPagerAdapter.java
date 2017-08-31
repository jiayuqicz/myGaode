package com.jiayuqicz.mygaode.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.jiayuqicz.mygaode.map.MapFragment;
import com.jiayuqicz.mygaode.search.SearchFragment;
import com.jiayuqicz.mygaode.setting.SettingFragment;
import com.jiayuqicz.mygaode.weather.WeatherFragment;


/**
 * Created by jiayuqicz on 2017/8/16 0016.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {


    SparseArray<Fragment> list = new SparseArray<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        list.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MapFragment.newInstance();
            case 1:
                return SearchFragment.newInstance();
            case 2:
                return WeatherFragment.newIntance();
            case 3:
                return SettingFragment.newInstance();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        list.remove(position);
    }

    public Fragment getFragment(int index) {
        return list.get(index);
    }
}
