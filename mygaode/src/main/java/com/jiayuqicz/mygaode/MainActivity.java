package com.jiayuqicz.mygaode;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment = null;
    private WeatherFragment weatherFragment = null;
    private SettingFragment settingFragment = null;
    private FragmentTransaction transaction = null;
    private ViewGroup fragment_container = null;
    public static long animateDuringTime;


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

        if(findViewById(R.id.fragment_container)!=null) {
            //初始化所有的Fragment
            mapFragment = new MapFragment();
            weatherFragment = new WeatherFragment();
            settingFragment = new SettingFragment();
            fragment_container = (ViewGroup) findViewById(R.id.fragment_container);
            animateDuringTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container,mapFragment).commit();
        }
    }

    public void replace(Fragment fragment) {
        //重新获取fragment变换
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        //允许返回键返回
        transaction.addToBackStack(null);
        //设置fragment容器不可见
        fragment_container.setAlpha(0);
        transaction.commit();
        //为fragment切换添加淡入动画
        fragment_container.animate().alpha(1).setDuration(animateDuringTime);
    }

    public void selectWeatherFragment(View view) {
        replace(weatherFragment);
    }

    public void selectSettingFragment(View view) {
        replace(settingFragment);
    }

    public void selectMapFragment(View view) {
        replace(mapFragment);
    }
}
