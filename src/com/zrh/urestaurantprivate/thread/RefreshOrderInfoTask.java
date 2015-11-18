package com.zrh.urestaurantprivate.thread;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zrh.urestaurantprivate.adapter.OrderListAdapter;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.entitydb.OrderDetailsDB;
import com.zrh.urestaurantprivate.entitydb.OrderInfoDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshListView;
import com.zrh.urestaurantprivate.umenu.OrderDetails;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

/**
 * @copyright 中荣恒科技有限公司
 * @function 订单查询接口封装类
 * @author 杨光勤
 * @version v1.2
 * @date 2015-05-27
 * @deprecated 封装获取数据类，用于各类订单查询提供接口查询
 */
public class RefreshOrderInfoTask extends AsyncTask<Void, Void, String>{
	
	private Context mContext;
	private SharePreferenceUtil sp;
	
	private String orderString;
	private JSONObject jsonObject;
	private List<OrderInfo> listOrderInfo;
	private OrderInfoDB oInfoDB;
	private OrderDetailsDB oDetailsDb;
	
	private List<JSONObject> jsonObjectList;
	private List<MenuDetails> listMenuDetails;
	
	private OrderListAdapter orderListAdapter;
	
	private PullToRefreshListView refreshListView;
	
	private String order_status = "1";
	
	private RefreshOrderInfoTask(){
		
	}
	public RefreshOrderInfoTask(Context context,PullToRefreshListView refreshListView,String order_status){
		this.mContext = context;
		this.refreshListView = refreshListView;
		this.order_status = order_status;
		
		oInfoDB = new OrderInfoDB(context);
		oDetailsDb = new OrderDetailsDB(context);
		
		listOrderInfo = oInfoDB.getInfoByOrder_status(order_status);
		listMenuDetails = oDetailsDb.getOrderInfoMsg();
		
		sp = new SharePreferenceUtil(context, MyConstants.SAVE_USER);
		orderListAdapter = new OrderListAdapter(listOrderInfo, context);
		
		ListView listView = refreshListView.getRefreshableView();
		listView.setAdapter(orderListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, OrderDetails.class);

				oDetailsDb = new OrderDetailsDB(mContext);
				listMenuDetails = oDetailsDb.getInfoByOrderId(listOrderInfo.get(
						//listOrderInfo.size() - 1 - arg2).getOrderNo());
						listOrderInfo.size() - arg2).getOrderNo());
				Log.e("Test", "the onItemClick 1236 ");
				intent.putExtra("menuDetails", (Serializable) listMenuDetails);
				intent.putExtra(
						"orderInfo",
						//(Serializable) listOrderInfo.get(listOrderInfo.size() - 1
						(Serializable) listOrderInfo.get(listOrderInfo.size()
								- arg2));

				mContext.startActivity(intent);
			}
			
		});
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		Map<String, String> map = ServerAPI.getInstance(mContext).getOrderDeal(
				sp.getAppID());

		// TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("c", "order");
		// map.put("a", "hqorder");
		// map.put("appid", sp.getAppID());
		// 获取json数据
		orderString = HttpUtils.getJsoneq(map);
		Log.e("Test", "orderString  is running :" + orderString);
		
		return orderString;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (orderString.equals("false")) {
			return;
		} else {
			jsonObject = JsonTools.parseJson(orderString);
			listOrderInfo = JsonTools.getOrderData(jsonObject);
			if (listOrderInfo != null && listOrderInfo.size() > 0) {
				// 把数据加入数据库
				oInfoDB = new OrderInfoDB(mContext);
				oInfoDB.delete();
				oInfoDB.addOrderInfo(listOrderInfo);
				//oInfoDB.close();
				jsonObjectList = JsonTools.getJsonList(jsonObject);
				//
				oDetailsDb = new OrderDetailsDB(mContext);
				oDetailsDb.delete();
				for (int j = 0; j < jsonObjectList.size(); j++) {
					listMenuDetails = JsonTools.getMenuData(jsonObjectList
							.get(j));
					oDetailsDb.addOrderInfo(listMenuDetails);

				}
				oDetailsDb.close();
			}
			if(order_status.equalsIgnoreCase("200")){
				listOrderInfo = oInfoDB.getInfoByOrder_status("7");
				listOrderInfo.addAll(oInfoDB.getInfoByOrder_status("8"));
				listOrderInfo.addAll(oInfoDB.getInfoByOrder_status("9"));
				listOrderInfo.addAll(oInfoDB.getInfoByOrder_status("10"));
				oInfoDB.close();
			}else{
				listOrderInfo = oInfoDB.getInfoByOrder_status(order_status);
				oInfoDB.close();
			}
			
			
//			orderListAdapter.notifyDataSetChanged();
			orderListAdapter = new OrderListAdapter(listOrderInfo, mContext);
			refreshListView.setAdapter(orderListAdapter);
		}
		refreshListView.onRefreshComplete();
	}
}
