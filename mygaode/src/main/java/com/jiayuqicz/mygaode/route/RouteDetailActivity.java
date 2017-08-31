package com.jiayuqicz.mygaode.route;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.services.route.Path;
import com.amap.api.services.route.RouteResult;
import com.jiayuqicz.mygaode.R;


public class RouteDetailActivity extends AppCompatActivity {

    private RouteMapFragment mapFragment;
    private RouteListFragment listFragment = null;
    private FragmentTransaction transaction = null;
    private RouteResult result = null;
    private Path path;

    private boolean flag = false;
    private int routeType;

    private final int bus = 0;
    private final int car = 1;
    private final int walk = 2;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        intView();
        addFragmentInvisible(mapFragment);
        addFragment(listFragment);
        switchRouteType(routeType);
    }

    private void intView() {
        Intent intent = getIntent();
        routeType = intent.getIntExtra(RouteActivity.ROUTE_TYPE, 0);
        path = intent.getParcelableExtra(RouteActivity.DETAIL_INTENT);
        result = intent.getParcelableExtra(RouteActivity.ROUTE_RESULT);

        mapFragment = RouteMapFragment.newInstance(path, result, routeType);
        listFragment = RouteListFragment.newInstance(path, routeType);
        button = (Button) findViewById(R.id.switch_button);
    }

    private void showFragment(Fragment fragment) {

        transaction = getFragmentManager().beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment) {

        transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    private void addFragmentInvisible(Fragment fragment) {

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.detail_container, fragment);
        transaction.hide(fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment) {

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.detail_container, fragment);
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
        if (flag) {
            hideFragment(listFragment);
            showFragment(mapFragment);
            button.setText(R.string.detail_button);
        }
        else {
            hideFragment(mapFragment);
            showFragment(listFragment);
            button.setText(R.string.map_button);
        }

    }
}
