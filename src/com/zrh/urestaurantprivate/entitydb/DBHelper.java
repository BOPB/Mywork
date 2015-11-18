package com.zrh.urestaurantprivate.entitydb;
import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

import com.zrh.urestaurantprivate.httputils.MyConstants;
/**
 * @copyright 中荣恒科技有限公司
 * @function  数据库帮助类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class DBHelper extends SQLiteOpenHelper {
	private static File sdcardDir = Environment.getExternalStorageDirectory();

	public DBHelper(Context context, boolean enableWAL) {
		super(context, sdcardDir.getPath() + MyConstants.DBPATH
				+ MyConstants.DBNAME, null, 1);
		if (enableWAL && Build.VERSION.SDK_INT >= 11) {
			getWritableDatabase().enableWriteAheadLogging();
		}
	}

	// 创建数据库表
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE table IF NOT EXISTS menu"
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT,name TEXT, menu_md5 TEXT,price TEXT, type TEXT, rebate TEXT, state TEXT,pic TEXT,description TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE user ADD COLUMN other TEXT");
	}

	public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File dbp = new File(sdcardDir.getPath() + MyConstants.DBPATH);
			File dbf = new File(sdcardDir.getPath() + MyConstants.DBPATH
					+ MyConstants.DBNAME);
			if (!dbp.exists()) {
				dbp.mkdirs();
			}
			boolean iscreate = false;
			if (!dbf.exists()) {
				try {
					iscreate = dbf.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				iscreate = true;
			}
			if (iscreate) {
				db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
			}
		}
		return db;
	}

}
