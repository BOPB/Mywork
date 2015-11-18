package com.zrh.urestaurantprivate.util;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
/**
 * 
 * Copyright: 2015, 深圳市中荣恒科技有限公司
 * File name: ScreenUtils
 * Description: 获取屏幕信息
 * Author: 李文华
 * Version: v1.1
 * Date: 2015-1-23下午12:01:10 
 * 修改日期:
 * 修改人:
 * 修改内容简述:
 */
public class ScreenUtils {

	/**********************************************************
	Function: getScreenWidth
	Description: 获取屏幕宽度
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}
	
	public static int getScreenWidth2(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	
	public static int getScreenHeight2(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	
	public static int getScreenDPI(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.densityDpi;
	}

	/**********************************************************
	Function: getScreenHeight
	Description: 获取屏幕高度
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}
	
	/**********************************************************
	Function: getScreenDensity
	Description: 获取屏幕密度
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
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