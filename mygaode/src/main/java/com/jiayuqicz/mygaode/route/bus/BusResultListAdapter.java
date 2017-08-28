package com.jiayuqicz.mygaode.route.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.util.AMapUtil;

import java.util.List;

public class BusResultListAdapter extends BaseAdapter {
	private Context mContext;
	private List<BusPath> mBusPathList;

	public BusResultListAdapter(Context context, BusRouteResult busrouteresult) {
		mContext = context;
		mBusPathList = busrouteresult.getPaths();
	}
	
	@Override
	public int getCount() {
		return mBusPathList.size();
	}

	@Override
	public Object getItem(int position) {
		return mBusPathList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.route_result_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.bus_item_title);
			holder.des = (TextView) convertView.findViewById(R.id.bus_item_des);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final BusPath item = mBusPathList.get(position);
		holder.title.setText(AMapUtil.getBusPathTitle(item));
		holder.des.setText(AMapUtil.getBusPathDes(item));

		
		return convertView;
	}
	
	private class ViewHolder {
		private TextView title;
		private TextView des;
	}

}
