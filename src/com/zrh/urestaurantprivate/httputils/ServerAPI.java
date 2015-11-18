package com.zrh.urestaurantprivate.httputils;

import java.util.Map;

import com.zrh.urestaurantprivate.util.MD5;

import android.content.Context;

/**
 * @版权: 2015, 深圳市中荣恒科技有限公司 zonroh.com
 * @功能:接口参数统一管理类
 * @作者: 李文华
 * @版本: v1.2
 * @日期: 2015-3-5下午7:17:58
 * @修改日期:
 * @修改人:
 * @修改内容简述:
 */
public class ServerAPI {
	private static ServerAPI instance;
	private static Object lock = new Object();

	private ServerAPI(Context context) {
	}

	public static ServerAPI getInstance(Context context) {
		synchronized (lock) {
			if (instance == null) {
				instance = new ServerAPI(context);
			}
		}
		return instance;
	}

	/**
	 * 
	 * GetAdvPictureThread
	 * 
	 * @方法名: loadAdvertisementPhoto
	 * @描述: 下载广告图片
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-13下午9:03:21
	 * @作者:李文华
	 */
	public Map<String, String> loadAdvertisementPhoto() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "qenv").put("type", "ctapp").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetDishInfoThread
	 * 
	 * @方法名: getMenuCategory
	 * @描述: 获取菜品类数据
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:35:04
	 * @作者:李文华
	 */
	public Map<String, String> getMenuCategory() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "qmenu").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetDishPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取菜品信息数据
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */
	public Map<String, String> getMenuMsg(String changemenu) {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "updatem").put("changemenu", changemenu).put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取菜品信息数据
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */
	public Map<String, String> GetEnvPicture() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "qenv").put("type", "ctapp").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取订单信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */
//	public Map<String, String> Order(String str) {
//		RequestData requestData = RequestData.getInstance().put("c", "order")
//				.put("a", "hqorder").put("appid", str);
//		return requestData.getParamMap();
//	}

	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 选位订餐页面
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	public Map<String, String> tableinfo() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "qtable").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取并台操作信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	public Map<String, String> GetMergeInfoAPI() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "atable").put("type", "and").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取拆操作信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	public Map<String, String> apartInfoAPI() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "stable").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}
	
	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: getOperaInfoAPI
	 * @描述: 获取开台操作信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-6-17下午17:37:22
	 * @作者:李卓
	 */

	public Map<String, String> getOperaInfoAPI() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "otable").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}
	
	/**
	 * GetEnvPictureThread
	 * 
	 * @方法名: cancelTableAPI
	 * @描述: 取消开台操作信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-7-7下午19:37:22
	 * @作者:李卓
	 */

	public Map<String, String> cancelTableAPI() {
		RequestData requestData = RequestData.getInstance().put("c", "store")
				.put("a", "otable").put("action", "close").put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}
	
	

	/**
	 * 判断餐厅环境图片和广告图片是否更新
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 获取拆操作信息
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	public Map<String, String> UpdateThread(String str, String str1) {
		RequestData requestData = RequestData.getInstance()
				.put("a", "dataUpdate").put("c", "store").put("data", "env")
				.put("change", str).put("ve", str1).put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * 
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 确认订单页面
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	public Map<String, String> orderSure(String str, String str1) {
		RequestData requestData = RequestData.getInstance().put("c", "order")
				.put("a", "horder").put("appid", str).put("json", str1).put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

	/**
	 * 
	 * 
	 * @方法名: getMenuMsg
	 * @描述: 预定订单界面
	 * @参数: @return
	 * @返回类型: Map<String,String>
	 * @日期: 2015-3-17下午8:37:12
	 * @作者:李文华
	 */

	// public Map<String, String> getOrder(String str) {
	// RequestData requestData = RequestData.getInstance().put("c", "order")
	// .put("a", "horder").put("appid", str);
	// return requestData.getParamMap();
	// }

	public Map<String, String> getOrderDeal(String str) {
		RequestData requestData = RequestData.getInstance().put("c", "order")
				.put("a", "hqorder").put("appid", str).put("roleid", MyConstants.ROLEID);
		return requestData.getParamMap();
	}

}
