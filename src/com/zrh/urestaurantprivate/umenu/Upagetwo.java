package com.zrh.urestaurantprivate.umenu;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zrh.urestaurantprivate.adapter.OrdersListViewAdapter;
import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.bean.MenuCategory;
import com.zrh.urestaurantprivate.bean.OrderList;
import com.zrh.urestaurantprivate.entitydb.CategoryDB;
import com.zrh.urestaurantprivate.entitydb.MenuDB;
import com.zrh.urestaurantprivate.entitydb.RestaurantInfoDB;
import com.zrh.urestaurantprivate.thread.OrderInfoThread;
import com.zrh.urestaurantprivate.util.FixedSpeedScroller;
import com.zrh.urestaurantprivate.view.MenuCategoryTextView;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @copyright 中荣恒科技有限公司
 * @function 点菜界面及功能
 * @author 吴强
 * @version v1.1
 * @date 2015-02-02
 * 
 */
public class Upagetwo extends FragmentActivity implements OnClickListener {
	private ListView orderListView = null;
	private Button dcbtn = null;
	private Button tv_back = null;
	private TextView totaltv = null, myordertv = null;
	private TextView myEmpty = null;
	private static long lastClickTime;
	private MyApplication application;
	private List<MenuCategory> menuCatName;

	private String catId;
	private List<Menu> menulist = null;
	private List<String> menuid;
	private int o = 1;
	private int z = 0;
	private int zz = 0;
	
	public static String tv1, tv2, tv3;
	private static MyHandler myhandler = null;
	private OrdersListViewAdapter slistItemAdapter = null;
	private List<String> menuCatnamelist; // 菜品类名
	private Toast mToast;
	private String menucat;
	public static int select_item = 0;// 被选中时字体变大
	private OrderList orderList;
	private char symbol = 165;
	public static Upagetwo instance = null;
	private HorizontalScrollView mHorizontalScrollView;
	private LinearLayout mLinearLayout;
	private ViewPager pager;
	private int mScreenWidth;
	private int mPreSelectItem;
	// private ImageView mImageView;

	private ArrayList<Fragment> fragments;

	private MyFragmentPagerAdapter fragmentPagerAdapter;
	private BaseFragment fragment;
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		application = (MyApplication) this.getApplicationContext();
		instance = this;
		myhandler = new MyHandler();

		menuid = new ArrayList<String>();
		menuCatnamelist = new ArrayList<String>();
		menulist = new ArrayList<Menu>();
		myEmpty = (TextView) findViewById(R.id.no_data);

