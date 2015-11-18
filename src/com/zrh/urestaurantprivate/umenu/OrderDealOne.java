package com.zrh.urestaurantprivate.umenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.zrh.urestaurantprivate.adapter.OrderListAdapter;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.entitydb.OrderDetailsDB;
import com.zrh.urestaurantprivate.entitydb.OrderInfoDB;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshListView;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase.Mode;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.zrh.urestaurantprivate.umenu.TableInfoActivity.MyHandler;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;
import com.zrh.urestaurantprivate.view.RefreshableView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @copyright 中荣恒科技有限公司
 * @function 预定订单界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * @修改人：杨光勤
 * @修改内容：根据封装的RefreshOrderInfoTask和下拉上拉刷新控件加载内容
 */
public class OrderDealOne extends Activity implements OnClickListener {
	private static final String ORDER_STATUS = "2";
	private String value = "";
	private PullToRefreshListView refreshListView;
	private TextView tv_back;//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_one);

		refreshListView = (PullToRefreshListView) findViewById(R.id.listview_refresh);
		Intent intent = getIntent();
		value = intent.getStringExtra("value");
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		getInfo();
		initRefreshListView();
		
	}
	
	@SuppressWarnings("deprecation")
	private com.zrh.urestaurantprivate.thread.RefreshOrderInfoTask refreshOrderInfoTask = null;

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	private void initRefreshListView() {
		
		refreshListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
		refreshListView.setOnRefreshListener(new OnRefreshListener2(){

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				executeRefreshOrderTask();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				executeRefreshOrderTask();
			}
		});

		refreshListView.setRefreshing();
	}

	/**
	 * 获取订单信息
	 * 
	 * @author Administrator
	 * 
	 */
	private void getInfo() {

		executeRefreshOrderTask();

	}

	@SuppressWarnings("deprecation")
	private void executeRefreshOrderTask() {
		refreshOrderInfoTask = new com.zrh.urestaurantprivate.thread.RefreshOrderInfoTask(OrderDealOne.this, refreshListView,ORDER_STATUS);
		refreshOrderInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (refreshOrderInfoTask != null
						&& refreshOrderInfoTask.getStatus() == AsyncTask.Status.RUNNING) {
					refreshOrderInfoTask.cancel(true);// 如果Task还在运行，则先取消它
					refreshOrderInfoTask = null;
				}
			}
		};
		timer.schedule(task, 5000);
	}
	


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		System.out.println("value:" + value);
		if ("OrderSureActivity".equals(value)) {
			finish();
			Upagetwo.instance.finish();
			UMainActivity.getHost().setCurrentTabByTag("PAGETWO");
			UMainActivity.getButton().check(R.id.tab2);
			MyHandler menuHandler = TableInfoActivity.getHandler();
			menuHandler.sendEmptyMessage(520);
		}
		if (value == null || "".equals(value)) {
			finish();
		}
	}
	
	/**
	 * 对返回键进行捕获处理
	 * 修改人：李卓
	 * 修改时间：2015-8-25 14:25:05
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ("OrderSureActivity".equals(value)) {
				finish();
				Upagetwo.instance.finish();
				UMainActivity.getHost().setCurrentTabByTag("PAGETWO");
				UMainActivity.getButton().check(R.id.tab2);
				MyHandler menuHandler = TableInfoActivity.getHandler();
				menuHandler.sendEmptyMessage(520);
			}
			if (value == null || "".equals(value)) {
				finish();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}


}
