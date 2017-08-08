package com.jiayuqicz.mygaode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    TextureMapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
//        mMapView = (TextureMapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(savedInstanceState);
//        AMap aMap = mMapView.getMap();
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
//        MyLocationStyle style = new MyLocationStyle();
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
//        style.interval(1000);
//        aMap.setMyLocationStyle(style);
//        aMap.setMyLocationEnabled(true);
//
//        UiSettings uiSettings = aMap.getUiSettings();
//        uiSettings.setCompassEnabled(true);

    }
}
