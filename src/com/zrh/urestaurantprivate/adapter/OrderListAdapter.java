package com.zrh.urestaurantprivate.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.umenu.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @copyright 中荣恒科技有限公司
 * @function 订单信息适配器
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class OrderListAdapter extends BaseAdapter {
	private List<OrderInfo> listInfo;
	private Context mContext;
	private ViewHolder holder;

	public OrderListAdapter(List<OrderInfo> listInfo, Context mContext) {
		super();
		this.listInfo = listInfo;
		this.mContext = mContext;

	}

	private class ViewHolder {
		TextView textNo;
		TextView textNum;
		TextView textPrice;
		TextView textTime;
		TextView textPhone;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e("TAGs", "the line  35");
		return listInfo.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 != null) {
			holder = (ViewHolder) arg1.getTag();
		} else {
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.listviewcontent, null);
			holder = new ViewHolder();
			holder.textNum = (TextView) arg1.findViewById(R.id.tv_numbel);
			holder.textNo = (TextView) arg1.findViewById(R.id.tv_listcontent2);
			holder.textPrice = (TextView) arg1
					.findViewById(R.id.tv_listcontent6);
			holder.textTime = (TextView) arg1
					.findViewById(R.id.tv_listcontent4);
			holder.textPhone = (TextView) arg1.findViewById(R.id.tv_phones);
			arg1.setTag(holder);
		}
		holder.textNum.setText(arg0 + 1 + "");
		holder.textNo.setText(""
				+ listInfo.get(listInfo.size() - 1 - arg0).getOrderNo());
		holder.textPrice
				.setText("¥"
						+ listInfo.get(listInfo.size() - 1 - arg0)
								.getOrderTotalPrice());
		holder.textTime.setText(""
				+ listInfo.get(listInfo.size() - 1 - arg0).getOrderTiem());
		if (listInfo.get(listInfo.size() - 1 - arg0).getPhone() == null
				|| listInfo.get(listInfo.size() - 1 - arg0).getPhone()
						.equals("")) {
			holder.textPhone.setText("无");
		} else {
			holder.textPhone.setText(""
					+ listInfo.get(listInfo.size() - 1 - arg0).getPhone());
		}
		return arg1;
	}
}
