package com.jiayuqicz.mygaode.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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


    private Byte index;
    //存储当前出行方式的mode值，用作参数
    private int mode;
    //存储公交模式
    private String[] bus_mode = null;
    //存储驾车模式
    private String[] car_mode = null;
    //存储步行模式
    private String[] walk_mode = null;

    private TextView modeTextView = null;
    private LatLonPoint start = null;
    private LatLonPoint end = null;
    private RouteSearch routeSearch = null;
    private ListView routeList = null;

    private BusRouteResult busRouteResult = null;
    private DriveRouteResult driveRouteResult = null;
    private WalkRouteResult walkRouteResult = null;

    private String city = "北京";

    public final static String DETAIL_INTENT = "detail_intent";
    public final static String ROUTE_TYPE = "route_type";
    public final static String ROUTE_RESULT = "route_result";

    public static final int bus = 0;
    public static final int car = 1;
    public static final int walk = 2;

    private ProgressBar bar;

    private int routeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView();
        //开始搜索汽车出行方式
        searchBus(null);
    }

    private void initView() {

        //初始化数组
        bus_mode = getResources().getStringArray(R.array.bus_mode);
        car_mode = getResources().getStringArray(R.array.car_mode);
        walk_mode = getResources().getStringArray(R.array.walk_mode);

        start = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_START);
        end = getIntent().getParcelableExtra(MapFragment.INTENT_DATA_ID_END);
        city = getIntent().getStringExtra(MapFragment.INTENT_CITY);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        modeTextView = (TextView) findViewById(R.id.schedule);
        routeList = (ListView) findViewById(R.id.route_list);
        routeList.setOnItemClickListener(this);

        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
    }

    public void changeSchedule(View view) {
        //index自加1
        index++;
        //改变模式
        changeMode();
        //开始搜索
        searchRouteResult(routeType, mode);
    }

    //改变模式
    private void changeMode() {

        //循环控制不溢出
        if (index >= bus_mode.length) {
            index = 0;
        }

        if (routeType == bus) {

            switch (index) {
                case 0:
                    mode = RouteSearch.BUS_DEFAULT;
                    break;
                case 1:
                    mode = RouteSearch.BUS_SAVE_MONEY;
                    break;
                case 2:
                    mode = RouteSearch.BUS_LEASE_WALK;
                    break;
            }
            modeTextView.setText(bus_mode[index]);
        }
        if (routeType == car) {

            switch (index) {
                case 0:
                    mode = RouteSearch.DRIVING_MULTI_CHOICE_HIGHWAY_AVOID_CONGESTION;
                    break;
                case 1:
                    mode = RouteSearch.DRIVING_MULTI_CHOICE_AVOID_CONGESTION_SAVE_MONEY;
                    break;
                case 2:
                    mode = RouteSearch.DRIVING_MULTI_CHOICE_AVOID_CONGESTION;
                    break;
            }
            modeTextView.setText(car_mode[index]);
        }
    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int schedule) {

        Log.e("test", String.valueOf(routeType) + " " + String.valueOf(schedule));

        //显示进度条
        bar.setVisibility(View.VISIBLE);

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
                routeType = bus;
                bar.setVisibility(View.INVISIBLE);
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
                    routeType = car;
                    bar.setVisibility(View.INVISIBLE);
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
                    routeType = walk;
                    bar.setVisibility(View.INVISIBLE);
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
                intent.putExtra(ROUTE_RESULT, busRouteResult);
                startActivity(intent);
                break;
            }
            case car: {
                Intent intent = new Intent(this, RouteDetailActivity.class);
                DrivePath path = driveRouteResult.getPaths().get(position);
                intent.putExtra(DETAIL_INTENT, path);
                intent.putExtra(ROUTE_RESULT, driveRouteResult);
                intent.putExtra(ROUTE_TYPE, car);
                startActivity(intent);
                break;
            }
            case walk: {
                Intent intent = new Intent(this, RouteDetailActivity.class);
                WalkPath path = walkRouteResult.getPaths().get(position);
                intent.putExtra(DETAIL_INTENT, path);
                intent.putExtra(ROUTE_RESULT, walkRouteResult);
                intent.putExtra(ROUTE_TYPE, walk);
                startActivity(intent);
                break;
            }
        }
    }

    public void searchBus(View view) {
        index = 0;
        routeType = bus;
        changeMode();
        searchRouteResult(routeType, mode);
    }

    public void searchCar(View view) {
        routeType = car;
        index = 0;
        changeMode();
        searchRouteResult(routeType, mode);
    }

    public void searchWalk(View view) {
        routeType = walk;
        index = 0;
        changeMode();
        searchRouteResult(routeType, mode);
    }
}
