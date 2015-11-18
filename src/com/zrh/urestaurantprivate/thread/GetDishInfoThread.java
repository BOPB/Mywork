package com.zrh.urestaurantprivate.thread;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.zrh.urestaurantprivate.bean.MenuCategory;
import com.zrh.urestaurantprivate.entitydb.CategoryDB;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.httputils.ServerAPI;
/**
 * @copyright 中荣恒科技有限公司
 * @function 获取菜品类信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class GetDishInfoThread extends Thread {
	private List<MenuCategory> name; // 菜品累型
	private Context context;

	public GetDishInfoThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		super.run();

		Map<String, String> map = ServerAPI.getInstance(context).getMenuCategory();
		String jsonString = HttpUtils.getJsoneq(map);
		if (jsonString.equals("fasle")) {
			return;
		} else {
			if (jsonString != null) {
				// closeReflashDialog();
				JSONObject jsonObj = JsonTools.parseJson(jsonString);
				if (jsonObj != null) {
					// code = jsonObj.optInt("code");
					if (jsonObj.optInt("code") == 0) {
						// 获取菜品类型
						name = JsonTools.getMenuCategory(jsonObj);
						// System.out.println("打印出name"+name);
						if (name.size() > 0) {
							CategoryDB edb = new CategoryDB(context);
							edb.delete();
							edb.add(name);
							edb.close();
						}
					}
				} else if (jsonObj == null) {

					return;
				}
			}
		}

	}
}
