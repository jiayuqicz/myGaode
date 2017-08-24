package com.jiayuqicz.mygaode.map;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.route.RouteActivity;


public class MapFragment extends Fragment implements AMap.OnMarkerClickListener{

    private TextureMapView mapView = null;
    private AMap aMap = null;

    private String SHOW_CAMPASS = "show_compass";
    SharedPreferences share = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        //读取设置
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());

        View rootView = inflater.inflate(R.layout.main_fragment_map,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (TextureMapView) getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
    }


    //初始化地图组件
    private void initMap() {

        aMap = mapView.getMap();
        MyLocationStyle style = new MyLocationStyle();
        style.showMyLocation(true);
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        style.interval(600000);
        aMap.setMyLocationStyle(style);
        aMap.setMyLocationEnabled(true);

        //设置marker点击监听器
        aMap.setOnMarkerClickListener(this);


        //获取地图的UI设置工具
        UiSettings uiSettings = aMap.getUiSettings();

        //设置指南针
        if (share.getBoolean(SHOW_CAMPASS, true))
            uiSettings.setCompassEnabled(true);
        else
            uiSettings.setCompassEnabled(false);

        //开启定位按钮和小蓝点
        uiSettings.setMyLocationButtonEnabled(true);
        //开启缩放按钮
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void addMaker(LatLonPoint makerPoint) {

        double lat = makerPoint.getLatitude();
        double lon = makerPoint.getLongitude();

        MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lon));
        aMap.addMarker(options);

    }

    public void locate(LatLonPoint makerPoint) {

        double lat = makerPoint.getLatitude();
        double lon = makerPoint.getLongitude();

        MarkerOptions options = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(lat, lon))
                .title(getString(R.string.marker_title));
        aMap.clear();
        aMap.addMarker(options).showInfoWindow();

        //缩放范围： 3- 19
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(getActivity(), RouteActivity.class);
        startActivity(intent);

        return true;
    }
}
