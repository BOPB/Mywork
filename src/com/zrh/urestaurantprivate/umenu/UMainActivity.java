package com.zrh.urestaurantprivate.umenu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;
/**
 * @copyright 中荣恒科技有限公司
 * @function 主界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class UMainActivity extends TabActivity implements
		OnCheckedChangeListener {
	private static RadioGroup radioGroup = null;
	public static TabHost mHost;

	private boolean isExit = false;
	private Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.umaininterface);
		radioGroup = (RadioGroup) findViewById(R.id.myradioGroup);
		mHost = this.getTabHost();// 添加tabhost
		mHost.addTab(mHost.newTabSpec("PAGEONE").setIndicator("PAGEONE")
				.setContent(new Intent(this, MyRestaurantActivity.class)));
		mHost.addTab(mHost.newTabSpec("PAGETWO").setIndicator("PAGETWO")
				.setContent(new Intent(this, TableInfoActivity.class)));
		mHost.addTab(mHost.newTabSpec("PAGETHREE").setIndicator("PAGETHREE")
				.setContent(new Intent(this, aOrderActivity.class)));
		radioGroup.setOnCheckedChangeListener(this);
	}

	public static TabHost getHost() {
		return mHost;
	}

	public static RadioGroup getButton() {
		return radioGroup;
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case R.id.tab1:
			mHost.setCurrentTabByTag("PAGEONE");
			break;
		case R.id.tab2:
			mHost.setCurrentTabByTag("PAGETWO");
			break;
		case R.id.tab3:
			mHost.setCurrentTabByTag("PAGETHREE");
			break;

		default:
			break;
		}
	}
/**
 * 点击两次返回键退出
 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// exit();
			exitBy2Click();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	// 点击两次返回键退出
	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			showToast(this, "再按一次退出程序", Toast.LENGTH_SHORT);
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			// 数据清除
			UMainActivity.this.finish();
		}
	}

	// 不让Toast多显示长时间
	private void showToast(Context context, String msg, int showtime) {
		// 实例化一个Toast对象
		mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		if (mToast != null) {
			mToast.setText(msg);
		} else {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

}
