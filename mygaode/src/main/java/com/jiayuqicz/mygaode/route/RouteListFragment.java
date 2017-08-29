package com.jiayuqicz.mygaode.route;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.Path;
import com.amap.api.services.route.WalkPath;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.route.bus.BusDetailListAdapter;
import com.jiayuqicz.mygaode.route.car.CarDetailListAdapter;
import com.jiayuqicz.mygaode.route.walk.WalkDetailListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteListFragment extends Fragment {

    private Path path = null;
    private ListView detailList = null;
    private int type;

    private static final int bus = 0;
    private static final int car = 1;
    private static final int walk = 2;

    private static String BUNDLE_PATH = "path";
    private static String BUNDLE_INT = "int";

    public static RouteListFragment newInstance(Path path, int type) {
        RouteListFragment fragment = new RouteListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_PATH, path);
        bundle.putInt(BUNDLE_INT, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        path = getArguments().getParcelable(BUNDLE_PATH);
        type = getArguments().getInt(BUNDLE_INT);
        return inflater.inflate(R.layout.route_fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailList = (ListView) getView().findViewById(R.id.bus_detail);
        switchRouteType(type);
    }



    public void switchRouteType(int type) {

        switch (type) {
            case bus: {
                BusPath path = (BusPath) this.path;
                BusDetailListAdapter adapter = new BusDetailListAdapter(getActivity(), path.getSteps());
                detailList.setAdapter(adapter);
                break;
            }
            case car: {
                DrivePath path = (DrivePath) this.path;
                CarDetailListAdapter adapter = new CarDetailListAdapter(getActivity(), path.getSteps());
                detailList.setAdapter(adapter);
                break;
            }
            case walk: {
                WalkPath path = (WalkPath) this.path;
                WalkDetailListAdapter adapter = new WalkDetailListAdapter(getActivity(),path.getSteps());
                detailList.setAdapter(adapter);
                break;
            }
        }

    }

}
