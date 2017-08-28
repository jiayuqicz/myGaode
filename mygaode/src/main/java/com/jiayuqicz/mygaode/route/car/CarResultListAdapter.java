package com.jiayuqicz.mygaode.route.car;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.util.AMapUtil;

import java.util.List;

public class CarResultListAdapter extends BaseAdapter {
	private Context mContext;
	private List<DrivePath> drivePaths;

	public CarResultListAdapter(Context context, DriveRouteResult driveRouteResult) {
		mContext = context;
		drivePaths = driveRouteResult.getPaths();
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
		
		final DrivePath item = drivePaths.get(position);
		holder.title.setText(AMapUtil.getDrivePathTitle(item));
		holder.des.setText(AMapUtil.getDrivePathDes(item));

		
		return convertView;
	}
	
	private class ViewHolder {
		private TextView title;
		private TextView des;
	}

}
