package com.zrh.urestaurantprivate.entitydb;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zrh.urestaurantprivate.bean.MenuCategory;
import com.zrh.urestaurantprivate.httputils.MyConstants;
/**
 * @copyright 中荣恒科技有限公司
 * @function  菜品类数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class CategoryDB {

	private SQLiteDatabase db;

	// 创建表
	public CategoryDB(Context context) {
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE table IF NOT EXISTS cats"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ids TEXT,name TEXT,description TEXT)");
		Log.e("菜品", "菜品表创建成功");
	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void add(List<MenuCategory> list) {
		for (MenuCategory e : list) {
			db.execSQL(
					"insert into cats(ids,name,description) values(?,?,?)",
					new Object[] { e.getCategory_id(), e.getCategory_name(),
							e.getCategory_description() });
		}
	}

	/**********************************************************
	 * Function: getEvi Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 环境信息 Others:
	 * 其它说明
	 *********************************************************/
	public List<MenuCategory> getEvi() {
		List<MenuCategory> list = new ArrayList<MenuCategory>();
		Cursor c = db.rawQuery("SELECT * from cats", null);
		while (c.moveToNext()) {
			String category_id = c.getString(c.getColumnIndex("ids"));
			String category_name = c.getString(c.getColumnIndex("name"));
			String category_description = c.getString(c
					.getColumnIndex("description"));
			MenuCategory evi = new MenuCategory();
			evi.setCategory_id(category_id);
			evi.setCategory_name(category_name);
			evi.setCategory_description(category_description);
			list.add(evi);
		}
		c.close();
		return list;
	}

	/**********************************************************
	 * Function: getId Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 环境信息 Others:
	 * 其它说明
	 *********************************************************/
	public String getId(String name) {
		String category_id = null;
		Cursor c = db.rawQuery("SELECT * from cats where name= '" + name + "'",
				null);
		while (c.moveToNext()) {
			category_id = c.getString(c.getColumnIndex("ids"));
		}
		c.close();
		return category_id;
	}

	/**********************************************************
	 * Function: getCnt Description: 获取条数 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 条数 Others:
	 * 其它说明
	 *********************************************************/
	public int getCount() {
		int cnt = 0;
		Cursor c = db.rawQuery("SELECT count(ids) from cats", null);
		c.moveToFirst();
		cnt = c.getInt(0);
		c.close();
		return cnt;
	}

	/**********************************************************
	 * Function: delete Description: 删除信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void delete() {
		db.execSQL("delete from cats");
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
