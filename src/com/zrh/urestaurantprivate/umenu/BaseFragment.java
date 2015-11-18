package com.zrh.urestaurantprivate.umenu;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zrh.urestaurantprivate.adapter.DishesListViewAdapter;
import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.entitydb.MenuDB;
import com.zrh.urestaurantprivate.httputils.MyConstants;

/**
 * @copyright 中荣恒科技有限公司
 * @function 存放每一个菜品\商品类型的模块，并为他们添加数据
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class BaseFragment extends Fragment {

	private String catId = "";
	private ListView dishListView;
	private DishesListViewAdapter dishesListViewAdapter;
	private char symbol = 165;
	private Context context;
	private MyApplication application;
	private TextView tvNodata;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment, null);
		tvNodata = (TextView) view.findViewById(R.id.tv_nodata1);
		dishListView = (ListView) view.findViewById(R.id.dishes_list);// 菜品
		dishListView.setDivider(null);
		application = (MyApplication) getActivity().getApplicationContext();
		// TextView tv_fragment = (TextView)
		// view.findViewById(R.id.tv_fragment);
		// tv_fragment.setText(text);
		Bundle args = getArguments();
		catId = args != null ? args.getString("catId") : "";
		UpdateDatas(catId);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = (Upagetwo) activity;
	}

	public void UpdateDatas(String type) {

		MenuDB mdb = new MenuDB(context);
		application.menulist = mdb.getMenu(type);
		mdb.close();
		if (application.menulist.size() > 0) {
			tvNodata.setVisibility(View.GONE);
			System.out.println("menulist:" + application.menulist);
			int len = 0;
			File directory = Environment.getExternalStorageDirectory();
			String parth = null;
			try {
				parth = directory.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> filelist = new ArrayList<String>();
			for (Menu m : application.menulist) {
				String fileName = m.getPic().substring(
						m.getPic().lastIndexOf("/") + 1);
				filelist.add(parth + MyConstants.D_IMG_DIR_MENU + fileName);// /urestaurant
			}
			len = application.menulist.size();
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			if (len % 2 == 0) {
				for (int i = 0; i < len; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage", filelist.get(i));// 图像资源的ID
					if (application.menulist.get(i).getRebate() == 1f) {
						map.put("ItemText",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice()));
					} else {
						map.put("ItemText",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice())
										+ "("
										+ new DecimalFormat("#0.0")
												.format(application.menulist
														.get(i).getRebate() * 10)
										+ "折)");
					}
					map.put("ItemImage1", filelist.get(++i));// 图像资源的ID
					if (application.menulist.get(i).getRebate() == 1f) {
						map.put("ItemText1",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice()));
					} else {
						map.put("ItemText1",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice())
										+ "("
										+ new DecimalFormat("#0.0")
												.format(application.menulist
														.get(i).getRebate() * 10)
										+ "折)");
					}
					listItem.add(map);
				}
			} else {
				len -= 1;
				for (int i = 0; i < len; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage", filelist.get(i));// 图像资源的ID
					if (application.menulist.get(i).getRebate() == 1f) {
						map.put("ItemText",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice()));
					} else {
						map.put("ItemText",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice())
										+ "("
										+ new DecimalFormat("#0.0")
												.format(application.menulist
														.get(i).getRebate() * 10)
										+ "折)");
					}
					map.put("ItemImage1", filelist.get(++i));// 图像资源的ID
					if (application.menulist.get(i).getRebate() == 1f) {
						map.put("ItemText1",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice()));
					} else {
						map.put("ItemText1",
								application.menulist.get(i).getName()
										+ "\n "
										+ String.valueOf(symbol)
										+ new DecimalFormat("#0.00")
												.format(application.menulist
														.get(i).getPrice())
										+ "("
										+ new DecimalFormat("#0.0")
												.format(application.menulist
														.get(i).getRebate() * 10)
										+ "折)");
					}
					listItem.add(map);
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", filelist.get(len));// 图像资源的ID
				if (application.menulist.get(len).getRebate() == 1f) {
					map.put("ItemText",
							application.menulist.get(len).getName()
									+ "\n "
									+ String.valueOf(symbol)
									+ new DecimalFormat("#0.00")
											.format(application.menulist.get(
													len).getPrice()));
				} else {
					map.put("ItemText",
							application.menulist.get(len).getName()
									+ "\n "
									+ String.valueOf(symbol)
									+ new DecimalFormat("#0.00")
											.format(application.menulist.get(
													len).getPrice())
									+ "("
									+ new DecimalFormat("#0.0")
											.format(application.menulist.get(
													len).getRebate() * 10)
									+ "折)");
				}
				map.put("ItemImage1", "00");// 图像资源的ID
				map.put("ItemText1", "00");
				listItem.add(map);
			}

			// 生成适配器的Item和动态数组对应的元素
			dishesListViewAdapter = new DishesListViewAdapter(context,
					listItem,// 数据源
					R.layout.image_item,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "ItemImage", "ItemText", "ItemImage1",
							"ItemText1" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.image_one, R.id.msg_one, R.id.image_two,
							R.id.msg_two });
			// 添加并且显示
			dishListView.setAdapter(dishesListViewAdapter);
		} else {
			tvNodata.setVisibility(View.VISIBLE);
		}
	}

}
