package com.jiayuqicz.mygaode.route;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.Path;
import com.amap.api.services.route.RouteResult;
import com.jiayuqicz.mygaode.map.BaseFragment;
import com.jiayuqicz.mygaode.overlay.BusRouteOverlay;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteMapFragment extends BaseFragment {

    private static String RESULT = "result";
    private static String PATH = "path";

    private RouteResult result;
    private Path path;

    public static RouteMapFragment newInstance(Path path, RouteResult result) {
        
        Bundle args = new Bundle();
        args.putParcelable(RESULT, result);
        args.putParcelable(PATH, path);
        RouteMapFragment fragment = new RouteMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);
        BusRouteOverlay overlay = new BusRouteOverlay(getActivity(),aMap, (BusPath) path,result.getStartPos(),
                result.getTargetPos());
        overlay.removeFromMap();
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        result = getArguments().getParcelable(RESULT);
        path = getArguments().getParcelable(PATH);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
