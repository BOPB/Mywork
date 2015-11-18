package com.zrh.urestaurantprivate.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;

import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.entitydb.OrderDetailsDB;
import com.zrh.urestaurantprivate.entitydb.OrderInfoDB;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

/**
 * @copyright 中荣恒科技有限公司
 * @function 获取订单信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class OrderInfoThread extends Thread {
	private List<JSONObject> jsonObjectList;// 订单信息的JSONObject集合
	private List<MenuDetails> listMenuDetails;// 获取解析的菜品详情
	private List<OrderInfo> listOrderInfo;// 预定订单部分
	private OrderInfoDB oInfoDB;// 订单信息数据库
	private OrderDetailsDB oDetailsDb;// 订单菜品详情数据库
	private String orderString;// 放返回的订单json数据
	private JSONObject jsonObject;
	private Context context;
	private SharePreferenceUtil sp;

	public OrderInfoThread(Context context) {
		this.context = context;
		sp = new SharePreferenceUtil(context, MyConstants.SAVE_USER);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> map = ServerAPI.getInstance(context).getOrderDeal(
				sp.getAppID());

		// TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("c", "order");
		// map.put("a", "hqorder");
		// map.put("appid", sp.getAppID());
		// 获取json数据
		orderString = HttpUtils.getJsoneq(map);
		Log.e("Test", "orderString  is running :" + orderString);
		if (orderString.equals("false")) {
			return;
		} else {
			jsonObject = JsonTools.parseJson(orderString);
			listOrderInfo = JsonTools.getOrderData(jsonObject);
			if (listOrderInfo != null && listOrderInfo.size() > 0) {
				// 把数据加入数据库
				oInfoDB = new OrderInfoDB(context);
				oInfoDB.delete();
				oInfoDB.addOrderInfo(listOrderInfo);
				oInfoDB.close();
				jsonObjectList = JsonTools.getJsonList(jsonObject);
				//
				oDetailsDb = new OrderDetailsDB(context);
				oDetailsDb.delete();
				for (int j = 0; j < jsonObjectList.size(); j++) {
					listMenuDetails = JsonTools.getMenuData(jsonObjectList
							.get(j));
					oDetailsDb.addOrderInfo(listMenuDetails);

				}
				oDetailsDb.close();
			}

		}

	}
}
