package com.jiayuqicz.mygaode.route;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.Path;
import com.amap.api.services.route.RouteResult;
import com.amap.api.services.route.WalkPath;
import com.jiayuqicz.mygaode.map.BaseFragment;
import com.jiayuqicz.mygaode.overlay.BusRouteOverlay;
import com.jiayuqicz.mygaode.overlay.DrivingRouteOverlay;
import com.jiayuqicz.mygaode.overlay.WalkRouteOverlay;

import static com.jiayuqicz.mygaode.route.RouteActivity.bus;
import static com.jiayuqicz.mygaode.route.RouteActivity.car;
import static com.jiayuqicz.mygaode.route.RouteActivity.walk;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteMapFragment extends BaseFragment {

    private static String RESULT = "result";
    private static String PATH = "path";
    private static String TYPE = "type";

    private int type;
    private RouteResult result;
    private Path path;

    public static RouteMapFragment newInstance(Path path, RouteResult result, int type) {
        
        Bundle args = new Bundle();
        args.putParcelable(RESULT, result);
        args.putParcelable(PATH, path);
        args.putInt(TYPE, type);
        RouteMapFragment fragment = new RouteMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);

        switch (type) {
            case bus: {
                BusRouteOverlay overlay = new BusRouteOverlay(getActivity(),aMap, (BusPath) path,
                        result.getStartPos(), result.getTargetPos());
                overlay.removeFromMap();
                overlay.addToMap();
                overlay.zoomToSpan();
                break;
            }
            case car: {
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(getActivity(),aMap,
                        (DrivePath) path, result.getStartPos(), result.getTargetPos(), null);
                overlay.removeFromMap();
                overlay.addToMap();
                overlay.zoomToSpan();
                break;
            }
            case walk: {
                WalkRouteOverlay overlay = new WalkRouteOverlay(getActivity(),aMap, (WalkPath) path,
                        result.getStartPos(), result.getTargetPos());
                overlay.removeFromMap();
                overlay.addToMap();
                overlay.zoomToSpan();
                break;
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        result = getArguments().getParcelable(RESULT);
        path = getArguments().getParcelable(PATH);
        type = getArguments().getInt(TYPE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
