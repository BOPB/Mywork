package com.zrh.urestaurantprivate.util;

import java.io.File;

import android.os.Environment;

import com.zrh.urestaurantprivate.httputils.MyConstants;
/**
 * @copyright 中荣恒科技有限公司
 * @function 创建文件夹
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class CreateSdcardDir {

	/**********************************************************
	 * Function: createSDCardDir Description: 创建sd卡的目录 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public static void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();

			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + MyConstants.D_IMG_DIR_MENU;// test
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
			}
			String ps = sdcardDir.getPath() + MyConstants.D_IMG_DIR_ENV;// test
			File path2 = new File(ps);
			if (!path2.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path2.mkdirs();
			}
			String logpath = sdcardDir.getPath() + MyConstants.D_IMG_DIR_LOG;// test
			File log_path = new File(logpath);
			if (!log_path.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				log_path.mkdirs();
			}
		} else {
			return;
		}

	}
}
