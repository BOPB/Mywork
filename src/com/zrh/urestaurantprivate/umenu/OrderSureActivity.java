package com.zrh.urestaurantprivate.umenu;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrh.urestaurantprivate.adapter.OrdersSureListAdapter;
import com.zrh.urestaurantprivate.bean.Order;
import com.zrh.urestaurantprivate.bean.OrderList;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.umenu.Upagetwo.MyHandler;
import com.zrh.urestaurantprivate.util.AvoidTwoClick;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

/**
 * @copyright 中荣恒科技有限公司
 * @function 确认订单界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class OrderSureActivity extends Activity implements OnClickListener {

	private OrdersSureListAdapter ordersSureListAdapter = null;
	private MyApplication application;
	private ListView order_sure_list;
	private char symbol = 165;// 人民币符号
	private TextView total_money;
	private TextView num;
	private Button order_sure;
	private Order od = null;
	private EditText fuwuyuan_id;
	private EditText phone;
	private Toast mToast;
	private EditText beizhu;
	private LinearLayout mLinear;
	private String odjs = "";
	private Dialog mDialog = null;
	private MyHandler twohandler = null;
	private TextView tv_back;
	private SharePreferenceUtil sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.order_sure);
		sp = new SharePreferenceUtil(this, MyConstants.SAVE_USER);
		twohandler = Upagetwo.getHandler();
		application = (MyApplication) this.getApplicationContext();
		order_sure_list = (ListView) findViewById(R.id.order_sure_list);
		order_sure_list.setDivider(null);
		total_money = (TextView) findViewById(R.id.total_money);
		// num = (TextView) findViewById(R.id.num);
		order_sure = (Button) findViewById(R.id.order_sure);
		fuwuyuan_id = (EditText) findViewById(R.id.fuwuyuan_id);
		phone = (EditText) findViewById(R.id.phone);
		beizhu = (EditText) findViewById(R.id.beizhu);
		mLinear = (LinearLayout) findViewById(R.id.mLinear);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		order_sure.setOnClickListener(this);
		AddDate();
		fuwuyuan_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					order_sure_list.setVisibility(View.GONE);
				} else {
					order_sure_list.setVisibility(View.VISIBLE);
				}
			}
		});
		phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					order_sure_list.setVisibility(View.GONE);
				} else {
					order_sure_list.setVisibility(View.VISIBLE);
				}
			}
		});
		beizhu.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					order_sure_list.setVisibility(View.GONE);
				} else {
					order_sure_list.setVisibility(View.VISIBLE);
				}
			}
		});
		mLinear.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mLinear.setFocusable(true);
				mLinear.setFocusableInTouchMode(true);
				mLinear.requestFocus();
				
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  
		        return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);  

			}
		});

	}

	public void AddDate() {
		ArrayList<HashMap<String, Object>> selMenuItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < application.orderlist.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			OrderList orderItem = application.orderlist.get(i);
			map.put("ItemText", orderItem.getName());
			map.put("MoneyText", String.valueOf(symbol)
					+ new DecimalFormat("#0.00").format(orderItem.getPrice()));
			map.put("NumText", orderItem.getAmount() + " 份");
			selMenuItem.add(map);
		}

		// 生成适配器的Item和动态数组对应的元素
		ordersSureListAdapter = new OrdersSureListAdapter(this, selMenuItem,// 数据源
				R.layout.order_sure_view,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemText", "MoneyText", "NumText", },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.usetv, R.id.ormoney, R.id.ornum });

		// 添加并且显示
		order_sure_list.setAdapter(ordersSureListAdapter);
		ordersSureListAdapter.notifyDataSetChanged();
		if (application.total <= 0) {
			application.total = 0f;
		}
		// num.setText(application.orderlist.size() + "");
		total_money.setText(String.valueOf(symbol)
				+ new DecimalFormat("#0.00").format(application.total));
	}

	@Override
	public void onClick(View v) {
		if (AvoidTwoClick.canClick) {
			AvoidTwoClick.canClick = false;
			AvoidTwoClick.avoidTwoClick();
			if (v.getId() == R.id.tv_back) {
				// 1.得到InputMethodManager对象
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				// 获取状态信息
				boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
				if (isOpen) {
					imm.hideSoftInputFromWindow(fuwuyuan_id.getWindowToken(), 0);
				}
				finish();
			}
			if (v == order_sure) {
				if (fuwuyuan_id.getText().toString().length() == 0) {
					showToast(OrderSureActivity.this, "服务员号不能为空",
							Toast.LENGTH_SHORT);
					return;
				}
				showRequestDialog();
				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String date = sDateFormat.format(new Date());
				od = new Order(application.order.getSeatid(),
						new DecimalFormat("#0.00").format(application.total),
						beizhu.getText().toString(),
						application.order.getTableflag(), fuwuyuan_id.getText()
								.toString(), application.order.getTime(), date,
						phone.getText().toString() + "");

				odjs = JsonTools.createOrder(application.orderlist, od);

				new doUserOrderMenu()
						.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}

		}

	}

	private class doUserOrderMenu extends AsyncTask<Void, Void, String> {
		private JSONObject appidJson;
		private int huiyuan_msg;

		@Override
		protected String doInBackground(Void... arg0) {
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("c", "order");
			// map.put("a", "horder");
			// map.put("appid", sp.getAppID());
			// map.put("json", odjs);

			Map<String, String> map = ServerAPI.getInstance(
					getApplicationContext()).orderSure(sp.getAppID(), odjs);

			String odbc = HttpUtils.getJsoneq(map);
			System.out.println(odbc);
			return odbc;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				closeReflashDialog();
				JSONObject jsonObj = JsonTools.parseJson(result);
				System.out.println("jsonObj:" + jsonObj);
				if (jsonObj == null) {
					System.out.println(result);
					if (result.equals("false")) {
						closeReflashDialog();
						showToast(OrderSureActivity.this, "下定单失败，请检查您的网络",
								Toast.LENGTH_SHORT);
						return;
					} else {
						closeReflashDialog();
						showToast(OrderSureActivity.this, "接口数据错误：" + result,
								Toast.LENGTH_SHORT);
					}
				}
				int code = jsonObj.optInt("code");

				String scode = jsonObj.optString("scode");
				String description = jsonObj.optString("description");

				// huiyuan_msg

				if (sp.getAppID().equals("")) {
					try {
						appidJson = jsonObj.getJSONObject("data");

						Log.e("Test", "huiyuan_msg  is running :" + huiyuan_msg);
						sp.setAppID(appidJson.getString("appid"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
					}
				}

				try {
					JSONObject jo = jsonObj.getJSONObject("data");
					huiyuan_msg = jo.getInt("huiyuan_zk");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					huiyuan_msg = 100;
					e.printStackTrace();
				}

				System.out.println("description = " + description);
				if (code == 0) {
					closeReflashDialog();
					if (huiyuan_msg < 100) {
						showToast(OrderSureActivity.this, "下订单成功," + "今日会员打"
								+ (float) huiyuan_msg / 10 + "折",
								Toast.LENGTH_SHORT);
					} else {
						showToast(OrderSureActivity.this, "下订单成功",
								Toast.LENGTH_SHORT);
					}
				} else {
					closeReflashDialog();
					showToast(OrderSureActivity.this, description,
							Toast.LENGTH_SHORT);
					if (code == 1004 && "该手机号码非本店会员!".equals(description)) {
						phone.setText("");
					}
					return;
				}

				if (!isFinishing()) {
					Intent intent = new Intent();
					intent.putExtra("value", "OrderSureActivity");
					intent.setClass(OrderSureActivity.this, OrderDealOne.class);
					startActivity(intent);
					twohandler.sendEmptyMessage(2015);
					OrderSureActivity.this.finish();
				} else {
					twohandler.sendEmptyMessage(2015);
					OrderSureActivity.this.finish();
				}
			}

		}

	}

	private void getBackData() {

	}

	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}

		mDialog = DialogFactory.creatRequestDialog(this, "正在请求服务器，请稍等...");
		mDialog.setCancelable(false);
		mDialog.show();
	}

	// 关闭转转框
	public void closeReflashDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	// 不让Toast多显示长时间
	private void showToast(Context context, String msg, int showtime) {
		// 实例化一个Toast对象
		if (mToast != null) {
			mToast.setText(msg);
		} else {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		}
		mToast.show();

	}

}
