package com.jiayuqicz.mygaode.map;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapsInitializer;
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
import com.jiayuqicz.mygaode.util.OfflineMapUtil;


public class MapFragment extends Fragment implements AMap.OnMarkerClickListener,
        AMap.OnMyLocationChangeListener{

    private TextureMapView mapView = null;
    protected AMap aMap = null;
    private final String SHOW_CAMPASS = "show_compass";

    public final static String INTENT_DATA_ID_START = "com.jiayuqicz.mygaode.map.start";
    public final static String INTENT_DATA_ID_END = "com.jiayuqicz.mygaode.map.end";

    private SharedPreferences share = null;
    private LatLonPoint start = null;
    private LatLonPoint end = null;

    //便于维护，传递参数数据
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //读取设置
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //设置离线地图的目录
        MapsInitializer.sdcardDir = OfflineMapUtil.getSdCacheDir();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_map,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (TextureMapView) getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
    }


    //初始化地图组件
    protected void initMap() {

        //获取地图控制器
        aMap = mapView.getMap();

        //开启小蓝点定位
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

        //开启定位按钮
        uiSettings.setMyLocationButtonEnabled(true);
        //开启定位的监听器
        aMap.setOnMyLocationChangeListener(this);
        //开启缩放按钮
        uiSettings.setZoomControlsEnabled(true);
        //开启手势地图操作
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

        if (end == null)
            end = new LatLonPoint(lat, lon);
        else {
            end.setLatitude(lat);
            end.setLongitude(lon);
        }


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
        intent.putExtra(INTENT_DATA_ID_START, this.start);
        intent.putExtra(INTENT_DATA_ID_END,this.end);
        startActivity(intent);
        return true;
    }

    @Override
    public void onMyLocationChange(Location location) {

        if(start == null)
            start = new LatLonPoint(location.getLatitude(), location.getLongitude());
        else {
            start.setLatitude(location.getLatitude());
            start.setLongitude(location.getLongitude());
        }
    }
}
