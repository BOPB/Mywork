package com.zrh.urestaurantprivate.entitydb;



import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;
import com.zrh.urestaurantprivate.httputils.MyConstants;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * @copyright 中荣恒科技有限公司
 * @function  环境图片信息数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class EnviromentDB {

	private SQLiteDatabase db;
	private Context context;

	// 创建表
	public EnviromentDB(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(MyConstants.DBNAME,
				Context.MODE_PRIVATE, null);

		db.execSQL("CREATE table IF NOT EXISTS enviroment"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,hash TEXT, enviromentid TEXT,pic TEXT)");
		// Log.e("enviroment表", "创建成功");

	}

	/**********************************************************
	 * Function: addEvi Description: 插入信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 环境信息 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public void addEvi(List<ResEnvPicInfo> list) {
		for (ResEnvPicInfo e : list) {
			// Log.i("数据库状态",db.isOpen() + "," + db.isReadOnly());
			db.execSQL(
					"insert into enviroment(enviromentid,pic,hash) values(?,?,?)",
					new Object[] { e.getEnviromentid(), e.getPic(), e.getHash() });
			// Log.e("enviroment表", "插入");
		}
	}

	/**********************************************************
	 * Function: getEvi Description: 获取信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * / 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 环境信息 Others:
	 * 其它说明
	 *********************************************************/
	public List<ResEnvPicInfo> getEvi() {
		List<ResEnvPicInfo> list = new ArrayList<ResEnvPicInfo>();
		Log.i("数据库状态", db.isOpen() + "," + db.isReadOnly());
		Cursor c = db.rawQuery("SELECT * from enviroment", null);
		while (c.moveToNext()) {

			String enviromentid = c.getString(c.getColumnIndex("enviromentid"));
			String pic = c.getString(c.getColumnIndex("pic"));
			String hash = c.getString(c.getColumnIndex("hash"));

			ResEnvPicInfo evi = new ResEnvPicInfo();
			evi.setEnviromentid(enviromentid);
			evi.setPic(pic);
			evi.setHash(hash);
			list.add(evi);
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
		Cursor c = db.rawQuery("SELECT enviromentid from enviroment", null);
		cnt = c.getCount();
		c.close();
		Log.e("enviroment表", "获取条数");
		return cnt;
	}

	/**********************************************************
	 * Function: delete Description: 删除信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void delete() {
		db.execSQL("delete from enviroment");
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

	public List<ResEnvPicInfo> getHa() {
		List<ResEnvPicInfo> list = new ArrayList<ResEnvPicInfo>();
		Cursor c = db.rawQuery("SELECT enviromentid, hash from enviroment",
				null);
		while (c.moveToNext()) {
			String hash = c.getString(c.getColumnIndex("hash"));
			String enviromentid = c.getString(c.getColumnIndex("enviromentid"));
			ResEnvPicInfo evi = new ResEnvPicInfo();
			evi.setHash(hash);
			evi.setEnviromentid(enviromentid);
			list.add(evi);
		}
		c.close();
		return list;
	}

}
