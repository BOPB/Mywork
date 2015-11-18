package com.zrh.urestaurantprivate.umenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zrh.urestaurantprivate.bean.Advertisement;
import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;
import com.zrh.urestaurantprivate.entitydb.AdvertisementDB;
import com.zrh.urestaurantprivate.entitydb.EnviromentDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.ImageTools;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.util.AvoidTwoClick;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.util.MD5;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

/**
 * @copyright 中荣恒科技有限公司
 * @function 我的餐厅页面:广告滑动效果和餐厅环境图片
 * @author 吴强
 * @version v1.1
 * 
 * @date 2015-02-04
 */
public class MyRestaurantActivity extends Activity {

	private ImageView[] imagePoints = null;// 存放有动画效果的小点
	private ImageView[] imageViewsRes = null;// 存放餐厅环境图片
	private ImageView imageView = null;// 存放单张图片
	private ImageView imageviewRes = null;
	private ViewPager advPager = null;
	private AtomicInteger whatInt = new AtomicInteger(0);// 线性安全的操作加减运算
	private List<Advertisement> advMent;// 广告图片类
	private List<Bitmap> advListBitmap;
	private List<Bitmap> resListBitmap;// 服务器返回的图片
	private ViewPager vpager_enviroment;
	private List<ResEnvPicInfo> resEnvPicInfo;// 餐厅环境图片
	private Bitmap eBitmap;
	private Bitmap aBitmap;
	private Dialog mDialog = null;
	private List<View> advBitmapChangeImage;// 广告bitmap转换view
	private List<View> envBtimapChangeImage;// 餐厅环境bitmap转换view
	private ImageView advImage;
	private ImageView envImage;
	private boolean isExit = false;
	private Toast mToast;
	private ImageView iv;// 点击屏幕，重新加载，无数据时显示此画面
	private LinearLayout ll_renvpic;// 包裹餐厅环境和广告图片的线性布局容器
	private final Handler viewHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(whatInt.get());
		}
	};
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 初始化广告图片
			case 0x110:
				iv.setVisibility(View.GONE);
				ll_renvpic.setVisibility(0);
				initViewPager();
				closeReflashDialog();
				break;
			// 初始化餐厅环境图片
			case 0x111:
				iv.setVisibility(8);
				ll_renvpic.setVisibility(0);
				initResViewPager();
				closeReflashDialog();
				break;
			// 服务器数据异常
			case 0x112:
				ll_renvpic.setVisibility(8);
				iv.setVisibility(0);
				closeReflashDialog();
				Toast.makeText(MyRestaurantActivity.this, "服务器数据异常",
						Toast.LENGTH_LONG).show();
				break;
			// 连接超时，检查网络
			case 0x113:
				ll_renvpic.setVisibility(8);
				iv.setVisibility(0);
				closeReflashDialog();
				Toast.makeText(MyRestaurantActivity.this, "数据请求失败，请检查网络",
						Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}
	};
	private View newView;
	private View newView1;
	private SharePreferenceUtil sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advertiseviewpager);
		iv = (ImageView) findViewById(R.id.iv);
		ll_renvpic = (LinearLayout) findViewById(R.id.ll_renvpic);
		sp = new SharePreferenceUtil(this, MyConstants.SAVE_USER);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (AvoidTwoClick.canClick) {
					AvoidTwoClick.canClick = false;
					AvoidTwoClick.avoidTwoClick();
					showRequestDialog("数据正在加载中，请稍等...");
					new GetEnvPictureThread().start();

				}
			}
		});
		doFirst();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	/**
	 * 应用第一次运行时调用
	 */
	private void doFirst() {
		if (sp.getIsFirst()) {
			showRequestDialog("数据正在加载中，请稍等...");
			sp.setIsFirst(false);
			new GetEnvPictureThread().start();
		} else {
			new advThread().start();
			new envThread().start();
		}
	}

	/**
	 * 获取广告图片
	 */
	private class advThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			getAdvPicture();
		}
	}

	/**
	 * 获取餐厅环境图片
	 * 
	 * @author Administrator
	 * 
	 */
	private class envThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			getEnvPicture();
		}
	}

	/**
	 * 获取广告图片
	 */
	private void getAdvPicture() {

		adb = new AdvertisementDB(this);
		advMent = adb.getEvi();
		adb.close();
		advListBitmap = new ArrayList<Bitmap>();
		if (advMent != null && !advMent.equals("") && advMent.size() > 0) {
			File directory = Environment.getExternalStorageDirectory();
			String parth = null;
			try {
				parth = directory.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> filelist = new ArrayList<String>();
			for (Advertisement adv : advMent) {
				String fileName = adv.getPic().substring(
						adv.getPic().lastIndexOf("/") + 1);
				filelist.add(parth + MyConstants.D_IMG_DIR_ENV + fileName);// /urestaurant
			}
			for (int i = 0; i < filelist.size(); i++) {

				aBitmap = getImageThumbnail(filelist.get(i));

				advListBitmap.add(aBitmap);
				aBitmap = null;
			}
			handler.sendEmptyMessage(0x110);

		} else {
			handler.sendEmptyMessage(0x112);
		}

	}

	/**
	 * 获取餐厅环境图片
	 */
	private void getEnvPicture() {
		EnviromentDB edb = new EnviromentDB(this);
		resEnvPicInfo = edb.getEvi();
		edb.close();
		resListBitmap = new ArrayList<Bitmap>();
		if (resEnvPicInfo != null && !resEnvPicInfo.equals("")
				&& resEnvPicInfo.size() > 0) {
			File directory = Environment.getExternalStorageDirectory();
			String parth = null;
			try {
				parth = directory.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> filelists = new ArrayList<String>();
			for (ResEnvPicInfo env : resEnvPicInfo) {
				String fileName = env.getPic().substring(
						env.getPic().lastIndexOf("/") + 1);
				filelists.add(parth + MyConstants.D_IMG_DIR_ENV + fileName);// /urestaurant
			}

			for (int i = 0; i < filelists.size(); i++) {
				eBitmap = getImageThumbnail(filelists.get(i));
				resListBitmap.add(eBitmap);
				eBitmap = null;
			}
			handler.sendEmptyMessage(0x111);
		} else {

			handler.sendEmptyMessage(0x112);

		}

	}

	/**
	 * 初始化餐厅环境viewpager
	 */
	private void initResViewPager() {
		vpager_enviroment = (ViewPager) findViewById(R.id.vpager_environment);
		envBtimapChangeImage = new ArrayList<View>();
		changeToenvView();
		vpager_enviroment.setAdapter(new MyPagerAdapter());
		group = (ViewGroup) findViewById(R.id.viewGroup_res);
		imageViewsRes = new ImageView[resListBitmap.size()];// 点的个数与图片的张数相同
		// 小图标
		for (int i = 0; i < resListBitmap.size(); i++) {
			imageviewRes = new ImageView(this);
			imageviewRes.setLayoutParams(new LayoutParams(20, 20));// 设置点的大小
			imageviewRes.setPadding(5, 5, 5, 5);
			imageViewsRes[i] = imageviewRes;
			if (i == 0) {
				imageViewsRes[i]
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViewsRes[i]
						.setBackgroundResource(R.drawable.page_indicator);
			}
			group.addView(imageViewsRes[i]);// 把3个小点放入到一个线性布局中
		}

		vpager_enviroment.setOnPageChangeListener(new GuidePageChangeListener(
				imageViewsRes));
	}

	/**
	 * 餐厅环境适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return envBtimapChangeImage.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub

			((ViewPager) container).removeView(envBtimapChangeImage
					.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {

			((ViewPager) container).addView(envBtimapChangeImage.get(position));
			return envBtimapChangeImage.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	/**
	 * 初始化广告viewPager
	 * 
	 */
	private void initViewPager() {
		advPager = (ViewPager) findViewById(R.id.adv_pager);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);// 把一个线性布局放进一个view容器中
		imagePoints = new ImageView[advListBitmap.size()];// 点的个数与图片的张数相同
		// 小图标
		for (int i = 0; i < advListBitmap.size(); i++) {
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(20, 20));// 设置点的大小
			imageView.setPadding(5, 5, 5, 5);
			imagePoints[i] = imageView;
			if (i == 0) {
				imagePoints[i]
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imagePoints[i].setBackgroundResource(R.drawable.page_indicator);
			}
			group.addView(imagePoints[i]);// 把所有的小点放入到一个线性布局中
		}
		// 调用bitmap转换图片方法
		advBitmapChangeImage = new ArrayList<View>();
		changeToadvView();
		advPager.setAdapter(new AdvAdapter());
		// advPager.setCurrentItem(400);
		advPager.setOnPageChangeListener(new GuidePageChangeListener(
				imagePoints));
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					viewHandler.sendEmptyMessage(whatInt.get());
					whatInt.incrementAndGet();
					if (whatInt.get() > imagePoints.length - 1) {
						whatInt.getAndAdd(-imagePoints.length);

					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {

					}
				}

			}
		}).start();
	}

	/**
	 * 广告图片页面转换监听类
	 * 
	 * @author Administrator
	 * 
	 */
	private final class GuidePageChangeListener implements OnPageChangeListener {
		private ImageView[] imageViewv;

		public GuidePageChangeListener(ImageView[] imageViews) {
			super();
			this.imageViewv = imageViews;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < imageViewv.length; i++) {
				imageViewv[arg0]
						.setBackgroundResource(R.drawable.page_indicator_focused);
				if (arg0 != i) {
					imageViewv[i]
							.setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}

	/**
	 * 广告图片适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private final class AdvAdapter extends PagerAdapter {
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(advBitmapChangeImage
					.get(position));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// return Integer.MAX_VALUE;// 设置成最大数
			return advBitmapChangeImage.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// advViewStyle = getViewStyle(position);
			((ViewPager) container).addView(advBitmapChangeImage.get(position));
			return advBitmapChangeImage.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	}

	/**
	 * 广告bitmap转换成View
	 */
	public void changeToadvView() {
		for (int i = 0; i < advListBitmap.size(); i++) {
			newView = LayoutInflater.from(MyRestaurantActivity.this).inflate(
					R.layout.styleimage, null);
			advImage = (ImageView) newView.findViewById(R.id.image_style);
			advImage.setImageBitmap(advListBitmap.get(i));
			advBitmapChangeImage.add(newView);
			newView = null;
		}
	}

	/**
	 * 餐厅环境图片bitmap转换成view
	 */
	public void changeToenvView() {
		for (int i = 0; i < resListBitmap.size(); i++) {
			newView1 = LayoutInflater.from(MyRestaurantActivity.this).inflate(
					R.layout.styleimage, null);
			envImage = (ImageView) newView1.findViewById(R.id.image_style);
			envImage.setImageBitmap(resListBitmap.get(i));
			envBtimapChangeImage.add(newView1);
			newView1 = null;
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

	private AdvertisementDB adb;

	/**
	 * 系统返回键监听，实现提示点击两次退出程序功能
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// exit();
			exitBy2Click();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

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
	 * 从sdcard获取图片
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap getImageThumbnail(String imagePath) {
		Bitmap bitmaps = null;
		if (new File(imagePath).exists()) {
			bitmaps = BitmapFactory.decodeFile(imagePath);
		} else {
			bitmaps = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.defaultpic2);

		}
		return bitmaps;
	}

	/**
	 * 下载广告图片
	 */

	private String envString;
	private JSONObject envJson;
	private List<Advertisement> advList;
	private List<ResEnvPicInfo> resList;
	private ViewGroup group;

	private class GetEnvPictureThread extends Thread {
		@Override
		public void run() {

			Map<String, String> map = ServerAPI.getInstance(
					MyRestaurantActivity.this).loadAdvertisementPhoto();
			envString = HttpUtils.getJsoneq(map);

			if (envString.equals(false)) {
				handler.sendEmptyMessage(0x113);
			} else {

				envJson = JsonTools.parseJson(envString);
				advList = JsonTools.getAdvertisement(envJson);
				resList = JsonTools.getEnvironment(envJson);

				if (advList != null) {
					new Thread() {
						public void run() {
							advListBitmap = ImageTools.getAdvImage(advList);
							if (advListBitmap != null) {
								handler.sendEmptyMessage(0x110);
							} else {
								handler.sendEmptyMessage(0x112);
							}
						};
					}.start();
					new Thread() {
						public void run() {
							resListBitmap = ImageTools.getImage(resList);
							if (resListBitmap != null) {
								handler.sendEmptyMessage(0x111);
							} else {
								handler.sendEmptyMessage(0x112);
							}
						};
					}.start();

				} else {
					handler.sendEmptyMessage(0x112);
				}
			}

		}
	}

	/**
	 * 判断餐厅环境图片和广告图片是否更新
	 */
	private class UpdateThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String str = "store-Update" + "env" + sp.getIsEnvUpdate();
			Map<String, String> map = ServerAPI.getInstance(
					getApplicationContext()).UpdateThread(
					sp.getIsEnvUpdate() + "", MD5.GetMD5Code(str));
			// Map<String, String> map = new HashMap<String, String>();
			//
			// map.put("a", "dataUpdate");
			// map.put("c", "store");
			// map.put("data", "env");
			// map.put("change", sp.getIsEnvUpdate() + "");
			// map.put("ve", MD5.GetMD5Code(str));
		}
	}
}
