package com.zrh.urestaurantprivate.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;
import com.zrh.urestaurantprivate.bean.Restaurant;
import com.zrh.urestaurantprivate.entitydb.EnviromentDB;
import com.zrh.urestaurantprivate.entitydb.RestaurantInfoDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.ServerAPI;

/**
 * @copyright 中荣恒科技有限公司
 * @function 获取环境图片信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class GetEnvPictureThread extends Thread {
	private List<ResEnvPicInfo> localMenu;
	private ResEnvPicInfo evn;
	private ResEnvPicInfo evnLoc;
	private Restaurant rest;// 餐厅信息
	private RestaurantInfoDB rdb;// 餐厅信息数据库
	private JSONObject jsonObj;// 返回的json对象
	private String jsonString;
	private EnviromentDB edb;// 餐厅环境图片、广告图片数据库
	private List<ResEnvPicInfo> ments;// 餐厅环境图片
	private Context context;

	public GetEnvPictureThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		Map<String, String> map = ServerAPI.getInstance(context)
				.GetEnvPicture();

		// TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("c", "store");
		// map.put("a", "qenv");
		// map.put("type", "ctapp");
		jsonString = HttpUtils.getJsoneq(map);
		if (jsonString.equals("false")) {
			return;
		}
		jsonObj = JsonTools.parseJson(jsonString);
		if (jsonObj != null) {
			rest = JsonTools.getRestinfo(jsonObj);
			if (rest != null) {
				rdb = new RestaurantInfoDB(context);
				rdb.delete();
				rdb.saveInfo(rest);
				rdb.close();
				// 获取环境图片以及广告图片
				ments = JsonTools.getEnvironment(jsonObj);
				// envBitmap = ImageTools.getImage(ments);
				edb = new EnviromentDB(context);
				localMenu = edb.getHa();// 获取数据库中的图片md5
				edb.close();
				if (ments != null && ments.size() > 0) {
					// 数据库
					edb = new EnviromentDB(context);
					edb.delete();
					edb.addEvi(ments);
					edb.close();
					boolean bIsExists = false;
					// 比对本地数据库的图片MD5值与接口获取到的数据图片MD5值
					for (int i = 0; i < ments.size(); i++) {
						evn = ments.get(i);
						bIsExists = false;
						for (int j = 0; j < localMenu.size(); j++) {
							evnLoc = localMenu.get(j);
							if (evn.getEnviromentid().equals(
									evnLoc.getEnviromentid())) {
								bIsExists = true;
								if (!evn.getHash().equals(evnLoc.getHash())) {
									// MD5值有变化，需要重新下载
									ments.get(i).setChangedStatus(1);
								}
								break;
							}
						}

						if (!bIsExists) {
							// 新的图片，需要下载
							ments.get(i).setChangedStatus(2);
						}
					}
					String[] slist = new String[ments.size()];
					int k = 0;
					for (int i = 0; i < slist.length; i++) {
						int nChangedStatus = ments.get(i).getChangedStatus();
						if (nChangedStatus == 1 || nChangedStatus == 2) {
							slist[k++] = ments.get(i).getPic();
						} else {
							if (!MyConstants.isExistsFile("env", ments.get(i)
									.getPic())) {
								slist[k++] = ments.get(i).getPic();
							}
						}
					}
					if (k > 0) {
						DownLoadThread dlt = new DownLoadThread(context, slist);
						dlt.start();
					}

				} else {
					return;
				}
			}
		} else {
			return;
		}

	}
}
