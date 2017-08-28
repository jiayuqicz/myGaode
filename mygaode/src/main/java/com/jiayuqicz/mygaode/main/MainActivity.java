package com.jiayuqicz.mygaode.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.map.MapFragment;
import com.jiayuqicz.mygaode.search.SearchFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.MyItemClickedListener,
        ViewPager.OnPageChangeListener
{

    //记录当前的页面，解决重复点击相同的标签，导致页面的重新加载的bug
    private String currentFragment = null;
    private MyViewPager pager = null;

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
        //初始化界面
        initView();
    }

    public void initView() {

        if(findViewById(R.id.viewPager)!=null) {
            pager = (MyViewPager) findViewById(R.id.viewPager);
            pager.setAdapter(new MyPagerAdapter(getFragmentManager()));
            pager.addOnPageChangeListener(this);
        }
    }

    public void selectMapFragment(View view) {
        //禁用手势滑动翻页
        pager.setScroll(false);

        if(currentFragment == MAP)
            return;
        pager.setCurrentItem(0);
        //记录当前页面
        currentFragment = MAP;
    }

    public void selectSearchFragment(View view) {

        pager.setScroll(true);

        if (currentFragment == SEARCH)
            return;
        pager.setCurrentItem(1);
        currentFragment = SEARCH;
    }

    public void selectWeatherFragment(View view) {

        pager.setScroll(true);

        if(currentFragment == WEATHER)
            return;
        //选择当前页面
        pager.setCurrentItem(2);
        currentFragment = WEATHER;
    }

    public void selectSettingFragment(View view) {

        pager.setScroll(true);

        if(currentFragment == SETTINGS)
            return;
        pager.setCurrentItem(3);
        currentFragment = SETTINGS;
    }

    @Override
    public void Locate(LatLonPoint point) {
//        MapFragment mapFragment = (MapFragment) pager.getAdapter().instantiateItem(pager, 0);
        MapFragment mapFragment = MapFragment.getFirstInstance();

        if(point != null)
            mapFragment.locate(point);
        else
            Toast.makeText(this, R.string.invalid_address, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当处于地图页面时，禁用滑动手势
        switch (position) {
            case 0:
                //当处于地图页面时，禁用滑动
                pager.setScroll(false);
                currentFragment = MAP;
                break;
            case 1:
                pager.setScroll(true);
                currentFragment = SEARCH;
                break;
            case 2:
                pager.setScroll(true);
                currentFragment = WEATHER;
                break;
            case 3:
                pager.setScroll(true);
                currentFragment = SETTINGS;
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
