package com.jiayuqicz.mygaode.map;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.main.MainActivity;
import com.jiayuqicz.mygaode.route.RouteActivity;


public class MapFragment extends BaseFragment implements AMap.OnMarkerClickListener,
        AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    public final static String INTENT_DATA_ID_START = "com.jiayuqicz.mygaode.map.start";
    public final static String INTENT_DATA_ID_END = "com.jiayuqicz.mygaode.map.end";
    public final static String INTENT_CITY = "com.jiayuqicz.mygaode.map.city";

    private LatLonPoint start = null;
    private LatLonPoint end = null;
    private String city = "北京";

    private GeocodeSearch geocodeSearch = null;

    //便于维护，传递参数数据
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    protected void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);

        //设置marker点击监听器
        aMap.setOnMarkerClickListener(this);
        //开启定位的监听器
        aMap.setOnMyLocationChangeListener(this);
        //初始化地理信息，得到当前的地址
        initGeo();
    }

    private void initGeo() {
        geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(this);
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
        intent.putExtra(INTENT_CITY, this.city);
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
        RegeocodeQuery query = new RegeocodeQuery(start, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null && result
                    .getRegeocodeAddress().getFormatAddress() != null) {
                city = result.getRegeocodeAddress().getCity();
                ((MainActivity)getActivity()).setCity(city);
            }
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
