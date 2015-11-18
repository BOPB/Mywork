package com.zrh.urestaurantprivate.umenu;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.zrh.urestaurantprivate.adapter.OrderListAdapter;
import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.entitydb.MenuDB;
import com.zrh.urestaurantprivate.entitydb.OrderDetailsDB;
import com.zrh.urestaurantprivate.entitydb.OrderInfoDB;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.thread.DownLoadThread;
import com.zrh.urestaurantprivate.thread.GetDishInfoThread;
import com.zrh.urestaurantprivate.thread.GetDishPictureThread;
import com.zrh.urestaurantprivate.util.AvoidTwoClick;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @copyright 中荣恒科技有限公司
 * @function 订单管理界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class aOrderActivity extends Activity implements OnClickListener {
	private boolean isExit = false;
	private Toast mToast;
	private RelativeLayout rl_ordermanage_prepare;
	private RelativeLayout rl_ordermanage_deal;
	private RelativeLayout rl_ordermanage_exception;
	private RelativeLayout rl_ordermanage_history;
	private RelativeLayout rl_ordermanage_about;
	private RelativeLayout rl_ordermanage_refresh;
	private List<JSONObject> jsonObjectList;// 订单信息的JSONObject集合
	private List<MenuDetails> listMenuDetails;// 获取解析的菜品详情
	private List<OrderInfo> listPrepareInfo;// 预定订单部分
	private List<OrderInfo> listOrderInfo;// 预定订单部分
	private JSONObject jsonObject;
	private String orderString;// 放返回的订单json数据
	private String menuString;// 放返回的菜单json数据
	private OrderInfoDB oInfoDB;
	private OrderDetailsDB oDetailsDb;
	private SharePreferenceUtil sp;
	private Dialog mDialog = null;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x113) {
				closeReflashDialog();
				Toast.makeText(aOrderActivity.this, "请求数据错误，请检查网络",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 0x220) {
				closeReflashDialog();
//				Toast.makeText(aOrderActivity.this, "订单信息已更新",
				Toast.makeText(aOrderActivity.this, "菜品信息已更新",
						Toast.LENGTH_LONG).show();
			}

			else if (msg.what == 0x112) {
				closeReflashDialog();
				Toast.makeText(aOrderActivity.this, "服务器无数据", Toast.LENGTH_LONG)
						.show();
			}

		};
	};
	public MyApplication application = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderactivity);
		sp = new SharePreferenceUtil(this, MyConstants.SAVE_USER);
		rl_ordermanage_prepare = (RelativeLayout) findViewById(R.id.rl_ordermanage_prepare);
		rl_ordermanage_deal = (RelativeLayout) findViewById(R.id.rl_ordermanage_deal);
		rl_ordermanage_exception = (RelativeLayout) findViewById(R.id.rl_ordermanage_exception);
		rl_ordermanage_about = (RelativeLayout) findViewById(R.id.rl_ordermanage_about);
		rl_ordermanage_history = (RelativeLayout) findViewById(R.id.rl_ordermanage_history);
		rl_ordermanage_refresh = (RelativeLayout) findViewById(R.id.rl_ordermanage_refresh);
		rl_ordermanage_refresh.setOnClickListener(this);
		rl_ordermanage_prepare.setOnClickListener(this);
		rl_ordermanage_deal.setOnClickListener(this);
		rl_ordermanage_exception.setOnClickListener(this);
		rl_ordermanage_about.setOnClickListener(this);
		rl_ordermanage_history.setOnClickListener(this);
		application = (MyApplication) getApplication();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (AvoidTwoClick.canClick) {
			AvoidTwoClick.canClick = false;
			AvoidTwoClick.avoidTwoClick();
			switch (arg0.getId()) {
			case R.id.rl_ordermanage_prepare:
				Intent intent = new Intent(aOrderActivity.this,
						OrderDealOne.class);
				//intent.putExtra("value", "OrderSureActivity");
				startActivity(intent);
				break;
			case R.id.rl_ordermanage_deal:
				Intent intent1 = new Intent(aOrderActivity.this,
						OrderDealTwo.class);
				startActivity(intent1);
				break;
			case R.id.rl_ordermanage_exception:
				Intent intent2 = new Intent(aOrderActivity.this,
						OrderDealThree.class);
				startActivity(intent2);
				break;
			case R.id.rl_ordermanage_about:
				Intent intent3 = new Intent(aOrderActivity.this,
						OrderDealFour.class);
				startActivity(intent3);
				break;
			case R.id.rl_ordermanage_history:
				Intent intent4 = new Intent(aOrderActivity.this,
						OrderHistory.class);
				startActivity(intent4);
				break;
			case R.id.rl_ordermanage_refresh:
				if (!detect()) {
					DialogFactory.ToastDialog(aOrderActivity.this, "温 馨 提 示",
							"网络请求失败，请检查网络");
				} else {
					showRequestDialog("正在更新，请稍等...");
//					new GetOrderJson().start();
					new GetDishInfoThread(application).start();
					new getMenuJson().start();
				}
				break;
			default:
				break;
			}

		}

	}

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
			aOrderActivity.this.finish();
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

