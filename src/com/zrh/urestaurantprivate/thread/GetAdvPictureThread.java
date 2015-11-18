package com.zrh.urestaurantprivate.thread;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.zrh.urestaurantprivate.bean.Advertisement;
import com.zrh.urestaurantprivate.entitydb.AdvertisementDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.ServerAPI;

/**
 * 获取广告图片
 * @copyright 中荣恒科技有限公司
 * @function 获取广告图片
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class GetAdvPictureThread extends Thread {
	private Advertisement advLoc;
	private Advertisement adv;
	private List<Advertisement> localAdv;
	private AdvertisementDB adb;
	private JSONObject jsonObj;// 返回的json对象
	private String jsonString;
	private List<Advertisement> advMent;// 广告图片类
	private Context context;

	public GetAdvPictureThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		super.run();
		Map<String, String> map = ServerAPI.getInstance(context)
				.loadAdvertisementPhoto();
		jsonString = HttpUtils.getJsoneq(map);
		if (jsonString.equals("false")) {
			return;
		}
		jsonObj = JsonTools.parseJson(jsonString);
		if (jsonObj != null) {
			// 获取环境广告图片
			advMent = JsonTools.getAdvertisement(jsonObj);
			if (advMent != null) {
				// list_adv = ImageTools.getAdvImage(advMent);
				// 获取广告图片集合
				adb = new AdvertisementDB(context);
				localAdv = adb.getHa();// 获取数据库中的图片md5
				adb.close();
				if (advMent != null && advMent.size() > 0) {
					// 数据库
					adb = new AdvertisementDB(context);
					adb.delete();
					adb.addEvi(advMent);
					adb.close();
					boolean bIsExists = false;
					// 比对本地数据库的图片MD5值与接口获取到的数据图片MD5值
					for (int i = 0; i < advMent.size(); i++) {
						adv = advMent.get(i);
						bIsExists = false;
						for (int j = 0; j < localAdv.size(); j++) {
							advLoc = localAdv.get(j);
							if (adv.getAdvertisementid().equals(
									advLoc.getAdvertisementid())) {
								bIsExists = true;
								if (!adv.getHash().equals(advLoc.getHash())) {
									// MD5值有变化，需要重新下载
									advMent.get(i).setChangedStatus(1);
								}
								break;
							}
						}
						if (!bIsExists) {
							// 新的图片，需要下载
							advMent.get(i).setChangedStatus(2);
						}
					}
					String[] slist = new String[advMent.size()];
					int k = 0;
					for (int i = 0; i < slist.length; i++) {
						int nChangedStatus = advMent.get(i).getChangedStatus();
						if (nChangedStatus == 1 || nChangedStatus == 2) {
							slist[k++] = advMent.get(i).getPic();
						} else {
							if (!MyConstants.isExistsFile("env", advMent.get(i)
									.getPic())) {
								slist[k++] = advMent.get(i).getPic();
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
