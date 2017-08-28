package com.jiayuqicz.mygaode.route.walk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.util.AMapUtil;

import java.util.List;

public class WalkResultListAdapter extends BaseAdapter {
	private Context mContext;
	private List<WalkPath> drivePaths;

	public WalkResultListAdapter(Context context, WalkRouteResult walkRouteResult) {
		mContext = context;
		drivePaths = walkRouteResult.getPaths();
	}
	
	@Override
	public int getCount() {
		return drivePaths.size();
	}

	@Override
	public Object getItem(int position) {
		return drivePaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.route_result_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.bus_item_title);
			holder.des = (TextView) convertView.findViewById(R.id.bus_item_des);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final WalkPath item = drivePaths.get(position);
		holder.title.setText(AMapUtil.getWalkPathTitle(item));
		holder.des.setText(AMapUtil.getWalkPathDes(item));

		
		return convertView;
	}
	
	private class ViewHolder {
		private TextView title;
		private TextView des;
	}

}