		orderListView = (ListView) findViewById(R.id.order_list);
		totaltv = (TextView) findViewById(R.id.total);// 总价
		myordertv = (TextView) findViewById(R.id.myordertv);// 我的订单
		dcbtn = (Button) findViewById(R.id.jkdc);// 即刻订餐
		tv_back = (Button) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myhandler.sendEmptyMessage(2015);
				finish();
			}
		});
		/**
		 * 即可点餐按钮监听及处理
		 */
		dcbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFastDoubleClick()) {
					return;
				} else {
					orderDinner();
				}
			}
		});
		totaltv.setText(new DecimalFormat("#0.00").format(application.total));// 总价
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
		mLinearLayout = (LinearLayout) findViewById(R.id.hsv_content);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(10);
		fragments = new ArrayList<Fragment>();
		textSizeMin = getResources().getDimension(R.dimen.layout_x_27);
		textSizeMax = getResources().getDimension(R.dimen.layout_x_30);
		initFoodData();
	}

	/**
	 * 美食订餐数据获取
	 */
	private void initFoodData() {
		showView();
		select_item = 0;
		menuCatName = new ArrayList<MenuCategory>();
		CategoryDB catdb = new CategoryDB(Upagetwo.this);
		MenuDB mdb = new MenuDB(Upagetwo.this);
		menuCatName = catdb.getEvi();
		ViewGroup p = (ViewGroup) mLinearLayout;
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		if (menuCatName != null && !menuCatName.isEmpty()
				&& menuCatName.size() > 0) {
			for (MenuCategory menu : menuCatName) {
				menuCatnamelist.add(menu.getCategory_name());
			}
			getRebateLimit();
			String menucategory = menuCatnamelist.get(select_item);
			String cat_Id = catdb.getId(menucategory);
			menulist = mdb.getMenu(cat_Id);
			switch (menuCatnamelist.size()) {
			case 1:
				addViewPagerViewforOne(menuCatnamelist);
				break;
			case 2:
				addViewPagerViewforTwo(menuCatnamelist);
				break;
			case 3:
				addViewPagerViewforThree(menuCatnamelist);
				break;
			default:
				addViewPagerView(menuCatnamelist);
				break;
			}
			for (int i = 0; i < menuCatnamelist.size(); i++) {
				menucat = menuCatnamelist.get(i);
				catId = catdb.getId(menucat);
				System.out.println("美食订餐catId=" + catId);
				Bundle data = new Bundle();
				data.putString("catId", catId);
				fragment = new BaseFragment();
				fragment.setArguments(data);
				fragments.add(fragment);
			}
			fragmentPagerAdapter = new MyFragmentPagerAdapter(
					getSupportFragmentManager(), fragments);
			pager.setAdapter(fragmentPagerAdapter);
			// fragmentPagerAdapter.setFragments(fragments);
			pager.setOnPageChangeListener(new MyOnPageChangeListener());
			pager.setCurrentItem(0);
			// setViewPagerScrollSpeed();
			catdb.close();
			mdb.close();
		} else {
			showEmptyInfo();
		}
	}

	private void addViewPagerView(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String label = list.get(i);
			//MenuCategoryTextView tv = new MenuCategoryTextView(this);
			MenuCategoryTextView tv = (MenuCategoryTextView) View.inflate(this, R.layout.textview_hor, null).findViewById(R.id.tv_hor);
			//textSize = tv.getTextSize();
			tv.setText(label);
			tv.setOnClickListener(this);
			TextPaint paint = tv.getPaint();
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMin);
			if (i == 0) {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				//tv.setTextSize(20);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMax);
				paint.setFakeBoldText(false);
			} else {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				paint.setFakeBoldText(false);
			}
			mLinearLayout.addView(tv);
		}
	}
	private void addViewPagerViewforOne(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String label = list.get(i);
			//MenuCategoryTextView tv = new MenuCategoryTextView(this);
			MenuCategoryTextView tv = (MenuCategoryTextView) View.inflate(this, R.layout.textview_hor, null).findViewById(R.id.tv_hor);
			//textSize = tv.getTextSize();
			tv.setText(label);
			tv.setOnClickListener(this);
			tv.setPadding(
					(int)(getResources().getDimension(R.dimen.layout_x_190)), 0,
					(int)(getResources().getDimension(R.dimen.layout_x_190)), 0);
			TextPaint paint = tv.getPaint();
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMin);
			if (i == 0) {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				//tv.setTextSize(20);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMax);
				paint.setFakeBoldText(false);
			} else {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				paint.setFakeBoldText(false);
			}
			mLinearLayout.addView(tv);
		}
	}
	private void addViewPagerViewforTwo(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String label = list.get(i);
			//MenuCategoryTextView tv = new MenuCategoryTextView(this);
			MenuCategoryTextView tv = (MenuCategoryTextView) View.inflate(this, R.layout.textview_hor, null).findViewById(R.id.tv_hor);
			//textSize = tv.getTextSize();
			tv.setText(label);
			tv.setOnClickListener(this);
			tv.setPadding(
					(int)(getResources().getDimension(R.dimen.layout_x_70)), 0,
					(int)(getResources().getDimension(R.dimen.layout_x_70)), 0);
			TextPaint paint = tv.getPaint();
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMin);
			if (i == 0) {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				//tv.setTextSize(20);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMax);
				paint.setFakeBoldText(false);
			} else {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				paint.setFakeBoldText(false);
			}
			mLinearLayout.addView(tv);
		}
	}
	private void addViewPagerViewforThree(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String label = list.get(i);
			//MenuCategoryTextView tv = new MenuCategoryTextView(this);
			MenuCategoryTextView tv = (MenuCategoryTextView) View.inflate(this, R.layout.textview_hor, null).findViewById(R.id.tv_hor);
			//textSize = tv.getTextSize();
			tv.setText(label);
			tv.setOnClickListener(this);
			tv.setPadding(
					(int)(getResources().getDimension(R.dimen.layout_x_40)), 0,
					(int)(getResources().getDimension(R.dimen.layout_x_40)), 0);
			TextPaint paint = tv.getPaint();
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMin);
			if (i == 0) {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				//tv.setTextSize(20);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMax);
				paint.setFakeBoldText(false);
			} else {
				tv.setTextColor(Color.WHITE);
				tv.setIsHorizontaline(false);
				paint.setFakeBoldText(false);
			}
			mLinearLayout.addView(tv);
		}
	}

	/**
	 * 设置ViewPager的滑动速度
	 */
	private void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					pager.getContext());
			mScroller.set(pager, scroller);
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}

	public class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what < 1000) {
				if (msg.arg1 == 1) {
					
					orderMenu(msg);
					
				}
				if (msg.arg1 == 2) {
					// 查看详情
					showDetailMsg(msg);
				}
			} else if (msg.what < 2000) {
				// 删除下单的
				deleteOrderMenu(msg);
			} else if (msg.what == 2015) {
				clearData();// 清除数据
				// closeDB();// 关闭数据库
			} else if(msg.what == 2006){
				if(thread == null){
					thread = new OrderInfoThread(Upagetwo.this);
				}else {
					try {
						thread.interrupt();
						thread.join();
						thread = new OrderInfoThread(Upagetwo.this);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				try{
					clearData();
					thread.start();
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Upagetwo 更新菜单出现异常。");
				}
			}
		}
	}
	
	private OrderInfoThread thread = null;

	// get方法
	public static MyHandler getHandler() {
		return myhandler;
	}

	// 即刻点餐按钮点击
	@Override
	public void onClick(View v) {
		// 点击水平滚动条的点击事件

		for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
			MenuCategoryTextView child = (MenuCategoryTextView) mLinearLayout
					.getChildAt(i);
			if (child == v) {
				pager.setCurrentItem(i);
				
			}
			if (v.getId() == R.id.no_data) {
				clearData();
				initFoodData();
			}
		}
	}

	private int nDiscountLimit = 0;
	private RestaurantInfoDB rdb = null;// 餐厅信息DB

	/**
	 * 获取折扣菜限制个数
	 */
	private void getRebateLimit() {
		rdb = new RestaurantInfoDB(this);
		if (rdb.getInfo() != null && !rdb.getInfo().equals("")) {
			nDiscountLimit = rdb.getInfo().getDiscount_num_perorder_limit();
		}
		rdb.close();
	}

	// 点菜方法
	private void orderMenu(Message msg) {

		int x = msg.what % 10;
		int y = msg.what / 10;
		int i;
		i = 2 * y + x - 1;
		float price;// 价格
		int lindex = 0;
		int ordSize = application.orderlist.size();

		Menu menu = null;

		try {
			menu = menulist.get(i);
		} catch (Exception e) {
			e.printStackTrace();
			menu = null;
		}

		System.out.println("menulist.get(i):" + menu);
		if (menulist != null && !menulist.isEmpty()) {
			if (menu != null) {
				if (zz >= nDiscountLimit && menu.getRebate() != 1.0f && nDiscountLimit>0) {

					showToast(this, "折扣菜一次只能订" + nDiscountLimit + "份",
							Toast.LENGTH_SHORT);
					// di = "false";

				} else {
					o = 1;
					// 统计相同菜品的分数
					for (; lindex < ordSize; lindex++) {
						if (menulist
								.get(i)
								.getMenuid()
								.equals(application.orderlist.get(lindex)
										.getMenuid())) {
							o = application.orderlist.get(lindex).getAmount() + 1;
							application.orderlist.get(lindex).setAmount(o);
							break;
						}
					}
					price = (float) (Math
							.round((menulist.get(i).getPrice() * menulist
									.get(i).getRebate()) * 100)) / 100;
					application.total += price;
					if (lindex == ordSize) {
						OrderList ol = new OrderList(menulist.get(i)
								.getMenuid(), menulist.get(i).getName(), 1,
								price, menulist.get(i).getRebate(), menulist
										.get(i).getDescription());
						application.orderlist.add(ol);
						menuid.add(menulist.get(i).getMenuid());
					}

					AddDate();
					z = 0;
					zz = 0;

					if (nDiscountLimit > 0) {
						for (int j = 0; j < application.orderlist.size(); j++) {
							if (application.orderlist.get(j).getRebate() != 1.0f) {
								z = application.orderlist.get(j).getAmount();
								application.orderlist.get(j).setAmount(z);
								zz += z;
							}

						}
					}

				}
			} else {
				return;
			}
		}
	}

	// 删除已点菜品
	private void deleteOrderMenu(Message msg) {
		int k = msg.what % 1000;
		if (application.orderlist.size() > 0) {
			for (int n = 0; n < application.orderlist.size(); n++) {
				String kMenuid = null;
				String nMenuid = null;
				try {
					kMenuid = menuid.get(k);
					nMenuid = application.orderlist.get(n).getMenuid();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return;
				}

				if (kMenuid.equals(nMenuid)) {
					if (application.orderlist.get(n).getRebate() != 1.0f) {
						z = application.orderlist.get(n).getAmount();
						application.orderlist.get(n).setAmount(z);
						zz--;
					}
					if (application.orderlist.get(n).getAmount() == 1) {
						application.total -= application.orderlist.get(n)
								.getPrice();
						application.total = (float) (Math
								.round(application.total * 100)) / 100;
						application.orderlist.remove(n);
						menuid.remove(k);
					} else {
						int o = application.orderlist.get(n).getAmount() - 1;
						application.orderlist.get(n).setAmount(o);
						application.total -= application.orderlist.get(n)
								.getPrice();
						application.total = (float) (Math
								.round(application.total * 100)) / 100;
					}
					break;
				}
			}
		} else {
			myordertv.setVisibility(View.VISIBLE);
			orderListView.setAdapter(null);
			orderListView.setVisibility(View.GONE);
		}
		AddDate();
	}

	private void moveTitleLabel(int position) {

		// 点击当前按钮所有左边按钮的总宽度
		int visiableWidth = 0;
		// HorizontalScrollView的宽度
		int scrollViewWidth = 0;

		mLinearLayout.measure(mLinearLayout.getMeasuredWidth(), -1);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				mLinearLayout.getMeasuredWidth(), -1);
		params.gravity = Gravity.CENTER_VERTICAL;
		mLinearLayout.setLayoutParams(params);
		for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
			MenuCategoryTextView itemView = (MenuCategoryTextView) mLinearLayout
					.getChildAt(i);
			TextPaint paint = itemView.getPaint();
			int width = itemView.getMeasuredWidth();
			if (i < position) {
				visiableWidth += width;
			}
			scrollViewWidth += width;

			if (i == mLinearLayout.getChildCount()) {
				break;
			}
			if (position != i) {
				itemView.setTextColor(Color.WHITE);
				itemView.setIsHorizontaline(false);
				itemView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMin);
				paint.setFakeBoldText(false);
			} else {
				itemView.setTextColor(Color.WHITE);
				itemView.setIsHorizontaline(false);
				itemView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSizeMax);
				paint.setFakeBoldText(false);
			}
		}
		// 当前点击按钮的宽度
		int titleWidth = mLinearLayout.getChildAt(position).getMeasuredWidth();
		int nextTitleWidth = 0;
		// if (position > 0) {
		// 当前点击按钮相邻右边按钮的宽度
		nextTitleWidth = position == mLinearLayout.getChildCount() - 1 ? 0
				: mLinearLayout.getChildAt(position + 1).getMeasuredWidth();
		// }
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		final int move = visiableWidth - (screenWidth - titleWidth) / 2;
		if (mPreSelectItem < position) {// 向屏幕右边移动
			if ((visiableWidth + titleWidth + nextTitleWidth) >= (screenWidth / 2)) {
				// new Handler().post(new Runnable() {
				//
				// @Override
				// public void run() {
				((HorizontalScrollView) mLinearLayout.getParent())
						.setScrollX(move);
				// }
				// });

			}
		} else {// 向屏幕左边移动
			if ((scrollViewWidth - visiableWidth) >= (screenWidth / 2)) {
				((HorizontalScrollView) mLinearLayout.getParent())
						.setScrollX(move);
			}
		}
		mPreSelectItem = position;
	}

	// // 已经下载菜品信息
	// public void showData() {
	// adapter = new CatImageAdapter(Upagetwo.this);
	// gallery.setAdapter(adapter);
	// gallery.setSelection(200);// 设置起始图片显示位置（可以用来制作gallery循环显示效果）
	// // 查询数据库，菜品类类名
	// SelectName();
	// }

	private void showView() {
		myEmpty.setVisibility(View.GONE);

	}

	private void showEmptyInfo() {
		myEmpty.setVisibility(View.VISIBLE);
	}

	/**********************************************************
	 * Function: AddDate Description: 加载所点菜品 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public void AddDate() {
		if (application.orderlist.size() == 0) {
			myordertv.setVisibility(View.VISIBLE);
			orderListView.setVisibility(View.GONE);
		} else {
			ArrayList<HashMap<String, Object>> selMenuItem = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < application.orderlist.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				OrderList orderItem = application.orderlist.get(i);
				map.put("ItemText", orderItem.getName());
				map.put("MoneyText",
						String.valueOf(symbol)
								+ new DecimalFormat("#0.00").format(orderItem
										.getPrice()));
				map.put("NumText", orderItem.getAmount() + " 份");
				map.put("ButtonText", "删除");
				selMenuItem.add(map);
			}

			// 生成适配器的Item和动态数组对应的元素
			slistItemAdapter = new OrdersListViewAdapter(
					this,
					selMenuItem,// 数据源
					R.layout.uselected_view,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "ItemText", "MoneyText", "NumText",
							"ButtonText" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.usetv, R.id.ormoney, R.id.ornum, R.id.dele });

			// 添加并且显示
			myordertv.setVisibility(View.GONE);
			orderListView.setVisibility(View.VISIBLE);
			orderListView.setAdapter(slistItemAdapter);
			slistItemAdapter.notifyDataSetChanged();
			orderListView.setSelection(selMenuItem.size());
		}
		if (application.total <= 0) {
			application.total = 0f;
		}
		totaltv.setText(new DecimalFormat("#0.00").format(application.total));
	}

	/**
	 * 进入订单确认界面
	 */
	private void orderDinner() {
		if (application.orderlist.size() == 0) {
			myordertv.setVisibility(View.VISIBLE);
			showToast(Upagetwo.this, "你还没有选择下定菜品", Toast.LENGTH_SHORT);
		} else {
			if (isNetworkAvailable()) {
				Intent submitIntent = new Intent(Upagetwo.this,
						OrderSureActivity.class);
				startActivity(submitIntent);

			} else {
				noNetLink(Upagetwo.this);
			}
		}
	}

	// 点击小图标相识菜品详情信息
	private void showDetailMsg(Message msg) {
		int x = msg.what % 10;
		int y = msg.what / 10;
		int i = 2 * y + x - 1;
		System.out.println("订单长度：" + application.orderlist.size());
		orderList = new OrderList(menulist.get(i).getMenuid(), menulist.get(i)
				.getName(), application.orderlist.size(), menulist.get(i)
				.getPrice(), menulist.get(i).getRebate(), menulist.get(i)
				.getDescription());
		// menuid.add(menulist.get(i).getMenuid());
		Intent detailIntent = new Intent(Upagetwo.this,
				MenuDetailActivity.class);
		Bundle detailBundle = new Bundle();
		detailBundle.putSerializable("orderList", orderList);
		detailIntent.putExtras(detailBundle);
		startActivity(detailIntent);
	}

	private void noNetLink(Context context) {
		Dialog netDialog = new AlertDialog.Builder(context)
				.setTitle("温馨提示")
				.setMessage("您的网络连接未打开")
				.setPositiveButton("前往打开",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						showToast(Upagetwo.this, "您的网络连接未打开，您将无法进行网络操作！",
								Toast.LENGTH_LONG);
					}
				}).create();
		netDialog.show();
		netDialog.setCanceledOnTouchOutside(false);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) this
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragments;
		private FragmentManager fm;
		private FragmentTransaction ft;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void setFragments(ArrayList<Fragment> fragments) {
			if (this.fragments != null) {
				ft = fm.beginTransaction();
				for (Fragment f : this.fragments) {
					ft.remove(f);
				}
				ft.commit();
				fm.executePendingTransactions();
			}
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		// 初始化每个页卡选项
//		@Override
//		public Object instantiateItem(ViewGroup arg0, int arg1) {
//			return super.instantiateItem(arg0, arg1);
//		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			System.out.println("position Destory" + position);
			//super.destroyItem(container, position, object);
		}

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		// 此方法是页面跳转完后得到调用
		@Override
		public void onPageSelected(int position) {

			System.out.println("在这里：" + position);
			moveTitleLabel(position);
			select_item = position;
			System.out.println("position:" + position);
			System.out.println("menuCatnamelist:" + menuCatnamelist);
			CategoryDB cdb = new CategoryDB(Upagetwo.this);
			MenuDB mdb = new MenuDB(Upagetwo.this);
			String menucategory = menuCatnamelist.get(select_item);
			String menu_cat_Id = cdb.getId(menucategory);
			menulist = mdb.getMenu(menu_cat_Id);
			cdb.close();
			mdb.close();
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

	}

	private void clearData() {
		// 数据清除

		
		menuid.clear();
		application.total = 0;
		application.orderlist.clear();
		application.order.cleanAll();
		application.foodtime = "";
		orderListView.setAdapter(null);
		totaltv.setText(new DecimalFormat("#0.00").format(application.total)
				+ "");
		if (application.orderlist.size() == 0) {
			myordertv.setVisibility(View.VISIBLE);
		}
	}

	// 避免按钮重复点击
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		lastClickTime = time;
		if (timeD < 500) { // 0.5秒中之内只允许点击1次
			return true;
		}
		return false;
	}

	// 不让Toast多显示长时间
	private void showToast(Context context, String msg, int showtime) {
		// 实例化一个Toast对象
		// mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		// if (mToast != null) {
		// mToast.setText(msg);
		// } else {
		// mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		// }
		// mToast.show();
		if (mToast == null) {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		} else {
			// mToast.cancel();
			mToast.setText(msg);
		}
		mToast.show();

	}

	/**
	 * 离开此界面关闭数据库
	 */
	private void closeDB() {
		rdb.close();
	}

	private int totalWidth;// 总长度
	private int leftWidth;// 距离左边可以移动的距离
	private int totalMenuWidth = 0;// 到当前菜品的所有菜品长度
	private int moveLength = 0;
	private static float textSizeMin;
	private static float textSizeMax;

	/**
	 * 点击菜品类移动居中
	 * 
	 * @param position
	 */
	private void moveHsv(int position) {

		for (int i = 0; i < position; i++) {
			totalMenuWidth += mLinearLayout.getChildAt(i).getMeasuredWidth();
		}

		leftWidth = (int) (totalMenuWidth
				+ mLinearLayout.getChildAt(position).getMeasuredWidth() / 2 + 0.5f);

		moveLength = (int) (totalMenuWidth - mScreenWidth / 2
				+ mLinearLayout.getChildAt(position).getMeasuredWidth() / 2 + 0.5f);

		mHorizontalScrollView.smoothScrollTo(moveLength, 0);

		totalMenuWidth = 0;

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// exit();
			myhandler.sendEmptyMessage(2015);

			Upagetwo.this.finish();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
