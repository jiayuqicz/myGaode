package com.jiayuqicz.mygaode.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amap.api.services.core.LatLonPoint;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.map.MapFragment;
import com.jiayuqicz.mygaode.map.MyPagerAdapter;
import com.jiayuqicz.mygaode.search.SearchFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.MyItemClickedListener
{

    //记录当前的页面，解决重复点击相同的标签，导致页面的重新加载的bug
    private String currentFragment = null;
    private ViewPager pager = null;

    private String MAP = "mapFragment";
    private String SEARCH = "searchFragment";
    private String WEATHER = "weatherFragment";
    private String SETTINGS = "settingFragment";




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
            pager = (ViewPager) findViewById(R.id.viewPager);
            pager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        }
    }

    public void selectMapFragment(View view) {

        if(currentFragment == MAP)
            return;
        pager.setCurrentItem(0);
        //记录当前页面
        currentFragment = MAP;
    }

    public void selectSearchFragment(View view) {

        if (currentFragment == SEARCH)
            return;
        pager.setCurrentItem(1);
        currentFragment = SEARCH;
    }

    public void selectWeatherFragment(View view) {

        if(currentFragment == WEATHER)
            return;
        //选择当前页面
        pager.setCurrentItem(2);
        currentFragment = WEATHER;
    }

    public void selectSettingFragment(View view) {

        if(currentFragment == SETTINGS)
            return;
        pager.setCurrentItem(3);
        currentFragment = SETTINGS;
    }

    @Override
    public void setPoint(LatLonPoint point) {
        MapFragment mapFragment = (MapFragment) pager.getAdapter().instantiateItem(pager, 0);
        mapFragment.setMaker(point);
    }

}
