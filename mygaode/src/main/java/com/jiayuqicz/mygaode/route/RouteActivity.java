package com.jiayuqicz.mygaode.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.map.MapFragment;
import com.jiayuqicz.mygaode.route.bus.BusResultListAdapter;
import com.jiayuqicz.mygaode.route.car.CarResultListAdapter;
import com.jiayuqicz.mygaode.route.walk.WalkResultListAdapter;
import com.jiayuqicz.mygaode.util.ToastUtil;


public class RouteActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener,
        AdapterView.OnItemClickListener {


    private Byte schedule_index;
    private String[] schedule = null;
    private TextView currentSchedule = null;
    private LatLonPoint start = null;
    private LatLonPoint end = null;
    private RouteSearch routeSearch = null;
    private ListView routeList = null;

    private BusRouteResult busRouteResult = null;
    private DriveRouteResult driveRouteResult = null;
    private WalkRouteResult walkRouteResult = null;

    private String city = "北京";

    public static String DETAIL_INTENT = "detail_intent";
    public static String ROUTE_TYPE = "route_type";

    private static final int bus = 0;
    private static final int car = 1;
    private static final int walk = 2;

    private int routeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView();
        searchRouteResult(routeType, RouteSearch.BUS_DEFAULT);
    }

    private void initView() {
        //出行方式
        routeType = bus;
        //搜索的路径模式
        schedule_index = 0;

        schedule = getResources().getStringArray(R.array.schedule);
        currentSchedule = (TextView) findViewById(R.id.schedule);
        currentSchedule.setText(schedule[schedule_index]);

        start = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_START);
        end = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_END);

        routeList = (ListView) findViewById(R.id.route_list);
        routeList.setOnItemClickListener(this);

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
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

        if(errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                //缓存搜索的结果
                busRouteResult = result;
                //为ListView配置数据源
                BusResultListAdapter adapter = new BusResultListAdapter(this , result);
                routeList.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    driveRouteResult = result;

                    CarResultListAdapter adapter = new CarResultListAdapter(this, result);
                    routeList.setAdapter(adapter);

                }
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {

        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    walkRouteResult = result;
                    WalkResultListAdapter adapter = new WalkResultListAdapter(this, result);
                    routeList.setAdapter(adapter);
                }
            }
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (routeType) {
            case bus: {
                Intent intent = new Intent(this, RouteDetailActivity.class);
                BusPath path = busRouteResult.getPaths().get(position);
                intent.putExtra(DETAIL_INTENT, path);
                intent.putExtra(ROUTE_TYPE, bus);
                startActivity(intent);
                break;
            }
            case car: {
                Intent intent = new Intent(this, RouteDetailActivity.class);
                DrivePath path = driveRouteResult.getPaths().get(position);
                intent.putExtra(DETAIL_INTENT, path);
                intent.putExtra(ROUTE_TYPE, car);
                startActivity(intent);
                break;
            }
            case walk: {
                Intent intent = new Intent(this, RouteDetailActivity.class);
                WalkPath path = walkRouteResult.getPaths().get(position);
                intent.putExtra(DETAIL_INTENT, path);
                intent.putExtra(ROUTE_TYPE, walk);
                startActivity(intent);
                break;
            }
        }
    }

    public void searchBus(View view) {
        routeType = bus;
        searchRouteResult(bus, RouteSearch.BUS_DEFAULT);
    }

    public void searchCar(View view) {
        routeType = car;
        searchRouteResult(car, RouteSearch.BUS_DEFAULT);
    }

    public void searchWalk(View view) {
        routeType = walk;
        searchRouteResult(walk, RouteSearch.BUS_DEFAULT);
    }
}
