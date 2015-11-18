package com.zrh.urestaurantprivate.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zrh.urestaurantprivate.umenu.R;
import com.zrh.urestaurantprivate.umenu.Upagetwo;
/**
 * @copyright 中荣恒科技有限公司
 * @function  点菜显示适配器，upagetwo中点击图片点菜上面显示的订单列表
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class OrdersListViewAdapter extends BaseAdapter {

	private Upagetwo.MyHandler mHandler = null;
	public static int ps = -1, pu = -1;

	private class ViewHolder {
		Button button = null;
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

	public OrdersListViewAdapter(Context c,
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
			convertView = mInflater.inflate(R.layout.uselected_view, null);
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(valueViewID[0]);
			holder.money = (TextView) convertView.findViewById(valueViewID[1]);
			holder.ornum = (TextView) convertView.findViewById(valueViewID[2]);
			holder.button = (Button) convertView.findViewById(valueViewID[3]);
			convertView.setTag(holder);
		}

		HashMap<String, Object> appInfo = mAppList.get(position);
		if (appInfo != null) {
			String aname0 = (String) appInfo.get(keyString[0]);
			String aname1 = (String) appInfo.get(keyString[1]);
			String aname2 = (String) appInfo.get(keyString[2]);
			String aname3 = (String) appInfo.get(keyString[3]);
			holder.tv1.setText(aname0);
			holder.money.setText(aname1);
			holder.ornum.setText(aname2);
			holder.button.setText(aname3);
			holder.button.setOnClickListener(new ButtonListener(position));
			
			
		}
		return convertView;
	}

	class ButtonListener implements OnClickListener {
		private int position;

		ButtonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == holder.button.getId()) {
				if (((Button) v).getText().equals("删除")) {
					mHandler = Upagetwo.getHandler();
					mHandler.sendEmptyMessage(position + 1000);
				}
			}
		}
	}

}
