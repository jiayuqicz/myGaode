package com.jiayuqicz.mygaode.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.map.MapFragment;
import com.jiayuqicz.mygaode.util.ToastUtil;


public class RouteActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener {

    private Byte schedule_index;
    private String[] schedule = null;
    private TextView currentSchedule = null;
    private LatLonPoint start = null;
    private LatLonPoint end = null;
    private RouteSearch routeSearch = null;
    private ListView routeList = null;

    private String city = "北京";


    private final int bus = 0;
    private final int car = 1;
    private final int walk = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        
        initView();
        searchRouteResult(bus, RouteSearch.BUS_DEFAULT);
    }

    private void initView() {
        schedule_index = 0;
        schedule = getResources().getStringArray(R.array.schedule);
        currentSchedule = (TextView) findViewById(R.id.schedule);
        currentSchedule.setText(schedule[schedule_index]);

        start = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_START);
        end = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_END);

        routeList = (ListView) findViewById(R.id.route_list);

        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
    }

    public void changeSchedule(View view) {

        schedule_index++;
        //循环控制不溢出
        if(schedule_index == schedule.length) {
            schedule_index = 0;
        }
        currentSchedule.setText(schedule[schedule_index]);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int schedule) {
        if (start == null) {
            ToastUtil.show(this, "起点未设置");
            return;
        }
        if (end == null) {
            ToastUtil.show(this, "终点未设置");
        }

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                start, end);
        
        switch (routeType) {
            case bus:
                BusRouteQuery busRouteQuery = new BusRouteQuery(fromAndTo, schedule, city, 0);
                routeSearch.calculateBusRouteAsyn(busRouteQuery);
                break;
            case car:
                DriveRouteQuery driveRouteQuery = new DriveRouteQuery(fromAndTo, schedule, null,
                        null, null);
                routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
                break;
            case walk:
                WalkRouteQuery walkRouteQuery = new WalkRouteQuery(fromAndTo);
                routeSearch.calculateWalkRouteAsyn(walkRouteQuery);
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int errorCode) {

        if(errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (busRouteResult != null && busRouteResult.getPaths() != null) {
                BusResultListAdapter adapter = new BusResultListAdapter(this , busRouteResult);
                routeList.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {


    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
