package com.zrh.urestaurantprivate.entitydb;

import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.Advertisement;
import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;
import com.zrh.urestaurantprivate.httputils.MyConstants;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @copyright 中荣恒科技有限公司
 * @function  广告图片信息数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class AdvertisementDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public AdvertisementDB(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);

		db.execSQL("CREATE table IF NOT EXISTS advertisement"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,hash TEXT, advertisementid TEXT,pic TEXT)");
		// Log.e("enviroment表", "创建成功");

	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void addEvi(List<Advertisement> list) {
		for (Advertisement a : list) {
			// Log.i("数据库状态",db.isOpen() + "," + db.isReadOnly());
			db.execSQL(
					"insert into advertisement(advertisementid,pic,hash) values(?,?,?)",
					new Object[] { a.getAdvertisementid(), a.getPic(), a.getHash() });
			// Log.e("enviroment表", "插入");
		}
	}

	/**********************************************************
	 * Function: getEvi Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * / 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 环境信息 Others:
	 * 其它说明
	 *********************************************************/
	public List<Advertisement> getEvi() {
		List<Advertisement> list = new ArrayList<Advertisement>();
		Log.i("Test", db.isOpen() + "," + db.isReadOnly());
		Cursor c = db.rawQuery("SELECT * from advertisement", null);
		while (c.moveToNext()) {

			String advertisementid = c.getString(c.getColumnIndex("advertisementid"));
			String pic = c.getString(c.getColumnIndex("pic"));
			String hash = c.getString(c.getColumnIndex("hash"));

			Advertisement adv = new Advertisement();
			adv.setAdvertisementid(advertisementid);
			adv.setPic(pic);
			adv.setHash(hash);
			list.add(adv);
			// Log.e("enviroment表", "餐厅图片查询");
		}
		c.close();
		return list;
	}

	/**********************************************************
	 * Function: getCnt Description: 获取条数 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 条数 Others:
	 * 其它说明
	 *********************************************************/
	public int getCnt() {
		int cnt = 0;
		Cursor c = db.rawQuery("SELECT advertisementid from advertisement", null);
		cnt = c.getCount();
		c.close();
		Log.e("advertisement表", "获取条数");
		return cnt;
	}

	/**********************************************************
	 * Function: delete Description: 删除信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void delete() {
		db.execSQL("delete from advertisement");
		// Log.e("enviroment表", "删除");
		// db.close();
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

	public List<Advertisement> getHa() {
		List<Advertisement> list = new ArrayList<Advertisement>();
		Cursor c = db.rawQuery("SELECT advertisementid, hash from advertisement",
				null);
		while (c.moveToNext()) {
			String hash = c.getString(c.getColumnIndex("hash"));
			String advertisementid = c.getString(c.getColumnIndex("advertisementid"));
			Advertisement adv = new Advertisement();
			adv.setHash(hash);
			adv.setAdvertisementid(advertisementid);
			list.add(adv);
		}
		c.close();
		return list;
	}

}
