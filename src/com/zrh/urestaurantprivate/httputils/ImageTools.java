package com.zrh.urestaurantprivate.httputils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.zrh.urestaurantprivate.bean.Advertisement;
import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * @copyright 中荣恒科技有限公司
 * @function  获取网络图片类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class ImageTools {
	private static List<Bitmap> list_image;
	private static Bitmap bitmap;
    private static List<Bitmap>  list_adv;
	public static List<Bitmap> getImage(List<ResEnvPicInfo> list) {
		list_image = new ArrayList<Bitmap>();
		try {
			// 创建一个url对象

			for (int i = 0; i < list.size(); i++) {
				URL url = new URL(list.get(i).getPic());
				// 打开URL对应的资源输入流
				InputStream is = url.openStream();
				// 从InputStream流中解析出图片
				bitmap = BitmapFactory.decodeStream(is);
				list_image.add(bitmap);

				// 关闭输入流
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			list_image=null;
		}

		return list_image;
	}
	/**
	 * 根据图片地址获取图片
	 * @param list
	 * @return
	 */
	public static List<Bitmap> getAdvImage(List<Advertisement> list) {
		list_adv = new ArrayList<Bitmap>();
		try {
			// 创建一个url对象
			
			for (int i = 0; i < list.size(); i++) {
				URL url = new URL(list.get(i).getPic());
				// 打开URL对应的资源输入流
				InputStream is = url.openStream();
				// 从InputStream流中解析出图片
				bitmap = BitmapFactory.decodeStream(is);
				list_adv.add(bitmap);
				
				// 关闭输入流
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list_adv;
	}
}
