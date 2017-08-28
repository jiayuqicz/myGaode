package com.jiayuqicz.mygaode.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.WalkPath;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.route.bus.BusDetailListAdapter;
import com.jiayuqicz.mygaode.route.car.CarDetailListAdapter;
import com.jiayuqicz.mygaode.route.walk.WalkDetailListAdapter;

public class RouteDetailActivity extends AppCompatActivity {

    private BusPath busPath = null;
    private DrivePath drivePath = null;
    private WalkPath walkPath =null;

    private static final int bus = 0;
    private static final int car = 1;
    private static final int walk = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        intView();
    }

    private void intView() {
        Intent intent = getIntent();
        int type = intent.getIntExtra(RouteActivity.ROUTE_TYPE, 0);
        switch (type) {
            case bus: {
                busPath = intent.getParcelableExtra(RouteActivity.DETAIL_INTENT);
                ListView detailList = (ListView) findViewById(R.id.bus_detial);
                BusDetailListAdapter adapter = new BusDetailListAdapter(this, busPath.getSteps());
                detailList.setAdapter(adapter);
                break;
            }
            case car: {
                drivePath = intent.getParcelableExtra(RouteActivity.DETAIL_INTENT);
                ListView detailList = (ListView) findViewById(R.id.bus_detial);
                CarDetailListAdapter adapter = new CarDetailListAdapter(this, drivePath.getSteps());
                detailList.setAdapter(adapter);
                break;
            }
            case walk: {
                walkPath = intent.getParcelableExtra(RouteActivity.DETAIL_INTENT);
                ListView detalList = (ListView) findViewById(R.id.bus_detial);
                WalkDetailListAdapter adapter = new WalkDetailListAdapter(this,walkPath.getSteps());
                detalList.setAdapter(adapter);
                break;
            }
        }

    }
}
