package com.zrh.urestaurantprivate.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @copyright 中荣恒科技有限公司
 * @function 工具类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class Utils {

	/**********************************************************
	 * Function: getScreenWidth Description: 获取屏幕宽度 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 无 Others: 其它说明
	 *********************************************************/
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**********************************************************
	 * Function: getScreenHeight Description: 获取屏幕高度 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 无 Others: 其它说明
	 *********************************************************/
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	/**********************************************************
	 * Function: getScreenDensity Description: 获取屏幕密度 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 无 Others: 其它说明
	 *********************************************************/
	public static float getScreenDensity(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			return dm.density;
		} catch (Exception ex) {

		}
		return 1.0f;
	}

}