package com.jiayuqicz.mygaode.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.amap.api.services.core.LatLonPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wzq on 2017/8/21.
 */

public class MySimpleAdapter extends SimpleAdapter {

    private List<? extends Map<String, ?>> dataList = null;

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        dataList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        Map data = dataList.get(position);
        LatLonPoint point = (LatLonPoint) data.get("point");
        //添加位置数据
        view.setTag(point);
        return view;
    }

    public Set<LatLonPoint> getLatLonPoints() {
        return getAdressMap().keySet();
    }

    public HashMap<LatLonPoint, String> getAdressMap () {
        HashMap<LatLonPoint, String> hashMap = new HashMap<>();
        for(Map map : dataList) {
            LatLonPoint point = (LatLonPoint) map.get("point");
            String name = (String) map.get("name");
            hashMap.put(point, name);
        }
        return hashMap;
    }

}
