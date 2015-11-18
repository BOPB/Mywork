package com.zrh.urestaurantprivate.entitydb;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zrh.urestaurantprivate.bean.Restaurant;
import com.zrh.urestaurantprivate.httputils.MyConstants;
/**
 * @copyright 中荣恒科技有限公司
 * @function  餐厅信息数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class RestaurantInfoDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public RestaurantInfoDB(Context context) {
		this.context=context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE table IF NOT EXISTS restaurantinfo"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, android_app_qrcode TEXT,ios_app_qrcode TEXT,shop_name TEXT,business_hours_begin TEXT,"
				+ "business_hours_end TEXT,shop_phone TEXT,shop_address TEXT,host_domain TEXT,order_time_limit TEXT,order_times_perday_limit TEXT,discount_num_perorder_limit TEXT)");
	}

	/**********************************************************
	 * Function: saveInfo Description: 保存信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 信息内容 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public void saveInfo(Restaurant r) {
		db.execSQL(
				"insert into restaurantinfo(android_app_qrcode,ios_app_qrcode,shop_name,business_hours_begin,business_hours_end,shop_phone,shop_address,"
						+ "host_domain,order_time_limit,order_times_perday_limit,discount_num_perorder_limit) values(?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { r.getAndroid_app_qrcode(),
						r.getIos_app_qrcode(), r.getShop_name(),
						r.getBusiness_hours_begin(), r.getBusiness_hours_end(),
						r.getShop_phone(), r.getShop_address(),
						r.getHost_domain(), r.getOrder_time_limit(),
						r.getOrder_times_perday_limit(),r.getDiscount_num_perorder_limit() });
	}

	/**********************************************************
	 * Function: getInfo Description: 获取个人信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public Restaurant getInfo() {
		Restaurant r = new Restaurant();
		Cursor c = db.rawQuery("SELECT * from restaurantinfo", null);
		if (c.moveToFirst()) {
			r.setAndroid_app_qrcode(c.getString(c.getColumnIndex("android_app_qrcode")));
			r.setIos_app_qrcode(c.getString(c.getColumnIndex("ios_app_qrcode")));
			r.setShop_name(c.getString(c.getColumnIndex("shop_name")));
			r.setBusiness_hours_begin(c.getInt(c.getColumnIndex("business_hours_begin")));
			r.setBusiness_hours_end(c.getInt(c.getColumnIndex("business_hours_end")));
			r.setShop_phone(c.getString(c.getColumnIndex("shop_phone")));
			r.setShop_address(c.getString(c.getColumnIndex("shop_address")));
			r.setHost_domain(c.getString(c.getColumnIndex("host_domain")));
			r.setOrder_time_limit(c.getInt(c.getColumnIndex("order_time_limit")));
			r.setOrder_times_perday_limit(c.getInt(c.getColumnIndex("order_times_perday_limit")));
			r.setDiscount_num_perorder_limit(c.getInt(c.getColumnIndex("discount_num_perorder_limit")));
		} else {
			r = null;
		}
		c.close();
		return r;
	}

	
	
	/**********************************************************
	 * Function: delete Description: 删除信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void delete() {
		db.execSQL("delete from restaurantinfo");
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
}
