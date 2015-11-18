package com.zrh.urestaurantprivate.umenu;

import java.util.List;

import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.OrderInfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @copyright 中荣恒科技有限公司
 * @function 订单详情界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class OrderDetails extends Activity {
	// private List<OrderInfo> detailInfo;
	private GridView gv_menu;
	private TextView tv_back;
	private OrderInfo detailInfo;// 接受intent传过来的详情信息
	private ListView lv_order_details;
	private List<MenuDetails> listMenuDetails;// 接受intent传过来的菜品信息
	private String detailsString[] = { "订单号", "座位号", "总价", "付款状态", "下单时间",
			"服务员工号", "电话号码", "订单备注" };
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_details);

		detailInfo = (OrderInfo) (this.getIntent()
				.getSerializableExtra("orderInfo"));
		listMenuDetails = (List<MenuDetails>) this.getIntent()
				.getSerializableExtra("menuDetails");

		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		gv_menu = (GridView) findViewById(R.id.gv_menu);
		gv_menu.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				View view = LayoutInflater.from(OrderDetails.this).inflate(
						R.layout.menu_gridview_content, null);
				TextView text = (TextView) view.findViewById(R.id.tv_names);
				TextView text1 = (TextView) view.findViewById(R.id.tv_amounts);
				TextView text2 = (TextView) view.findViewById(R.id.tv_prices);
				TextView text3 = (TextView) view
						.findViewById(R.id.tv_discounts);
				text.setText("" + listMenuDetails.get(arg0).getName());
				text1.setText("" + listMenuDetails.get(arg0).getAmount() + "份");
				text2.setText("¥" + listMenuDetails.get(arg0).getPrice());
				if (listMenuDetails.get(arg0).getDiscount() == 1.0) {
					text3.setText("无");
				} else {
					text3.setText("" + listMenuDetails.get(arg0).getDiscount()
							* 10 + "折");
				}

				// if (arg0 == 2) {
				// text.setText("红烧排骨");
				// }
				return view;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listMenuDetails.size();
			}
		});
		lv_order_details = (ListView) findViewById(R.id.lv_order_details);
		lv_order_details.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				View view = LayoutInflater.from(OrderDetails.this).inflate(
						R.layout.details_listcontent, null);
				TextView text = (TextView) view.findViewById(R.id.tv);
				TextView text1 = (TextView) view.findViewById(R.id.tv1);
				text.setText(detailsString[arg0]);
				switch (arg0) {
				case 0:
					text1.setText("" + detailInfo.getOrderNo());
					break;
				case 1:
					text1.setText("" + detailInfo.getTable_id());
					break;
				case 2:
					text1.setText("¥" + detailInfo.getOrderTotalPrice());
					break;

				case 3:
					if (detailInfo.getPay_status() == 0) {
						text1.setText("未付款");
					} else if (detailInfo.getPay_status() == 1) {
						text1.setText("已付款");
					}

					break;
				case 4:
					text1.setText("" + detailInfo.getOrderTiem());
					break;
				case 5:
					text1.setText("" + detailInfo.getWaiter_id());
					break;
				case 6:
					if(detailInfo.getPhone().equals("")||detailInfo.getPhone()==null)
					{
						text1.setText("无");
					}
					else{
						text1.setText("" + detailInfo.getPhone());
					}
				
					break;
				case 7:
					text1.setText("" + detailInfo.getRemarks());
					break;

				default:
					break;
				}

				return view;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return detailsString.length;
			}
		});
	}
}
