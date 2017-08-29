package com.jiayuqicz.mygaode.route;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.route.Path;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.map.MapFragment;


public class RouteDetailActivity extends AppCompatActivity {

    private MapFragment mapFragment = null;
    private RouteListFragment listFragment = null;
    private FragmentTransaction transaction = null;

    private boolean flag = false;
    private int routeType;

    private static final int bus = 0;
    private static final int car = 1;
    private static final int walk = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        intView();
        addFragment(listFragment);
        switchRouteType(routeType);
    }

    private void intView() {
        Intent intent = getIntent();
        routeType = intent.getIntExtra(RouteActivity.ROUTE_TYPE, 0);
        Path path = intent.getParcelableExtra(RouteActivity.DETAIL_INTENT);
        mapFragment = MapFragment.newInstance();
        listFragment = RouteListFragment.newInstance(path, routeType);
    }

    private void addFragment(Fragment fragment) {
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.detail_container, fragment);
        transaction.isAddToBackStackAllowed();
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_container, fragment);
        transaction.isAddToBackStackAllowed();
        transaction.commit();
    }

    public void switchRouteType(int type) {

        TextView title = (TextView) findViewById(R.id.detail_title);

        switch (type) {
            case bus: {
                //设置标题
                title.setText(getString(R.string.detail_title_bus));
                break;
            }
            case car: {
                title.setText(getString(R.string.detail_title_car));
                break;
            }
            case walk: {
                title.setText(getString(R.string.detail_title_walk));
                break;
            }
        }
    }

    public void switchMap(View view) {
        flag = !flag;
        if(flag) {
            replaceFragment(mapFragment);
            return;
        }
        replaceFragment(listFragment);
    }
}
