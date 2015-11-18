package com.zrh.urestaurantprivate.umenu;

import android.app.Activity;
import android.os.Bundle;

import java.io.Serializable;
import java.util.*;

import org.json.JSONObject;

import com.zrh.urestaurantprivate.adapter.OrderListAdapter;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.entitydb.OrderDetailsDB;
import com.zrh.urestaurantprivate.entitydb.OrderInfoDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshListView;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase.Mode;
import com.zrh.urestaurantprivate.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.zrh.urestaurantprivate.umenu.TableInfoActivity.MyHandler;
import com.zrh.urestaurantprivate.util.DialogFactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
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
 * @function 历史订单界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * @修改人：杨光勤
 * @修改内容：根据封装的RefreshOrderInfoTask和下拉上拉刷新控件加载内容
 */
public class OrderHistory extends Activity implements OnClickListener {
	
	private static final String ORDER_STATUS = "6";
	private String value = "";
	private PullToRefreshListView refreshListView;
	private TextView tv_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_five);
		refreshListView = (PullToRefreshListView) findViewById(R.id.listview_refresh);
		Intent intent = getIntent();
		value = intent.getStringExtra("value");

		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);

		getInfo();
		
		initRefreshListView();
	}
	
	private com.zrh.urestaurantprivate.thread.RefreshOrderInfoTask refreshOrderInfoTask = null;

	private void initRefreshListView() {
		// TODO Auto-generated method stub
		refreshListView.setMode(Mode.BOTH);
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
	
	private void getInfo() {
		executeRefreshOrderTask();
	}

	private void executeRefreshOrderTask() {
		refreshOrderInfoTask = new com.zrh.urestaurantprivate.thread.RefreshOrderInfoTask(OrderHistory.this, refreshListView,ORDER_STATUS);
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
	
	/*private Dialog mDialog = null;
	private ListView lv_prepare;
	private TextView tv_back;//
	private JSONObject jsonObject;
	private String orderString;// 放返回的订单json数据
	private LinearLayout linear;
	private LinearLayout linearPrepare;
	private List<JSONObject> jsonObjectList;// 订单信息的JSONObject集合
	private OrderInfoDB oInfoDB;
	private OrderDetailsDB oDetailsDb;
	private List<MenuDetails> listMenuDetails;// 获取解析的菜品详情
	private List<OrderInfo> listOrderInfo;// 接受返回的订单信息列表
	private List<OrderInfo> listOrderInfos;
	private int times = 0;// 循环的次数，五次就表示10秒超时
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x111) {
				linearPrepare.setVisibility(0);
				linear.setVisibility(8);
				initPrepareOrder();
				closeReflashDialog();
			} else if (msg.what == 0x112) {
				linear.setVisibility(0);
				linearPrepare.setVisibility(8);
				closeReflashDialog();
			} else if (msg.what == 0x113) {
				closeReflashDialog();
				Toast.makeText(OrderHistory.this, "获取数据失败，请检查网络", 1).show();
				linear.setVisibility(0);
				linearPrepare.setVisibility(8);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_five);
		showRequestDialog("正在加载数据，请稍等...");
		lv_prepare = (ListView) findViewById(R.id.lv_prepare_order);
		lv_prepare.setDivider(null);
		linear = (LinearLayout) findViewById(R.id.nodata_deal);
		linearPrepare = (LinearLayout) findViewById(R.id.ll_prepare);
		// new GetOrderJson().start();
		getInfo();
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
	}

	*//**
	 * 获取订单信息
	 * 
	 * @author Administrator
	 * 
	 *//*
	private void getInfo() {
		oInfoDB = new OrderInfoDB(OrderHistory.this);
		listOrderInfo = oInfoDB.getInfoByOrder_status("2");
		oInfoDB.close();
		if (listOrderInfo != null && listOrderInfo.size() > 0) {
			Log.e("Test", "listOrderInfo  is not null");
			handler.sendEmptyMessage(0x111);
		} else {
			handler.sendEmptyMessage(0x112);
		}
	}

	*//**
	 * 初始化历史订单
	 *//*
	private void initPrepareOrder() {

		lv_prepare.setAdapter(new OrderListAdapter(listOrderInfo,
				OrderHistory.this));
		lv_prepare.setOnItemClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(OrderHistory.this, OrderDetails.class);
		// intent.getIntExtra("orderInfo", listPrepareInfo);
		// 解析选中的元素内的订单详情

		// listMenuDetails = JsonTools.getMenuData(jsonObjectList
		// .get(jsonObjectList.size() - 1 - arg2));
		oDetailsDb = new OrderDetailsDB(OrderHistory.this);
		listMenuDetails = oDetailsDb.getInfoByOrderId(listOrderInfo.get(
				listOrderInfo.size() - 1 - arg2).getOrderNo());

		Log.e("Test", "the onItemClick 1236 ");

		intent.putExtra("menuDetails", (Serializable) listMenuDetails);
		intent.putExtra(
				"orderInfo",
				(Serializable) listOrderInfo.get(listOrderInfo.size() - 1
						- arg2));

		startActivity(intent);
	}

	// 旋转加载框
	public void showRequestDialog(String msg) {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, msg);
		mDialog.show();
		// mDialog.setCancelable(false);
	}

	// 关闭旋转框
	public void closeReflashDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}*/
}