/*	private class GetOrderJson extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			Map<String, String> map = ServerAPI.getInstance(
					getApplicationContext()).getOrderDeal(sp.getAppID());
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("c", "order");
			// map.put("a", "hqorder");
			// map.put("appid", sp.getAppID());
			// 获取json数据
			orderString = HttpUtils.getJsoneq(map);
			if (orderString.equals("false")) {
				handler.sendEmptyMessage(0x113);
				
			} else {
				jsonObject = JsonTools.parseJson(orderString);
				listOrderInfo = JsonTools.getOrderData(jsonObject);
				//
				if (listOrderInfo != null) {
					// 把数据加入数据库
					oInfoDB = new OrderInfoDB(aOrderActivity.this);
					oInfoDB.delete();
					oInfoDB.addOrderInfo(listOrderInfo);
					oInfoDB.close();
					jsonObjectList = JsonTools.getJsonList(jsonObject);
					//
					oDetailsDb = new OrderDetailsDB(aOrderActivity.this);
					oDetailsDb.delete();
					for (int j = 0; j < jsonObjectList.size(); j++) {
						listMenuDetails = JsonTools.getMenuData(jsonObjectList
								.get(j));
						oDetailsDb.addOrderInfo(listMenuDetails);
						
					}
					oDetailsDb.close();
					handler.sendEmptyMessage(0x220);
				} else {
					handler.sendEmptyMessage(0x112);
				}
				
			}
			
		}
	}
*/	private class getMenuJson extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub

//			Map<String, String> map = ServerAPI.getInstance(
//					getApplicationContext()).getOrderDeal(sp.getAppID());
			Map<String, String> map = ServerAPI.getInstance(getApplicationContext())
					.getMenuMsg(String.valueOf(sp.getMenuChange()));
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("c", "order");
			// map.put("a", "hqorder");
			// map.put("appid", sp.getAppID());
			// 获取json数据
			//orderString = HttpUtils.getJsoneq(map);
			menuString = HttpUtils.getJsoneq(map);
			if (menuString.equals("false")) {
				handler.sendEmptyMessage(0x113);

			} else {
				jsonObject = JsonTools.parseJson(menuString);
				List<Menu> menus = JsonTools.getMenus(jsonObject);
				System.out.println("menus:" + menus);
				if (menus != null && !menus.isEmpty()) {
					MenuDB mdb = new MenuDB(application );
					List<Menu> oldMenuList = mdb.getAllMenu();
					mdb.delete();
					mdb.addMenu(menus);
					mdb.close();
					boolean bIsExists = false;
					// 比对本地数据库的图片MD5值与接口获取到的数据图片MD5值
					for (int i = 0; i < menus.size(); i++) {
						Menu me = menus.get(i);
						bIsExists = false;
						for (int j = 0; j < oldMenuList.size(); j++) {
							Menu meLoc = oldMenuList.get(j);
							if (me.getMenuid() == meLoc.getMenuid()) {
								bIsExists = true;
								if (!me.getMenu_md5().equals(
										meLoc.getMenu_md5())) {
									// MD5值有变化，需要重新下载
									menus.get(i).setChangedStatus(1);
								}
								break;
							}
						}
						if (!bIsExists) {
							// 新的图片，需要下载
							menus.get(i).setChangedStatus(2);
						}
					}
					String[] slist = new String[menus.size()];
					int k = 0;
					for (int i = 0; i < slist.length; i++) {
						int nChangedStatus = menus.get(i)
								.getChangedStatus();
						if (nChangedStatus == 1 || nChangedStatus == 2) {
							slist[k++] = menus.get(i).getPic();
						} else {
							if (!MyConstants.isExistsFile("menu", menus
									.get(i).getPic())) {
								slist[k++] = menus.get(i).getPic();
							}
						}
					}
					System.out.println("k的值为：" + k);
					// 下载图片
					if (k > 0) {
						DownLoadThread dlt = new DownLoadThread(
								application, slist);
						dlt.start();
					}
					slist = null;
					menus = null;
					handler.sendEmptyMessage(0x220);
				} else {
					handler.sendEmptyMessage(0x112);
				}
		}
	
	}
}
	// 旋转加载框
	public void showRequestDialog(String msg) {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, msg);
		mDialog.show();
		mDialog.setCancelable(false);
	}

	// 关闭旋转框
	public void closeReflashDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

}
