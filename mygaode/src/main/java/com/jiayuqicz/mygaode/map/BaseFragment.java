package com.jiayuqicz.mygaode.map;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.util.OfflineMapUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected TextureMapView mapView = null;
    protected SharedPreferences share = null;
    protected AMap aMap = null;
    protected final String SHOW_CAMPASS = "show_compass";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //读取设置
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //设置离线地图的目录
        MapsInitializer.sdcardDir = OfflineMapUtil.getSdCacheDir();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_map,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap(savedInstanceState);
    }

    protected void initMap(Bundle savedInstanceState) {
        mapView = getMapView();
        mapView.onCreate(savedInstanceState);

        //获取地图控制器
        aMap = mapView.getMap();

        //开启小蓝点定位
        MyLocationStyle style = new MyLocationStyle();
        style.showMyLocation(true);
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        style.interval(600000);
        aMap.setMyLocationStyle(style);
        aMap.setMyLocationEnabled(true);

        //获取地图的UI设置工具
        UiSettings uiSettings = aMap.getUiSettings();

        //设置指南针
        if (share.getBoolean(SHOW_CAMPASS, true))
            uiSettings.setCompassEnabled(true);
        else
            uiSettings.setCompassEnabled(false);

        //开启定位按钮
        uiSettings.setMyLocationButtonEnabled(true);
        //开启缩放按钮
        uiSettings.setZoomControlsEnabled(true);
        //开启手势地图操作
        uiSettings.setAllGesturesEnabled(true);
    }

    protected TextureMapView getMapView() {
        return (TextureMapView) getView().findViewById(R.id.mapView);
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
}
