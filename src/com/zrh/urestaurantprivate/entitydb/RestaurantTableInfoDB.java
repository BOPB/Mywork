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
import com.zrh.urestaurantprivate.bean.Restaurant;
import com.zrh.urestaurantprivate.bean.RestaurantTableInfo;
import com.zrh.urestaurantprivate.httputils.MyConstants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @copyright 中荣恒科技有限公司
 * @function  餐桌信息数据库
 * @author 杨光勤
 * @version v1.1
 * @date 2015-02-06
 */
public class RestaurantTableInfoDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public RestaurantTableInfoDB(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE table IF NOT EXISTS tableInfo"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, TableID TEXT,TableType INTEGER,TableNO INTEGER"
				+ ",TableLocationId INTEGER,TableSeatCount INTEGER,TableIsFree INTEGER,TableMergeInfo INTEGER,TableOrderInfo TEXT,TableOther TEXT,"
				+ "TableName TEXT,isBan INTEGER)");
		Log.e("订单信息", "订单信息表创建成功");
	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void addTableInfo(List<RestaurantTableInfo> list) {
		for (RestaurantTableInfo tableInfo : list) {
			db.execSQL(
					"insert into tableInfo( TableID,TableType,TableNO,TableLocationId," +
					"TableSeatCount,TableIsFree,TableMergeInfo,TableOrderInfo,TableOther,"
				+ "TableName,isBan) values(?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { tableInfo.getTableID(),
							tableInfo.getTableType(),
							tableInfo.getTableNO(),
							tableInfo.getTableLocationId(), tableInfo.getTableSeatCount(),
							tableInfo.getTableIsFree(), tableInfo.getTableMergeInfo(),
							tableInfo.getTableOrderInfo(), tableInfo.getTableOther(),
							tableInfo.getTableName(),tableInfo.getIsBan()});
		}
	}

	/**********************************************************
	 * Function: getAddressMsg Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 环境信息 Others: 其它说明
	 *********************************************************/
	public List<RestaurantTableInfo> getTableInfoMsg() {
		List<RestaurantTableInfo> list = new ArrayList<RestaurantTableInfo>();
		Cursor c = db.rawQuery("SELECT * from tableInfo", null);
		while (c.moveToNext()) {

			String TableID = c.getString(c.getColumnIndex("TableID"));
			int TableType = c.getInt(c.getColumnIndex("TableType"));
			int TableNO = c.getInt(c.getColumnIndex("TableNO"));
			int TableLocationId = c.getInt(c.getColumnIndex("TableLocationId"));
			int TableSeatCount = c.getInt(c.getColumnIndex("TableSeatCount"));
			int TableIsFree = c.getInt(c.getColumnIndex("TableIsFree"));
			int TableMergeInfo = c.getInt(c.getColumnIndex("TableMergeInfo"));
			String TableOrderInfo = c.getString(c.getColumnIndex("TableOrderInfo"));
			String TableOther = c.getString(c.getColumnIndex("TableOther"));
			String TableName = c.getString(c.getColumnIndex("TableName"));
			int isBan = c.getInt(c.getColumnIndex("isBan"));

			RestaurantTableInfo tableInfo = new RestaurantTableInfo();
			tableInfo.setTableID(TableID);
			tableInfo.setTableType(TableType);
			tableInfo.setTableNO(TableNO);
			tableInfo.setTableLocationId(TableLocationId);
			tableInfo.setTableSeatCount(TableSeatCount);
			tableInfo.setTableIsFree(TableIsFree);
			tableInfo.setTableMergeInfo(TableMergeInfo);
			tableInfo.setTableOrderInfo(TableOrderInfo);
			tableInfo.setTableOther(TableOther);
			tableInfo.setTableName(TableName);
			tableInfo.setIsBan(isBan);
			
			list.add(tableInfo);
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
		db.execSQL("delete from tableInfo");
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
