package com.zrh.urestaurantprivate.entitydb;

/**********************************************************
 Copyright: 2013, 深圳市中荣恒科技有限公司
 File name: CatDB
 Description: 菜品分类
 Author: 易日霖
 Version: v0.9
 Date: 完成日期：2013-09-04
 History:  修改日期:
 修改者:
 修改内容简述:
 **********************************************************/
import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.httputils.MyConstants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @copyright 中荣恒科技有限公司
 * @function  订单信息数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class OrderInfoDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public OrderInfoDB(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE table IF NOT EXISTS orderInfo"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, order_status TEXT,pay_status TEXT,orderTotalPrice TEXT"
				+ ",orderTiem TEXT,table_id TEXT,waiter_id TEXT,orderNo TEXT,remarks TEXT,phone TEXT)");
		Log.e("订单信息", "订单信息表创建成功");
	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void addOrderInfo(List<OrderInfo> list) {
		for (OrderInfo orderInfo : list) {
			db.execSQL(
					"insert into orderInfo(order_status,pay_status,orderTotalPrice,"
							+ "orderTiem,table_id,waiter_id,orderNo,remarks,phone) values(?,?,?,?,?,?,?,?,?)",
					new Object[] { orderInfo.getOrder_status(),
							orderInfo.getPay_status(),
							orderInfo.getOrderTotalPrice(),
							orderInfo.getOrderTiem(), orderInfo.getTable_id(),
							orderInfo.getWaiter_id(), orderInfo.getOrderNo(),
							orderInfo.getRemarks(), orderInfo.getPhone() });
		}
	}

	/**********************************************************
	 * Function: getAddressMsg Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 环境信息 Others: 其它说明
	 *********************************************************/
	public List<OrderInfo> getOrderInfoMsg() {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		Cursor c = db.rawQuery("SELECT * from orderInfo", null);
		while (c.moveToNext()) {

			int order_status = c.getInt(c.getColumnIndex("order_status"));
			int pay_status = c.getInt(c.getColumnIndex("pay_status"));
			String remarks = c.getString(c.getColumnIndex("remarks"));
			String orderNo = c.getString(c.getColumnIndex("orderNo"));
			String orderTiem = c.getString(c.getColumnIndex("orderTiem"));
			String waiter_id = c.getString(c.getColumnIndex("waiter_id"));
			String table_id = c.getString(c.getColumnIndex("table_id"));
			String phone = c.getString(c.getColumnIndex("phone"));
			double orderTotalPrice = c.getDouble(c
					.getColumnIndex("orderTotalPrice"));

			OrderInfo info = new OrderInfo();
			info.setPay_status(pay_status);
			info.setOrder_status(order_status);
			info.setRemarks(remarks);
			info.setOrderNo(orderNo);
			info.setOrderTiem(orderTiem);
			info.setWaiter_id(waiter_id);
			info.setTable_id(table_id);
			info.setOrderTotalPrice(orderTotalPrice);
			info.setPhone(phone);
			list.add(info);
		}
		c.close();
		return list;
	}

	/**********************************************************
	 * Function: delete Description: 删除信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void delete() {
		db.execSQL("delete from orderInfo");
	}

	/**********************************************************
	 * Function: close Description: 关闭数据库 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void close() {
		if (db != null)
			db.close();
	}

	public List<OrderInfo> getInfoByOrder_status(String order_status) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		Cursor c = db.rawQuery("select * from orderInfo where order_status='"
				+ order_status + "'", null);// phone + ""
		while (c.moveToNext()) {
			int order_status1 = c.getInt(c.getColumnIndex("order_status"));
			int pay_status = c.getInt(c.getColumnIndex("pay_status"));
			String remarks = c.getString(c.getColumnIndex("remarks"));
			String orderNo = c.getString(c.getColumnIndex("orderNo"));
			String orderTiem = c.getString(c.getColumnIndex("orderTiem"));
			String waiter_id = c.getString(c.getColumnIndex("waiter_id"));
			String table_id = c.getString(c.getColumnIndex("table_id"));
			String phone = c.getString(c.getColumnIndex("phone"));
			double orderTotalPrice = c.getDouble(c
					.getColumnIndex("orderTotalPrice"));

			OrderInfo info = new OrderInfo();
			info.setPay_status(pay_status);
			info.setOrder_status(order_status1);
			info.setRemarks(remarks);
			info.setOrderNo(orderNo);
			info.setOrderTiem(orderTiem);
			info.setWaiter_id(waiter_id);
			info.setTable_id(table_id);
			info.setOrderTotalPrice(orderTotalPrice);
			info.setPhone(phone);
			list.add(info);
		}
		c.close();
		return list;
	}
}
