package com.zrh.urestaurantprivate.umenu;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.thread.*;

/**
 * @copyright 中荣恒科技有限公司
 * @function 开场动画界面，在这里开启一些线程加载数据
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class WelcomeActivity extends Activity {
	private MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		application = (MyApplication) this.getApplicationContext();
		//MyConstants.HTTP = application.util.getValidation();
		if (!checkSDCard()) {
			Toast.makeText(getApplicationContext(), "检测到您的手机SD卡不存在",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (!detect()) {
			Toast.makeText(getApplicationContext(), "检测到你没有联网,请确定网络连接",
					Toast.LENGTH_LONG).show();
		}
		new GetAdvPictureThread(application).start();
		new GetEnvPictureThread(application).start();
		new GetDishPictureThread(application).start();
		new GetDishInfoThread(application).start();
		// 第一次没有appid不请求订单信息
		if (!application.util.getAppID().equals("")) {
			new OrderInfoThread(WelcomeActivity.this).start();
		} else {
			Log.e("Test", "appid is null");
		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						UMainActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		};
		timer.schedule(task, 2000);

	}
	/**
	 * 检测SD卡是否存在
	 */
	private boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 检测网络状态
	 */
	public boolean detect() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	private static Boolean isExit = false;

	/**
	 * 两次返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Log.e("Test", "248 is running");
			Toast.makeText(application, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			// 数据清除
			application.total = 0;
			application.outtime = 0;
			application.orderlist.clear();
			application.isstop = false;
			application.util.setIsonline(false);
			WelcomeActivity.this.finish();
		}
	}

}
