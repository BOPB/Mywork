package com.zrh.urestaurantprivate.adapter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrh.urestaurantprivate.bean.MenuCategory;
import com.zrh.urestaurantprivate.entitydb.CategoryDB;
import com.zrh.urestaurantprivate.umenu.R;
import com.zrh.urestaurantprivate.umenu.Upagetwo;
import com.zrh.urestaurantprivate.util.Utils;

/**
 * @copyright 中荣恒科技有限公司
 * @function 菜品类适配器
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class CategoryImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context context;
	private int select_item;
	private List<MenuCategory> category;
	private List<String> namelist;
	private CategoryDB edb = null;// 菜品分类

	// 构造函数
	public CategoryImageAdapter(Context context) {
		this.context = context;
		TypedArray typedArray = context
				.obtainStyledAttributes(R.styleable.Gallery);
		mGalleryItemBackground = typedArray.getResourceId(
				R.styleable.Gallery_android_galleryItemBackground, 0);
		typedArray.recycle();
		edb = new CategoryDB(context);
		category = new ArrayList<MenuCategory>();
		category = edb.getEvi();
		edb.close();
		namelist = new ArrayList<String>();
		for (MenuCategory e : category) {
			namelist.add(e.getCategory_name());
		}
		category = null;
	}

	/**
	 * 增加这样一个静态类，缓存一下，这样不用每次都重新加载
	 * 
	 * @author Administrator
	 * 
	 */
	final class ViewHolder {

		public TextView textView;
	}

	/**
	 * 返回所有菜品类的种类个数
	 */
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	/**
	 * 返回数据在资源的位置
	 */
	@Override
	public Object getItem(int position) {
		return namelist.get(position);
	}

	/**
	 * 返回数据在资源的位置
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 此方法是最主要的，他设置好的View对象返回给Gallery
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (namelist.size() > 0) {
			ViewHolder holder;
			if (convertView == null) {
				// View的findViewById()方法也是比较耗时的，因此需要考虑中调用一次，之后用
				// View的getTag()来获取这个ViewHolder对象
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.gallerylayout,
						null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.gallerytext);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			int w = Utils.getScreenWidth(context);//获取屏幕宽度
			holder.textView.setMinimumWidth(w / 4);//
			holder.textView.setText(namelist.get(position % namelist.size()));
			this.select_item = Upagetwo.select_item;
			if (this.select_item == position) {
				holder.textView.setTextSize(33); // 选中的Item字体：33px
			} else
				holder.textView.setTextSize(25); // 未选中的Item字体：25px
		}
		return convertView;
	}

	public int getmGalleryItemBackground() {
		return mGalleryItemBackground;
	}

	public void setmGalleryItemBackground(int mGalleryItemBackground) {
		this.mGalleryItemBackground = mGalleryItemBackground;
	}

}
