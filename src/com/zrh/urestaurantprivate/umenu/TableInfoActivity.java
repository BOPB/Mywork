package com.zrh.urestaurantprivate.umenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrh.urestaurantprivate.bean.RestaurantTableInfo;
import com.zrh.urestaurantprivate.entitydb.RestaurantTableInfoDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.umenu.Upagetwo.MyHandler;
import com.zrh.urestaurantprivate.util.AvoidTwoClick;
import com.zrh.urestaurantprivate.util.ChoiceDialog;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.view.RefreshableView;
import com.zrh.urestaurantprivate.view.RefreshableView.PullToRefreshListener;

/**
 * @copyright 中荣恒科技有限公司
 * @function 选位订餐界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class TableInfoActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	private boolean isExit = false;
	private Toast mToast;
	private long nowTime = 0;
	// private boolean isThreadOver = false;// 判断线程是否运行结束
	private boolean isUnusedTable = false;// 判断是否有未开台的桌位
	private boolean isHaveApart = false;// 判断是否有拆台的
	private boolean isOrdered = false;// 判断桌位是否已经下单使用中
	private boolean isOperation = false;// 判断桌位是否已开台
	private boolean isMergeTable = false;// 判断桌位是否已并台
	// private LinearLayout ll_tableinfo;
	private Dialog mDialog = null;
	private GridView gv;
	private Button btn_apart, btn_meger, btn_opera;
	private TextView TVrefresh, TVchoose;
	private Map<String, String> map;
	private JSONObject jsonTableInfo;
	private List<RestaurantTableInfo> tableList;// 桌子的信息
	private List<RestaurantTableInfo> CheckedTableList;// 被选中的桌子
	private MyGridAdapter adapter;
	private String mergeResult;
	private String mergeDescription;
	private String apartResult;
	private String apartDescription;
	private String operaResult;
	private String operaDescription;
	private String cancelResult;
	private String cancelDescription;
	private long lastClickTime;
	private ImageView img_click_refresh;
	private int lastClickId;
	private MyApplication application;
	private boolean isApartThreadOver = true;
	private boolean isMergeThreadOver = true;
	private boolean isOperaThreadOver = true;
	private boolean isCancelThreadOver = true;
	private static MyHandler menuHandler = null;

	private StringBuffer sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		application = (MyApplication) this.getApplicationContext();
		// ll_tableinfo = (LinearLayout) findViewById(R.id.ll_tableinfo);
		showRequestDialog("数据正在加载中，请稍等...");
		menuHandler = new MyHandler();
		CheckedTableList = new ArrayList<RestaurantTableInfo>();
		img_click_refresh = (ImageView) findViewById(R.id.img_click_refresh);
		img_click_refresh.setOnClickListener(this);
		gv = (GridView) findViewById(R.id.gv);
		menuHandler.sendEmptyMessage(520);
		new GetTableInfo().start();
		btn_apart = (Button) findViewById(R.id.btn_apart);
		btn_meger = (Button) findViewById(R.id.btn_meger);
		btn_opera = (Button) findViewById(R.id.btn_opera);
		TVrefresh = (TextView) findViewById(R.id.tab3refresh);
		TVchoose = (TextView) findViewById(R.id.tab3choose);
		btn_meger.setOnClickListener(this);
		btn_apart.setOnClickListener(this);
		btn_opera.setOnClickListener(this);
		TVrefresh.setOnClickListener(this);
		TVchoose.setOnClickListener(this);
		RestaurantTableInfoDB tableInfoDB = new RestaurantTableInfoDB(this);
		tableInfoDB.close();
	}

	/**
	 * @版权: 2015, 深圳市中荣恒科技有限公司 zonroh.com
	 * @功能: 使用MyHandler完成内部通信
	 * @作者: 李卓
	 * @版本: v1.2
	 * @日期: 2015-5-19下午5:06:02
	 */

	public class MyHandler extends Handler {

		public void handleMessage(Message msg) {
			if (msg.what == 0x777) {
				img_click_refresh.setVisibility(8);
				initTableViewPager();
			} else if (msg.what == 0x666)

			{
				new GetTableInfo().start();
				if (operaResult.equals("RETURN_CODE_SUCCESS")) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"开台成功");
				} else {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							operaDescription);
				}
			}

			else if (msg.what == 0x555)

			{
				new GetTableInfo().start();
				if (mergeResult.equals("RETURN_CODE_SUCCESS")) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"并台成功");
				} else {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							mergeDescription);
				}

			} else if (msg.what == 0x444)

			{
				new GetTableInfo().start();
				if (apartResult.equals("RETURN_CODE_SUCCESS")) {
					DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
							"拆台成功");

				} else {
					DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
							apartDescription);
				}
			} else if (msg.what == 0x333)

			{
				new GetTableInfo().start();
				if (cancelResult.equals("RETURN_CODE_SUCCESS")) {
					DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
							"取消开台成功");

				} else {
					DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
							cancelDescription);
				}
			} else if (msg.what == 0x100) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"已经拆台，不能重复拆台！");
			} else if (msg.what == 0x101) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"并台就餐中，尚未买单，不能进行拆台操作");
			} else if (msg.what == 0x102) {

				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"单独一张台，无法并台操作");
			} else if (msg.what == 0x103) {

				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"已经并过台的，不允许再并台操作");
			} else if (msg.what == 0x104) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"已经拆过台的，不允许再多张并台操作");
			} else if (msg.what == 0x105) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"已经并过台的，不允许再多张并台操作");
			} else if (msg.what == 0x106) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"已经下过单的，不允许再并台操作");
			} else if (msg.what == 0x107) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"取消开台后，才可拆台");
			} else if (msg.what == 0x110) {
				if (isApartThreadOver) {
					isApartThreadOver = false;
					new GetApartInfo().start();
				} else {
					Toast.makeText(TableInfoActivity.this, "上次操作未完成，请稍候再操作",
							Toast.LENGTH_SHORT).show();
				}
			} else if (msg.what == 0x120) {
				if (isMergeThreadOver) {
					isMergeThreadOver = false;
					new GetMergeInfo().start();
				} else {
					Toast.makeText(TableInfoActivity.this, "上次操作未完成，请稍候再操作",
							Toast.LENGTH_SHORT).show();
				}
				// 开台线程执行
			} else if (msg.what == 0x130) {
				if (isOperaThreadOver) {
					isOperaThreadOver = false;
					new GetOperaInfo().start();
				} else {
					Toast.makeText(TableInfoActivity.this, "上次操作未完成，请稍候再操作",
							Toast.LENGTH_SHORT).show();
				}
			} else if (msg.what == 0x140) {
				if (isCancelThreadOver) {
					isCancelThreadOver = false;
					new cancelOperaInfo().start();
				} else {
					Toast.makeText(TableInfoActivity.this, "上次操作未完成，请稍候再操作",
							Toast.LENGTH_SHORT).show();
				}
			} else if (msg.what == 0x200) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"此桌台不允许拆并台");
			} else if (msg.what == 0x210) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"有不可拆并的桌台");
			} else if (msg.what == 0x520) {
				Log.e("Test", "142 is running");
				closeReflashDialog();
				Toast.makeText(TableInfoActivity.this, "数据加载失败，请检查网络",
						Toast.LENGTH_LONG).show();
				if (tableList == null || tableList.size() == 0) {
					gv.setVisibility(View.GONE);
					img_click_refresh.setVisibility(View.VISIBLE);
				} else {
					img_click_refresh.setVisibility(View.GONE);
					initTableViewPager();
				}
			} else if (msg.what == 0x522) {
				Toast.makeText(TableInfoActivity.this, "数据加载失败，请检查网络",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 0x500) {
				showRequestDialog("数据正在加载中，请稍等...");
			} else if (msg.what == 0x530) {
				closeReflashDialog();
				Toast.makeText(TableInfoActivity.this, "暂无此类桌位，已显示全部桌位",
						Toast.LENGTH_LONG).show();
				new GetTableInfo().start();
			}else if (msg.what == 520) {
				// refreshableview.setStart();
			} else if (msg.what == 123) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"禁台的桌位不能执行任何操作！");
			}
		};
	}

	public static MyHandler getHandler() {
		return menuHandler;
	}

	/**
	 * 初始化viewpager
	 */

	private void initTableViewPager() {
		gv.setVisibility(View.VISIBLE);
		adapter = new MyGridAdapter();
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(this);
		gv.setOnItemLongClickListener(this);
		// ll_tableinfo.setVisibility(0);
		// refreshableview.setVisibility(0);
		closeReflashDialog();
	}

	/**
	 * 获取餐桌信息
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetTableInfo extends Thread {

		@Override
		public void run() {
			RestaurantTableInfoDB tableInfoDB = new RestaurantTableInfoDB(
					TableInfoActivity.this);
			map = ServerAPI.getInstance(getApplicationContext()).tableinfo();
			String string = HttpUtils.getJsoneq(map);
			tableList = tableInfoDB.getTableInfoMsg();
			if (string.equals("false")) {
				menuHandler.sendEmptyMessage(0x520);
			} else {
				jsonTableInfo = JsonTools.parseJson(string);
				tableList = JsonTools.getTableInfo(jsonTableInfo);
				List<RestaurantTableInfo> delList = new ArrayList<RestaurantTableInfo>();
				if (tableList != null && !tableList.isEmpty()) {
					tableInfoDB.delete();
					tableInfoDB.addTableInfo(tableList);
					for (RestaurantTableInfo restaurantTableInfo : tableList) {
						if (restaurantTableInfo.getTableID().equals("1")) {
							delList.add(restaurantTableInfo);
						}
					}
					for (RestaurantTableInfo tableInfo : delList) {
						tableList.remove(tableInfo);
					}
					tableList = tableInfoDB.getTableInfoMsg();
					menuHandler.sendEmptyMessage(0x777);
				} else {
					menuHandler.sendEmptyMessage(0x520);
				}
				deleteData();

			}

			tableInfoDB.close();
		}
	}
	
	/**
	 * 获取空闲桌位信息
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetFreeTableInfo extends Thread {

		@Override
		public void run() {
			RestaurantTableInfoDB tableInfoDB = new RestaurantTableInfoDB(
					TableInfoActivity.this);
			map = ServerAPI.getInstance(getApplicationContext()).tableinfo();
			String string = HttpUtils.getJsoneq(map);
			tableList = tableInfoDB.getTableInfoMsg();
			if (string.equals("false")) {
				menuHandler.sendEmptyMessage(0x520);
			} else {
				jsonTableInfo = JsonTools.parseJson(string);
				tableList = JsonTools.getFreeTableInfo(jsonTableInfo);
				List<RestaurantTableInfo> delList = new ArrayList<RestaurantTableInfo>();
				if (tableList != null && !tableList.isEmpty()) {
					tableInfoDB.delete();
					tableInfoDB.addTableInfo(tableList);
					for (RestaurantTableInfo restaurantTableInfo : tableList) {
						if (restaurantTableInfo.getTableID().equals("1")) {
							delList.add(restaurantTableInfo);
						}
					}
					for (RestaurantTableInfo tableInfo : delList) {
						tableList.remove(tableInfo);
					}
					tableList = tableInfoDB.getTableInfoMsg();
					menuHandler.sendEmptyMessage(0x777);
				} else {
					menuHandler.sendEmptyMessage(0x530);
				}
				deleteData();

			}

			tableInfoDB.close();
		}
	}
	
	/**
	 * 获取开台餐桌信息
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetOpenTableInfo extends Thread {

		@Override
		public void run() {
			RestaurantTableInfoDB tableInfoDB = new RestaurantTableInfoDB(
					TableInfoActivity.this);
			map = ServerAPI.getInstance(getApplicationContext()).tableinfo();
			String string = HttpUtils.getJsoneq(map);
			tableList = tableInfoDB.getTableInfoMsg();
			if (string.equals("false")) {
				menuHandler.sendEmptyMessage(0x520);
			} else {
				jsonTableInfo = JsonTools.parseJson(string);
				tableList = JsonTools.getOpenTableInfo(jsonTableInfo);
				List<RestaurantTableInfo> delList = new ArrayList<RestaurantTableInfo>();
				if (tableList != null && !tableList.isEmpty()) {
					tableInfoDB.delete();
					tableInfoDB.addTableInfo(tableList);
					for (RestaurantTableInfo restaurantTableInfo : tableList) {
						if (restaurantTableInfo.getTableID().equals("1")) {
							delList.add(restaurantTableInfo);
						}
					}
					for (RestaurantTableInfo tableInfo : delList) {
						tableList.remove(tableInfo);
					}
					tableList = tableInfoDB.getTableInfoMsg();
					menuHandler.sendEmptyMessage(0x777);
				} else {
					menuHandler.sendEmptyMessage(0x530);
				}
				deleteData();

			}

			tableInfoDB.close();
		}
	}
	
	/**
	 * 获取已下订餐桌信息
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetOrderedTableInfo extends Thread {

		@Override
		public void run() {
			RestaurantTableInfoDB tableInfoDB = new RestaurantTableInfoDB(
					TableInfoActivity.this);
			map = ServerAPI.getInstance(getApplicationContext()).tableinfo();
			String string = HttpUtils.getJsoneq(map);
			tableList = tableInfoDB.getTableInfoMsg();
			if (string.equals("false")) {
				menuHandler.sendEmptyMessage(0x520);
			} else {
				jsonTableInfo = JsonTools.parseJson(string);
				tableList = JsonTools.getOrderedTableInfo(jsonTableInfo);
				List<RestaurantTableInfo> delList = new ArrayList<RestaurantTableInfo>();
				if (tableList != null && !tableList.isEmpty()) {
					tableInfoDB.delete();
					tableInfoDB.addTableInfo(tableList);
					for (RestaurantTableInfo restaurantTableInfo : tableList) {
						if (restaurantTableInfo.getTableID().equals("1")) {
							delList.add(restaurantTableInfo);
						}
					}
					for (RestaurantTableInfo tableInfo : delList) {
						tableList.remove(tableInfo);
					}
					tableList = tableInfoDB.getTableInfoMsg();
					menuHandler.sendEmptyMessage(0x777);
				} else {
					menuHandler.sendEmptyMessage(0x530);
				}
				deleteData();

			}

			tableInfoDB.close();
		}
	}

	public class MyGridAdapter extends BaseAdapter {

		private ViewHolder holder;

		private class ViewHolder {
			TextView text1, text2, text3, text4, text5, text6, text7;
			ImageView image;
		}

		@Override
		public int getCount() {
			return tableList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 != null) {
				holder = (ViewHolder) arg1.getTag();
			} else {
				arg1 = LayoutInflater.from(TableInfoActivity.this).inflate(
						R.layout.gridviewcontent, null);
				holder = new ViewHolder();
				holder.image = (ImageView) arg1.findViewById(R.id.iv_gvcontent);
				// TextView text = (TextView) view.findViewById(R.id.isChecked);
				holder.text2 = (TextView) arg1.findViewById(R.id.tv_numbel);
				holder.text5 = (TextView) arg1.findViewById(R.id.btn_status);
				holder.text6 = (TextView) arg1.findViewById(R.id.tv_name);
				holder.text4 = (TextView) arg1.findViewById(R.id.tv_status);
				holder.text1 = (TextView) arg1.findViewById(R.id.isChecked);
				holder.text3 = (TextView) arg1.findViewById(R.id.tv_tablecount);
				holder.text7 = (TextView) arg1.findViewById(R.id.tv_isBan);
				arg1.setTag(holder);
			}
			/**
			 * 控制是否被选中
			 */
			if (!tableList.get(arg0).isChecked()) {
				holder.text1.setText("");
			} else {
				holder.text1.setText("选中");
			}
			holder.text6.setText("" + tableList.get(arg0).getTableName());

			if (tableList.get(arg0).getTableIsFree() == 1) {
				holder.text5.setText("可开台");
				holder.image.setImageResource(R.drawable.caiba_4);
			} else if (tableList.get(arg0).getTableIsFree() == 2) {
				holder.text5.setText("可下订");
				holder.image.setImageResource(R.drawable.caiba_4);
			} else {
				holder.text5.setText("加菜");
				holder.image.setImageResource(R.drawable.caiba_5);
			}
			holder.text2.setText("" + tableList.get(arg0).getTableNO());
			holder.text3.setText("" + tableList.get(arg0).getTableSeatCount());
			if (tableList.get(arg0).getTableID().equals("1")) {
				holder.text4.setText("不可拆并");
			} else {
				if (tableList.get(arg0).getTableMergeInfo() == 0) {

					holder.text4.setText("未拆并");
				} else if (tableList.get(arg0).getTableMergeInfo() == -1) {

					holder.text4.setText("已拆台");
				} else {

					holder.text4.setText("已并台"
							+ tableList.get(arg0).getTableMergeInfo());
				}
			}
			if (tableList.get(arg0).getIsBan() == 1) {
				holder.text7.setText("禁");
			} else {
				holder.text7.setText("");
			}
			return arg1;
		}

	}

	@Override
	public void onClick(View arg0) {
		if (AvoidTwoClick.canClick) {
			AvoidTwoClick.canClick = false;
			AvoidTwoClick.avoidTwoClick();
			switch (arg0.getId()) {
			case R.id.btn_apart:
				if (CheckedTableList != null) {
					/**
					 * 先自己判断，只把操作成功的发到服务器验证，减轻服务器的压力
					 */

					if (System.currentTimeMillis() - nowTime > 1000) {
						nowTime = System.currentTimeMillis();
						if (detect()) {
							ApartTable();
						} else {
							DialogFactory.ToastDialog(TableInfoActivity.this,
									"温 馨 提 示", "网络请求失败，请检查网络");
						}
					} else {
						Toast.makeText(TableInfoActivity.this, "亲，不要折腾我",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Log.e("Test", "(CheckedTableList==null)");
				}
				break;
			case R.id.btn_meger:
				if (CheckedTableList != null) {
					/**
					 * 先自己判断，只把操作成功的发到服务器验证，减轻服务器的压力
					 * 
					 */
					if (System.currentTimeMillis() - nowTime > 1000) {
						nowTime = System.currentTimeMillis();
						if (detect()) {

							MergeTable();
						} else {
							DialogFactory.ToastDialog(TableInfoActivity.this,
									"温 馨 提 示", "网络请求失败，请检查网络");
						}

					} else {
						Toast.makeText(TableInfoActivity.this, "请不要点击过快",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Log.e("Test", "(CheckedTableList==null)");
				}

				break;
			/**
			 * 新增开台相关判断 修改人：李卓 修改时间：2015-6-17 11:02:19
			 */
			case R.id.btn_opera:
				if (CheckedTableList != null) {
					/**
					 * 先自己判断，只把操作成功的发到服务器验证，减轻服务器的压力
					 * 
					 */
					if (System.currentTimeMillis() - nowTime > 1000) {
						nowTime = System.currentTimeMillis();
						if (detect()) {

							OperaTable();
						} else {
							DialogFactory.ToastDialog(TableInfoActivity.this,
									"温 馨 提 示", "网络请求失败，请检查网络");
						}

					} else {
						Toast.makeText(TableInfoActivity.this, "请不要点击过快",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Log.e("Test", "(CheckedTableList==null)");
				}
				break;

			case R.id.img_click_refresh:
				showRequestDialog("数据正在加载中，请稍等...");
				new GetTableInfo().start();
				break;
			/**
			 * 增加刷新按键
			 * 
			 */
			case R.id.tab3refresh:
				showRequestDialog("数据正在加载中，请稍等...");
				new GetTableInfo().start();
				break;
			/**
			 * 增加筛选功能
			 * 
			 */
			case R.id.tab3choose:
				String[] items = { "空闲中", "已开台", "已下单", "全部" };
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("桌位筛选").setItems(items,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,int which) {
								switch (which) {
								case 0:
									showRequestDialog("数据正在加载中，请稍等...");
									new GetFreeTableInfo().start();
									break;
								case 1:
									showRequestDialog("数据正在加载中，请稍等...");
									new GetOpenTableInfo().start();
									break;
								case 2:
									showRequestDialog("数据正在加载中，请稍等...");
									new GetOrderedTableInfo().start();
									break;
								case 3:
									showRequestDialog("数据正在加载中，请稍等...");
									new GetTableInfo().start();
									break;
								default:
									break;
								}
							}
						}).show();
				break;
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.e("Test", "onItemClick is running");
		if (tableList.get(arg2).getIsBan() == 1) {
			menuHandler.sendEmptyMessage(123);
		} else {
			/**
			 * 判断是否为双击
			 */
			if ((Integer) ((GridView) arg0).getAdapter().getItem(arg2) == lastClickId
					&& (Math.abs(lastClickTime - System.currentTimeMillis()) < 1000)) {
				lastClickTime = 0;
				lastClickId = 0;
				System.out.println("双击了。。。运行到这里了");
				if (tableList.get(arg2).getTableIsFree() == 1) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"请先开台！");
					return;
				}
				// 传拆并台的a或b桌，桌位号
				Intent intent = new Intent();
				application.tableflag = null;
				application.seat_id = null;

				application.order.setSeatid(tableList.get(arg2).getTableID()
						+ "");
				// 判断是否是拆台
				if (tableList.get(arg2).getTableMergeInfo() == -1) {
					// 弹出a、b桌选择

					ChoiceDialog cd = new ChoiceDialog(TableInfoActivity.this,
							R.style.MyDialog);
					cd.show();
				} else {
					intent.setClass(TableInfoActivity.this, Upagetwo.class);
					startActivity(intent);
				}

			} else {
				lastClickId = (Integer) ((GridView) arg0).getAdapter().getItem(
						arg2);
				lastClickTime = System.currentTimeMillis();
				/**
				 * 单击处理
				 */
				if (!tableList.get(arg2).isChecked()) {

					tableList.get(arg2).setChecked(true);
					CheckedTableList.add(tableList.get(arg2));
					adapter.notifyDataSetChanged();
				} else if (tableList.get(arg2).isChecked()) {

					CheckedTableList.remove(tableList.get(arg2));
					tableList.get(arg2).setChecked(false);
					adapter.notifyDataSetChanged();
				}
			}
		}

	}

	// 长按执行取消开台
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (tableList.get(arg2).getIsBan() == 1) {
			menuHandler.sendEmptyMessage(123);
		} else {
			cancelTable();
		}
		return true;
	}

	/**
	 * 获取并台操作信息
	 */
	private class GetMergeInfo extends Thread {

		@Override
		public void run() {
			Map<String, String> mergeInfo = ServerAPI.getInstance(application)
					.GetMergeInfoAPI();

			idChangeToString();
			mergeInfo.put("tids", sb.toString());

			// 发送map信息，获取数据
			String stirngInfo = HttpUtils.getJsoneq(mergeInfo);
			if (stirngInfo.equals("false")) {
				menuHandler.sendEmptyMessage(0x522);
			} else {
				// 解析数据
				JSONObject mergeObject = JsonTools.parseJson(stirngInfo);
				// 获取返回的結果
				mergeResult = JsonTools.getMergeResult(mergeObject);
				mergeDescription = JsonTools.getMergeDescription(mergeObject);
				Log.e("Test", "返回结果是：  " + mergeResult);

				menuHandler.sendEmptyMessage(0x555);

			}
			isMergeThreadOver = true;
		}
	}

	/**
	 * 获取开台操作信息
	 */
	private class GetOperaInfo extends Thread {

		@Override
		public void run() {
			Map<String, String> operaInfo = ServerAPI.getInstance(application)
					.getOperaInfoAPI();
			idChangeToString();
			operaInfo.put("tids", sb.toString());

			// 发送map信息，获取数据
			String stirngInfo = HttpUtils.getJsoneq(operaInfo);
			if (stirngInfo.equals("false")) {
				menuHandler.sendEmptyMessage(0x522);
			} else {
				// 解析数据
				JSONObject operaObject = JsonTools.parseJson(stirngInfo);
				// 获取返回的結果
				operaResult = JsonTools.getOperaResult(operaObject);
				operaDescription = JsonTools.getOperaDescription(operaObject);
				Log.e("Test", "返回结果是：  " + cancelResult);

				menuHandler.sendEmptyMessage(0x666);

			}
			isOperaThreadOver = true;
		}

	}

	/**
	 * 取消开台操作信息
	 */
	private class cancelOperaInfo extends Thread {

		@Override
		public void run() {
			Map<String, String> cancelInfo = ServerAPI.getInstance(application)
					.cancelTableAPI();
			idChangeToString();
			cancelInfo.put("tids", sb.toString());

			// 发送map信息，获取数据
			String stirngInfo = HttpUtils.getJsoneq(cancelInfo);
			if (stirngInfo.equals("false")) {
				menuHandler.sendEmptyMessage(0x522);
			} else {
				// 解析数据
				JSONObject operaObject = JsonTools.parseJson(stirngInfo);
				// 获取返回的結果
				cancelResult = JsonTools.getOperaResult(operaObject);
				cancelDescription = JsonTools.getOperaDescription(operaObject);
				Log.e("Test", "返回结果是：  " + operaResult);

				menuHandler.sendEmptyMessage(0x333);

			}
			isCancelThreadOver = true;
		}

	}

	/**
	 * 拆台操作接口
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetApartInfo extends Thread {

		@Override
		public void run() {
			Map<String, String> apartInfo = ServerAPI.getInstance(
					getApplicationContext()).apartInfoAPI();
			// 调用一个方法，把选中的id转成一个字符串

			idChangeToString();
			apartInfo.put("tid", sb.toString());

			String stirngInfo = HttpUtils.getJsoneq(apartInfo);
			if (stirngInfo.equals("false")) {
				menuHandler.sendEmptyMessage(0x522);
			}
			// 解析数据
			else {
				JSONObject mergeObject = JsonTools.parseJson(stirngInfo);
				apartResult = JsonTools.getMergeResult(mergeObject);
				apartDescription = JsonTools.getMergeDescription(mergeObject);
				Log.e("Test", "返回结果是：  " + apartResult);
				CheckedTableList = null;
				CheckedTableList = new ArrayList<RestaurantTableInfo>();
				menuHandler.sendEmptyMessage(0x444);
			}
			isApartThreadOver = true;
		}
	}

	/**
	 * 把id转换成字符串
	 */
	private void idChangeToString() {
		sb = new StringBuffer();
		for (int i = 0; i < CheckedTableList.size(); i++) {
			if (i == CheckedTableList.size() - 1) {
				sb.append(CheckedTableList.get(i).getTableID());
			} else {
				sb.append(CheckedTableList.get(i).getTableID() + ",");
			}
		}
	}

	/**
	 * 清空数据
	 */
	public void deleteData() {
		CheckedTableList = null;
		CheckedTableList = new ArrayList<RestaurantTableInfo>();
		// tableList = null;
	}

	/**
	 * 拆台逻辑
	 */
	public void ApartTable() {
		// 单张拆台
		if (CheckedTableList.size() == 1) {
			// 未开台
			if (CheckedTableList.get(0).getTableIsFree() == 1) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"未开台桌位，不可拆台！");
				// 已开台or已下单
			} else {
				// 已拆过台
				if (CheckedTableList.get(0).getTableMergeInfo() == -1) {
					DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
							"已经拆台，不能重复拆台！");
					// 未拆台
				} else {
					// 已并台已下单
					if (CheckedTableList.get(0).getTableMergeInfo() > 0
							&& CheckedTableList.get(0).getTableIsFree() == 0) {
						DialogFactory.ToastDialog(TableInfoActivity.this,
								"提 示", "并台就餐中，尚未买单，不能进行拆台操作！");
						// 已开台/已下单/已并台未下单
					} else {
						System.out.println("允许拆台");
						menuHandler.sendEmptyMessage(0x110);
					}
				}
			}
		} else {
			// 多张拆台
			if (CheckedTableList.size() > 1) {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"拆台只能选择一个桌位！");
				// 未选中桌位
			} else {
				DialogFactory.ToastDialog(TableInfoActivity.this, "提 示",
						"拆台必须选择一个桌位！");
			}

		}
	}

	/**
	 * 开台逻辑
	 */
	public void OperaTable() {
		// 单张开台
		if (CheckedTableList.size() == 1) {
			for (RestaurantTableInfo tableInfo : CheckedTableList) {
				if (tableInfo.getTableIsFree() == 0) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已下单桌位，不可开台！");
					return;
				}
				if (tableInfo.getTableIsFree() == 2) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已开台桌位，不可再次开台！");
					return;
				}
			}
			if (CheckedTableList.get(0).getTableIsFree() == 1) {
				System.out.println("允许开台");
				menuHandler.sendEmptyMessage(0x130);
			}
		}
		// 多张开台
		else if (CheckedTableList.size() > 1) {
			for (int i = 0; i < CheckedTableList.size(); i++) {
				if (CheckedTableList.get(i).getTableIsFree() == 0) {
					isOrdered = true;
					break;
				}
				if (CheckedTableList.get(i).getTableIsFree() == 2) {
					isOperation = true;
					break;
				}
			}
			if (isOrdered) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含已下单桌位，不可开台！");
				isOrdered = false;
			} else if (isOperation) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含已开台桌位，不可再次开台！");
				isOperation = false;
			} else {
				System.out.println("允许开台");
				menuHandler.sendEmptyMessage(0x130);
				/**
				 * 第一种状况：选的桌子中有下单使用中状态的： 提示：已经下单的，不允许再开台操作
				 * 第二种：没有下单状态但是有已开台的，提示：已经开过台的，不允许再开台操作
				 */
			}
		} else {
			DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
					"至少选择一个桌位！");
		}
	}

	/**
	 * 并台逻辑
	 */
	public void MergeTable() {
		// 单张并台
		if (CheckedTableList.size() == 1) {
			// 未开台
			if (CheckedTableList.get(0).getTableIsFree() == 1) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"未开台桌位，不可并台！");
			} else {
				// 未拆台
				if (CheckedTableList.get(0).getTableMergeInfo() == 0) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"单独一张桌位，无法并台操作！");
					// 已拆台已下单
				} else if (CheckedTableList.get(0).getTableIsFree() == 0
						&& CheckedTableList.get(0).getTableMergeInfo() == -1) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已下单桌位，不允许再并台操作！");
					// 已经并过台
				} else if (CheckedTableList.get(0).getTableMergeInfo() > 0) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已并台桌位，不允许再并台操作！");
					// 已拆台且无单
				} else if (CheckedTableList.get(0).getTableMergeInfo() == -1
						&& CheckedTableList.get(0).getTableIsFree() == 2) {
					System.out.println("允许并台");
					menuHandler.sendEmptyMessage(0x120);
				}
			}
			// 多张并台
		} else if (CheckedTableList.size() > 1) {
			// 遍历已选桌位
			for (int i = 0; i < CheckedTableList.size(); i++) {
				// 判断是否有未开台的
				if (CheckedTableList.get(i).getTableIsFree() == 1) {
					isUnusedTable = true;
					break;
				}
				// 判断是否有已下单的
				if (CheckedTableList.get(i).getTableIsFree() == 0) {
					isOrdered = true;
					break;
				}
				// 判断是否有已拆台的
				if (CheckedTableList.get(i).getTableMergeInfo() == -1) {
					isHaveApart = true;
					break;
				}
				// 判断是否有已并台的
				if (CheckedTableList.get(i).getTableMergeInfo() > 0) {
					isMergeTable = true;
					break;
				}
			}
			if (isUnusedTable) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含未开台桌位，不可并台！");
				isUnusedTable = false;
			} else if (isOrdered) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含已下单桌位，不允许再并台操作！");
				isOrdered = false;
			} else if (isHaveApart) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含已拆台桌位，不允许再并台操作！");
				isHaveApart = false;
			} else if (isMergeTable) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"包含已并台桌位，不允许再并台操作！");
				isMergeTable = false;
			} else {
				System.out.println("允许并台");
				menuHandler.sendEmptyMessage(0x120);
			}
			// 未选中
		} else {
			DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
					"至少选择一个桌位！");
		}
	}

	/**
	 * 取消开台逻辑
	 */
	public void cancelTable() {
		// 单张取消
		if (CheckedTableList.size() == 1) {
			// 空闲桌位
			if (CheckedTableList.get(0).getTableIsFree() == 1) {
				DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
						"未开台桌位，不需要取消开台");
			} else {
				// 已拆台
				if (CheckedTableList.get(0).getTableMergeInfo() == -1) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已拆台桌位，无法取消开台");
					// 已并台
				} else if (CheckedTableList.get(0).getTableMergeInfo() > 0) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已并台桌位，无法取消开台");
					// 已下单
				} else if (CheckedTableList.get(0).getTableIsFree() == 0) {
					DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
							"已下单桌位，无法取消开台");
					// 允许取消
				} else {
					System.out.println("允许取消");
					menuHandler.sendEmptyMessage(0x140);
				}
			}
			// 多张取消
		} else if (CheckedTableList.size() > 1) {
			DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
					"取消开台只能选择一张桌位！");
			// 未选中
		} else {
			DialogFactory.ToastDialog(TableInfoActivity.this, " 提 示",
					"取消开台需要选择一张桌位！");
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

	/**
	 * 监听系统返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// exit();
			exitByTwoClick();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 点击两次返回键退出
	private void exitByTwoClick() {
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
			TableInfoActivity.this.finish();
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

}
