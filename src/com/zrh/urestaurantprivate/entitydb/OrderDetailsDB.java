package com.zrh.urestaurantprivate.entitydb;

import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.httputils.MyConstants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @copyright 中荣恒科技有限公司
 * @function  订单详情数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class OrderDetailsDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public OrderDetailsDB(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE table IF NOT EXISTS orderDetails"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,order_id TEXT,amount TEXT"
				+ ",price TEXT,discount TEXT)");
		Log.e("订单详情信息", "订单详情信息表创建成功");
	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void addOrderInfo(List<MenuDetails> list) {
		if (list != null && list.size()>0) {
			
			for (MenuDetails orderInfo : list) {
				db.execSQL("insert into orderDetails(name,order_id,amount,"
						+ "price,discount) values(?,?,?,?,?)",
						new Object[] { orderInfo.getName(),
								orderInfo.getOrder_id(), orderInfo.getAmount(),
								orderInfo.getPrice(), orderInfo.getDiscount() });
			}
		}
	}

	/**********************************************************
	 * Function: getAddressMsg Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 环境信息 Others: 其它说明
	 *********************************************************/
	public List<MenuDetails> getOrderInfoMsg() {
		List<MenuDetails> list = new ArrayList<MenuDetails>();
		Cursor c = db.rawQuery("SELECT * from orderDetails", null);
		while (c.moveToNext()) {

			int amount = c.getInt(c.getColumnIndex("amount"));
			String name = c.getString(c.getColumnIndex("name"));
			String order_id = c.getString(c.getColumnIndex("order_id"));
			double price = c.getDouble(c.getColumnIndex("price"));
			double discount = c.getDouble(c.getColumnIndex("discount"));

			MenuDetails info = new MenuDetails();
			info.setName(name);
			info.setAmount(amount);
			info.setDiscount(discount);
			info.setOrder_id(order_id);
			info.setPrice(price);
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
		db.execSQL("delete from orderDetails");
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

	/**
	 * 根据订单号找指定订单详情
	 * 
	 * @param order_id
	 * @return
	 */
	public List<MenuDetails> getInfoByOrderId(String order_id) {

		List<MenuDetails> list = new ArrayList<MenuDetails>();
		Cursor c = db.rawQuery("select * from orderDetails where order_id=?",
				new String[] { order_id });// phone + ""
		while (c.moveToNext()) {
			int amount = c.getInt(c.getColumnIndex("amount"));
			String name = c.getString(c.getColumnIndex("name"));
			String order_ids = c.getString(c.getColumnIndex("order_id"));
			double price = c.getDouble(c.getColumnIndex("price"));
			double discount = c.getDouble(c.getColumnIndex("discount"));

			MenuDetails info = new MenuDetails();
			info.setName(name);
			info.setAmount(amount);
			info.setDiscount(discount);
			info.setOrder_id(order_ids);
			info.setPrice(price);
			list.add(info);
		}
		c.close();
		return list;

	}
}
