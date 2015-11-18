package com.zrh.urestaurantprivate.thread;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.entitydb.MenuDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
import com.zrh.urestaurantprivate.umenu.MyApplication;

/**
 * @copyright 中荣恒科技有限公司
 * @function 获取菜品及图片信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class GetDishPictureThread extends Thread{
	private JSONObject menuJsonObj;
	private List<Menu> menus;// 菜单类
	private MyApplication application;

	public GetDishPictureThread(MyApplication application) {
		this.application = application;
	}
	@Override
	public void run() {
		super.run();
		Map<String, String> map = ServerAPI.getInstance(application).getMenuMsg(application.util.getMenuChange() + "");
		String jsonMenustring = HttpUtils.getJsoneq(map);
		if (jsonMenustring.equals("false")) {
			return;
		} else {
			menuJsonObj = JsonTools.parseJson(jsonMenustring);
			if (menuJsonObj != null) {
				// 获取菜品信息
				menus = JsonTools.getMenus(menuJsonObj);
				System.out.println("menus:" + menus);
				if (menus != null && !menus.isEmpty()) {
					MenuDB mdb = new MenuDB(application);
					List<Menu> oldMenuList = mdb.getAllMenu();
					mdb.delete();
					mdb.addMenu(menus);
					mdb.close();
					boolean bIsExists = false;
					// 比对本地数据库的图片MD5值与接口获取到的数据图片MD5值
					for (int i = 0; i < menus.size(); i++) {
						Menu me = menus.get(i);
						bIsExists = false;
						for (int j = 0; j < oldMenuList.size(); j++) {
							Menu meLoc = oldMenuList.get(j);
							if (me.getMenuid() == meLoc.getMenuid()) {
								bIsExists = true;
								if (!me.getMenu_md5().equals(
										meLoc.getMenu_md5())) {
									// MD5值有变化，需要重新下载
									menus.get(i).setChangedStatus(1);
								}
								break;
							}
						}
						if (!bIsExists) {
							// 新的图片，需要下载
							menus.get(i).setChangedStatus(2);
						}
					}
					String[] slist = new String[menus.size()];
					int k = 0;
					for (int i = 0; i < slist.length; i++) {
						int nChangedStatus = menus.get(i)
								.getChangedStatus();
						if (nChangedStatus == 1 || nChangedStatus == 2) {
							slist[k++] = menus.get(i).getPic();
						} else {
							if (!MyConstants.isExistsFile("menu", menus
									.get(i).getPic())) {
								slist[k++] = menus.get(i).getPic();
							}
						}
					}
					System.out.println("k的值为：" + k);
					// 下载图片
					if (k > 0) {
						DownLoadThread dlt = new DownLoadThread(
								application, slist);
						dlt.start();
					}
					slist = null;
					menus = null;
				} else {
					return;
				}
			} else {
				return;
			}
		}
	
	}
}
