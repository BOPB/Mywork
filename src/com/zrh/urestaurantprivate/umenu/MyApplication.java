package com.zrh.urestaurantprivate.umenu;

import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.*;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.util.CrashHandler;
import com.zrh.urestaurantprivate.util.CreateSdcardDir;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

import android.app.Application;

/**
 * @copyright 中荣恒科技有限公司
 * @function 当前应用类，每个Android应用开启时都会自动创建一个应用类，我们通常继承这个应用了，
 *  存放一些在整个应用都能用到的数据，因为应用生命周期是最长的，只有当我们退出应用时，应用才会死亡。
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */

public class MyApplication extends Application {

	public float total = 0;// 总价
	public float temp = 0; // 临时变量 总价
	public List<OrderList> orderlist;
	public Order order = new Order();// 订单G
	public int outtime = 0;// 锁屏时间
	public SharePreferenceUtil util;// 共享文件
	public boolean isstop = false;// 下载启止控制
	public String phoneid = null; // 手机唯一码
	public String code = null;
	public int isporblem = 0;

	public String foodtime = null;// 用餐时间

	public String session = null;
	public float paymoney;
	public String zk = "1.00";
	public String titles = null;
	public String infos = null;
	public String haveOrder;
	public String seat_id = null;
	public String tableflag = null;
	public List<Menu> menulist;

	// private static final String TAG = "JPush";

	@Override
	public void onCreate() {
		super.onCreate();
		CreateSdcardDir.createSDCardDir();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		util = new SharePreferenceUtil(this, MyConstants.SAVE_USER);
		orderlist = new ArrayList<OrderList>();// 订单列表
		menulist = new ArrayList<Menu>();
	}

}
