package com.zrh.urestaurantprivate.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrh.urestaurantprivate.umenu.R;
import com.zrh.urestaurantprivate.umenu.Upagetwo;
import com.zrh.urestaurantprivate.umenu.Upagetwo.MyHandler;
import com.zrh.urestaurantprivate.util.AvoidTwoClick;

/**
 * @copyright 中荣恒科技有限公司
 * @function 菜肴图片适配器  对应菜品类的菜品图片列表 显示各个菜的信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
@SuppressWarnings("deprecation")
public class DishesListViewAdapter extends BaseAdapter {

	private MyHandler mHandler = null;

	private class ViewHolder {
		ImageView button1 = null, imageview1 = null, button2 = null,
				imageview2 = null;
		TextView tv1 = null, tv2 = null;
	}

	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private ViewHolder holder;
	private Map<String, SoftReference<Bitmap>> bitmapCache = new HashMap<String, SoftReference<Bitmap>>();

	public DishesListViewAdapter(Context c,
			ArrayList<HashMap<String, Object>> appList, int resource,
			String[] from, int[] to) {

		mHandler = Upagetwo.getHandler();
		mAppList = appList;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
	}

	@Override
	public int getCount() {
		return mAppList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeItem(int position) {
		mAppList.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// if (bitmap1 != null) {
		// bitmap1.recycle();
		// bitmap1 = null;
		// }
		// if (bitmap2 != null) {
		// bitmap2.recycle();
		// bitmap2 = null;
		// }
		// Log.e("TAG", "start 102");
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.image_item, null);
			holder = new ViewHolder();
			holder.button1 = (ImageView) convertView
					.findViewById(R.id.image_one);
			holder.imageview1 = (ImageView) convertView
					.findViewById(R.id.image1);
			holder.tv1 = (TextView) convertView.findViewById(R.id.msg_one);
			holder.button2 = (ImageView) convertView
					.findViewById(R.id.image_two);
			holder.imageview2 = (ImageView) convertView
					.findViewById(R.id.image2);
			holder.tv2 = (TextView) convertView.findViewById(R.id.msg_two);
			convertView.setTag(holder);
		}

		HashMap<String, Object> appInfo = mAppList.get(position);
		if (appInfo != null) {
			String aname1 = (String) appInfo.get(keyString[1]);
			String aname2 = (String) appInfo.get(keyString[3]);
			String id1 = (String) appInfo.get(keyString[0]);
			String id2 = (String) appInfo.get(keyString[2]);
			// holder.button1.setImageBitmap(getImageBitmap1(id1));
			// Log.e("TAG", "set bitmap1 128");
			holder.button1.setImageBitmap(getImageThumbnail(id1, 200, 150));

			holder.button1.setOnClickListener(new buttonListener(
					position * 10 + 1));
			holder.imageview1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (AvoidTwoClick.canClick) {
						AvoidTwoClick.canClick = false;
						AvoidTwoClick.avoidTwoClick();
						Message msg = new Message();
						msg.what = position * 10 + 1;
						msg.arg1 = 2;
						mHandler.sendMessage(msg);

					}

				}
			});
			holder.tv1.setText(aname1);
			if (!id2.equals("00")) {
				// holder.button2.setImageBitmap(getImageBitmap2(id2));
				// Log.e("TAG", "set bitmap2 152");
				holder.button2.setImageBitmap(getImageThumbnail(id2, 200, 150));
				holder.button2.setOnClickListener(new buttonListener(
						position * 10 + 2));
				holder.imageview2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (AvoidTwoClick.canClick) {
							AvoidTwoClick.canClick = false;
							AvoidTwoClick.avoidTwoClick();
							Message msg = new Message();
							msg.what = position * 10 + 2;
							msg.arg1 = 2;
							mHandler.sendMessage(msg);

						}
					}
				});
				holder.tv2.setText(aname2);
			} else {
				holder.button2.setBackgroundColor(Color.WHITE);
				holder.button2.setImageBitmap(null);
				holder.button2.setOnClickListener(null);
				holder.imageview2.setOnClickListener(null);
				holder.imageview2.setVisibility(View.GONE);
				holder.tv2.setText("");
			}
		}
		return convertView;
	}

	/**********************************************************
	 * Function: getImageThumbnail Description: 根据指定的图像路径和大小来获取缩略图 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input:
	 * imagePath 图像的路径 ,width 指定输出图像的宽度 ,height 指定输出图像的高度 Output: 对输出参数的说明。
	 * Return: 无 Others: 其它说明
	 *********************************************************/
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		try {
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			bitmap = null;
			try {
				Thread.sleep(200);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象

		return bitmap;
	}

	class buttonListener implements OnClickListener {
		private int position;

		buttonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			Message msg = new Message();
			msg.what = position;
			msg.arg1 = 1;
			mHandler.sendMessage(msg);
			System.out.println("position:" + position);
			// v.setBackgroundColor(Color.RED);
			// v.setBackgroundColor(Color.TRANSPARENT);
			// v.setBackgroundResource(R.drawable.menu_item_selector);
			// v.setBackgroundDrawable(mContext.getResources().getDrawable(
			// R.drawable.menu_item_selector));

		}
	}

	private Bitmap bitmap1 = null;

	private Bitmap getImageBitmap1(String path)

	{

		Bitmap bitmap = null;
		try {
			// 实例化Bitmap
			bitmap = BitmapFactory.decodeFile(path);
		} catch (OutOfMemoryError e) {
			Log.e("TAG", "OutOfMemory");
		}
		if (bitmap == null) {
			// 如果实例化失败 返回默认的Bitmap对象
			return null;
		}
		return bitmap;
	}

	private Bitmap bitmap2 = null;

	private Bitmap getImageBitmap2(String path)

	{
		bitmap2 = BitmapFactory.decodeFile(path);
		return bitmap2;
	}

	/**
	 * 添加软引用对象
	 * 
	 * @param path
	 */
	public Bitmap addBitmapToCache(String path) {
		// 强引用的Bitmap对象
		Bitmap bitmap = null;
		bitmap = BitmapFactory.decodeFile(path);

		// 软引用的Bitmap对象
		SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
		// 添加该对象到Map中使其缓存
		bitmapCache.put(path, softBitmap);
		// bitmap.recycle();//前面添加到软引用之后，bitmap已经不能再调用了，空指针
		return getBitmapByPath(path);
	}

	/**
	 * 获取软引用对象
	 * 
	 * @param path
	 * @return
	 */
	public Bitmap getBitmapByPath(String path) {
		// 从缓存中取软引用的Bitmap对象
		SoftReference<Bitmap> softBitmap = bitmapCache.get(path);
		// 判断是否存在软引用
		if (softBitmap == null) {
			return null;
		}
		// 取出Bitmap对象，如果由于内存不足Bitmap被回收，将取得空
		Bitmap bitmap = softBitmap.get();
		return bitmap;
	}
}
