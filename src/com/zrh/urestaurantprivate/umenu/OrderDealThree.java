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
 * @function 异常订单界面
 * @author 吴强
 * @version v1.1
 * @date 2015-01-31
 * @修改人：杨光勤
 * @修改内容：根据封装的RefreshOrderInfoTask和下拉上拉刷新控件加载内容
 */
public class OrderDealThree extends Activity implements OnClickListener {
	
	private static final String ORDER_STATUS = "200";//针对异常订单多状态设立的特征码，本身不是真正的ORDER_STATUS
	private String value = "";
	private PullToRefreshListView refreshListView;
	private TextView tv_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_three);
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
		refreshOrderInfoTask = new com.zrh.urestaurantprivate.thread.RefreshOrderInfoTask(OrderDealThree.this, refreshListView,ORDER_STATUS);
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
	private ListView lv_exception;
	private TextView tv_back;//
	private JSONObject jsonObject;
	private List<JSONObject> jsonObjectList;// 订单信息的JSONObject集合

	private List<MenuDetails> listMenuDetails;// 获取解析的菜品详情
	private String orderString;// 放返回的订单json数据
	private OrderInfoDB oInfoDB;
	private OrderDetailsDB oDetailsDb;
	private TextView textNodata;
	private LinearLayout linear;
	private LinearLayout linearException;
	private List<OrderInfo> listPrepareInfo;// 预定订单部分
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x111) {
				if (listPrepareInfo.size() > 0) {
					linearException.setVisibility(0);
					linear.setVisibility(8);
					initPrepareOrder();
				} else {
					linear.setVisibility(0);
					linearException.setVisibility(8);
				}
				closeReflashDialog();
			} else if (msg.what == 0x112) {
				linear.setVisibility(0);
				closeReflashDialog();
			} else if (msg.what == 0x113) {
				closeReflashDialog();
				Toast.makeText(OrderDealThree.this, "获取数据失败，请检查网络", 1).show();
				linear.setVisibility(0);
				linearException.setVisibility(8);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_three);
		showRequestDialog("正在加载数据，请稍等...");
		lv_exception = (ListView) findViewById(R.id.lv_exception);
		lv_exception.setDivider(null);
		textNodata = (TextView) findViewById(R.id.tv_nodata);
		linear = (LinearLayout) findViewById(R.id.nodata_exception);
		linearException = (LinearLayout) findViewById(R.id.ll_order_exception);
		getInfo();
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
	}

	*//**
	 * 获取异常信息
	 * 
	 * @author Administrator
	 * 
	 *//*
	private void getInfo() {
		oInfoDB = new OrderInfoDB(OrderDealThree.this);
		listPrepareInfo = oInfoDB.getInfoByOrder_status("3");
		oInfoDB.close();
		if (listPrepareInfo != null) {
			Log.e("Test", "listPrepareInfo  is not null");
			handler.sendEmptyMessage(0x111);
		} else {
			handler.sendEmptyMessage(0x112);
		}
	}

	*//**
	 * 初始化异常订单
	 *//*
	private void initPrepareOrder() {
		Log.e("TAGs", "the line  94");

		lv_exception.setAdapter(new OrderListAdapter(listPrepareInfo,
				OrderDealThree.this));
		lv_exception.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(OrderDealThree.this, OrderDetails.class);
		// listMenuDetails = JsonTools.getMenuData(jsonObjectList
		// .get(jsonObjectList.size() - 1 - arg2));
		// intent.getIntExtra("orderInfo", listPrepareInfo);
		oDetailsDb = new OrderDetailsDB(OrderDealThree.this);
		listMenuDetails = oDetailsDb.getInfoByOrderId(listPrepareInfo.get(
				listPrepareInfo.size() - 1 - arg2).getOrderNo());
		intent.putExtra(
				"orderInfo",
				(Serializable) listPrepareInfo.get(listPrepareInfo.size() - 1
						- arg2));
		intent.putExtra("menuDetails", (Serializable) listMenuDetails);
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
