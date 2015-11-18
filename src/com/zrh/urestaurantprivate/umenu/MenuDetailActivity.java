package com.zrh.urestaurantprivate.umenu;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrh.urestaurantprivate.bean.OrderList;
import com.zrh.urestaurantprivate.entitydb.RestaurantInfoDB;
import com.zrh.urestaurantprivate.umenu.Upagetwo.MyHandler;
/**
 * @copyright 中荣恒科技有限公司
 * @function 菜单详情界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class MenuDetailActivity extends Activity implements OnClickListener {
	private Button detail_back;
	private Button rush_purchase;
	private TextView price;
	private EditText num;
	private TextView zhekou;
	private TextView total_price;
	private TextView name;
	private TextView detail_msg;
	private ImageView add;
	private ImageView sub;
	private OrderList orderList;
	private MyHandler mHandler = null;
	private MyApplication application;
	private static DetailHandler handler = null;
	private float total;
	private int i = 1;
	private RestaurantInfoDB rdb = null;// 餐厅信息DB
	private Toast mToast;
	private int orderNum = 1;
	private float totalNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail);
		mHandler = Upagetwo.getHandler();
		handler = new DetailHandler();
		application = (MyApplication) this.getApplicationContext();
		rdb = new RestaurantInfoDB(MenuDetailActivity.this);
		price = (TextView) findViewById(R.id.price);
		num = (EditText) findViewById(R.id.num);
		zhekou = (TextView) findViewById(R.id.zhekou);
		total_price = (TextView) findViewById(R.id.total_price);
		name = (TextView) findViewById(R.id.name);
		detail_msg= (TextView) findViewById(R.id.detail_msg);
		detail_back = (Button) findViewById(R.id.detail_back);
		rush_purchase = (Button) findViewById(R.id.rush_purchase);
		add = (ImageView) findViewById(R.id.add);
		sub = (ImageView) findViewById(R.id.sub);
		sub.setEnabled(false);
		detail_back.setOnClickListener(this);
		rush_purchase.setOnClickListener(this);
		add.setOnClickListener(this);
		sub.setOnClickListener(this);
		Intent intent = getIntent();
		orderList = (OrderList) intent.getSerializableExtra("orderList");
		System.out.println("orderList:" + orderList.toString());
		name.setText(orderList.getName());
		price.setText(orderList.getPrice() + "元");
		System.out.println("orderList.getAmount():"+orderList.getAmount());
		num.addTextChangedListener(mTextWatcher);
		num.setSelection(num.length());
		if (orderList.getRebate() == 1.0) {
			zhekou.setText("无折扣");
		} else {
			zhekou.setText(new DecimalFormat("#0.00").format(orderList
					.getRebate() * 10) + "折");
		}
		total = orderList.getPrice() * orderList.getRebate();
		total_price.setText(new DecimalFormat("#0.00").format(total) + "元");
		if("null".equals(orderList.getDescription()) || "".equals(orderList.getDescription())){
			detail_msg.setText("暂无相关介绍");
		}else{
			detail_msg.setText(orderList.getDescription());
		}
		
	}

	// 实时监听输入
	TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			System.out.println(num.getText().toString());
//			if(orderList.getAmount()>0){
//				num.setText(orderList.getAmount());
//			}
			if (num.getText().toString() == null
					|| num.getText().toString().equals("")) {
				num.setText("1");
				num.setSelection(num.length());
				showToast(MenuDetailActivity.this, "最少购买一份", Toast.LENGTH_SHORT);
			} else {
				if (Integer.parseInt(num.getText().toString()) > 99999) {
					num.setText("99999");
					num.setSelection(num.length());
					showToast(MenuDetailActivity.this, "最多购买99999份",
							Toast.LENGTH_SHORT);
				} else {
					if (Integer.parseInt(num.getText().toString()) > 1) {
						sub.setEnabled(true);
						//sub.setBackgroundResource(R.drawable.imageview_sub_bg);
					} else if (Integer.parseInt(num.getText().toString()) == 1) {
						sub.setEnabled(false);
						sub.setBackgroundResource(R.drawable.btn_minus_disabled);
					}
					System.out.println("num.getText()" + num.getText());
					if (num.getText().toString().length() > 0) {
						orderNum = Integer.parseInt(num.getText().toString());
						totalNum = orderList.getPrice() * orderNum
								* orderList.getRebate();
						total_price.setText(new DecimalFormat("#0.00")
								.format(totalNum) + "元");
					} else {
						total_price.setText(total + "元");
					}
				}
			}

		}

	};

	@Override
	public void onClick(View v) {
		if (v == detail_back) {
			MenuDetailActivity.this.finish();
		} else if (v == rush_purchase) {
			int nDiscountLimit = rdb.getInfo().getDiscount_num_perorder_limit();
			System.out.println("限制：" + nDiscountLimit);
			if (orderNum > nDiscountLimit && orderList.getRebate() < 1.0 && nDiscountLimit>0) {
				showToast(MenuDetailActivity.this, "折扣菜一次只能订" + nDiscountLimit
						+ "份", Toast.LENGTH_SHORT);
			} else {
				String menuid = orderList.getMenuid();
				System.out.println("菜品" + menuid);
				if (application.orderlist.size() == 0) {
					if (orderNum == 1) {
						application.total += total;
					} else {
						application.total += totalNum;
					}
					orderList.setAmount(orderNum);
					application.orderlist.add(orderList);
					System.out
							.println("orderList菜品号1：" + orderList.getMenuid());
					mHandler.sendEmptyMessage(2014);
					this.finish();
				} else {
					for (int j = 0; j < application.orderlist.size(); j++) {
						System.out.println("菜品id1:"
								+ application.orderlist.get(j).getMenuid());
						System.out.println("菜品id2:" + menuid);
						if (menuid == application.orderlist.get(j).getMenuid()) {
							System.out.println("菜品id3:"
									+ application.orderlist.get(j).getMenuid());
							System.out.println("菜品id4:" + menuid);
							int orderNumTemp = application.orderlist.get(j)
									.getAmount() + orderNum;
							application.orderlist.get(j)
									.setAmount(orderNumTemp);
							float totalTemp = orderList.getPrice()
									* orderList.getRebate() * orderNumTemp;
							if(application.total==0f){
								application.total = totalTemp;
							}else{
								application.total +=  orderList.getPrice()* orderList.getRebate() *orderNum;;
							}
							
							mHandler.sendEmptyMessage(2014);
							this.finish();
							return;
						}
					}
					if (orderNum == 1) {
						application.total += total;
					} else {
						application.total += totalNum;
					}
					orderList.setAmount(orderNum);
					application.orderlist.add(orderList);
					System.out
							.println("orderList菜品号2：" + orderList.getMenuid());
					mHandler.sendEmptyMessage(2014);
					this.finish();

				}

			}

		} else if (v == add) {
			handler.sendEmptyMessage(2014);
		} else if (v == sub) {
			handler.sendEmptyMessage(2015);
		}
	}

	public class DetailHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 2014) {
				i = Integer.parseInt(num.getText().toString());
				System.out.println("a的值1：" + i);
				i += 1;
				num.setText(String.valueOf(i));
				num.setSelection(num.length());
				System.out.println("a的值2：" + String.valueOf(i));
			}
			if (msg.what == 2015) {
				System.out.println("内容:"
						+ Integer.parseInt(num.getText().toString()));
				i = Integer.parseInt(num.getText().toString());
				i -= 1;
				num.setText(i + "");
				num.setSelection(num.length());
			}
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
