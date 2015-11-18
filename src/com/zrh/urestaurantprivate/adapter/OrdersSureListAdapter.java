package com.zrh.urestaurantprivate.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrh.urestaurantprivate.umenu.R;
import com.zrh.urestaurantprivate.umenu.Upagetwo;
/**
 * @copyright 中荣恒科技有限公司
 * @function  订单确认界面适配器
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class OrdersSureListAdapter extends BaseAdapter {

	private Upagetwo.MyHandler mHandler = null;

	private class ViewHolder {
		TextView tv1 = null;
		TextView ornum = null;
		TextView money = null;
	}

	private static ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private ViewHolder holder;

	public OrdersSureListAdapter(Context c,
			ArrayList<HashMap<String, Object>> appList, int resource,
			String[] from, int[] to) {
		mAppList = appList;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
	}

	@Override
	public int getCount() {
		return mAppList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeItem(int position) {
		mAppList.remove(position);
		this.notifyDataSetChanged();
	}

	public static void removeAll() {
		mAppList.removeAll(mAppList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.order_sure_view, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(valueViewID[0]);
			holder.money = (TextView) convertView.findViewById(valueViewID[1]);
			holder.ornum = (TextView) convertView.findViewById(valueViewID[2]);
			convertView.setTag(holder);
		}

		HashMap<String, Object> appInfo = mAppList.get(position);
		if (appInfo != null) {
			String aname0 = (String) appInfo.get(keyString[0]);
			String aname1 = (String) appInfo.get(keyString[1]);
			String aname2 = (String) appInfo.get(keyString[2]);
			holder.tv1.setText(aname0);
			holder.money.setText(aname1);
			holder.ornum.setText(aname2);
			
			
		}
		return convertView;
	}

}
