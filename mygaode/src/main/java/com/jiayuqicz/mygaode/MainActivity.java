package com.jiayuqicz.mygaode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment = null;
    private WeatherFragment weatherFragment = null;
    private SettingFragment settingFragment = null;
    private FragmentTransaction transaction = null;

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
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container,mapFragment).commit();
        }
    }

    public void replace(Fragment fragment) {
        //重新获取fragment变换
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        //允许返回键返回
        transaction.addToBackStack(null);
        transaction.commit();
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
