package com.zrh.urestaurantprivate.httputils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.GetChars;
import android.util.Log;

import com.zrh.urestaurantprivate.bean.Advertisement;
import com.zrh.urestaurantprivate.bean.ResEnvPicInfo;
import com.zrh.urestaurantprivate.bean.Menu;
import com.zrh.urestaurantprivate.bean.MenuCategory;
import com.zrh.urestaurantprivate.bean.MenuDetails;
import com.zrh.urestaurantprivate.bean.Order;
import com.zrh.urestaurantprivate.bean.OrderInfo;
import com.zrh.urestaurantprivate.bean.OrderList;
import com.zrh.urestaurantprivate.bean.Restaurant;
import com.zrh.urestaurantprivate.bean.RestaurantTableInfo;

/**
 * @copyright 中荣恒科技有限公司
 * @function json解析工具类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class JsonTools {
	public static final int CODE_SUCCESS = 0;// 操作成功
	public static final int CODE_NOTFOUND = 1004;// 未找到
	public static final int CODE_SESSION_ERROR = 1006;// seession错误
	public static final int CODE_SESSION_OUTTIME = 1007;// seession超时
	public static final int CODE_WNEWORDER_ORDERMAX = 5001;// 下单次数限制

	private static Map<String, String> m_mapErr = new HashMap<String, String>();

	public JsonTools() {

	}

	public static String getErrorInfo(int code) {
		if (m_mapErr == null) {
			m_mapErr = new HashMap<String, String>();
		}

		if (m_mapErr.size() <= 0) {
			initErrorInfo();
		}

		String strErrInfo = m_mapErr.get(String.format("%d", code));
		if (strErrInfo == null)
			strErrInfo = "";

		return strErrInfo;
	}

	public static void initErrorInfo() {
		m_mapErr.put("0", "操作成功");
		m_mapErr.put("1000", "服务器错误");
		m_mapErr.put("1001", "版本错误");
		m_mapErr.put("1002", "缺少必要参数");
		m_mapErr.put("1003", "参数不匹配");
		m_mapErr.put("1004", "未找到");
		m_mapErr.put("1005", "被禁止");
		m_mapErr.put("1006", "seession错误");
		m_mapErr.put("1007", "seession超时");
		m_mapErr.put("1008", "已经存在");
		m_mapErr.put("1009", "验证码错误");
		m_mapErr.put("1010", "验证码超时");
		m_mapErr.put("1011", "参数错误");
		m_mapErr.put("1012", "手机号码错误");
		m_mapErr.put("1013", "不支持的验证码类型");
		m_mapErr.put("1014", "验证码验证信息错误");
		m_mapErr.put("1015", "在规定时间间隔后才能再次发生验证码");
		m_mapErr.put("5001", "下单次数限制");
	}

	/**
	 * 把字符串转换成JSONObject对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static JSONObject parseJson(String jsonString) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * 获取字符串值
	 * 
	 * @param jsonObject
	 * @param strKey
	 * @return
	 */
	private static String getJsonString(JSONObject jsonObject, String strKey) {
		String strValue = "";
		if (jsonObject.has(strKey)) {
			try {
				strValue = jsonObject.getString(strKey);
			} catch (JSONException e) {

			}
		}

		return strValue;
	}

	/**
	 * 解析int型数据
	 * 
	 * @param jsonObject
	 * @param strKey
	 * @return
	 */
	private static int getJsonInt(JSONObject jsonObject, String strKey) {
		int nValue = 0;
		if (jsonObject.has(strKey)) {
			try {
				nValue = jsonObject.getInt(strKey);
			} catch (JSONException e) {

			}
		}

		return nValue;
	}

	/**
	 * 获取餐厅环境图片
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static List<ResEnvPicInfo> getEnvironment(JSONObject jsonObj) {
		List<ResEnvPicInfo> list = new ArrayList<ResEnvPicInfo>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData.getJSONArray("environment");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject mentObject = jsonArray.getJSONObject(i);
							ResEnvPicInfo ment = new ResEnvPicInfo();

							ment.setEnviromentid(getJsonString(mentObject, "id"));
							ment.setPic(getJsonString(mentObject, "picture"));
							ment.setHash(getJsonString(mentObject, "md5"));

							list.add(ment);
						}
					} else {
						list = null;
					}
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	/**
	 * 获取广告图片
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static List<Advertisement> getAdvertisement(JSONObject jsonObj) {
		List<Advertisement> list = new ArrayList<Advertisement>();
		try {
			JSONObject jsonData = jsonObj.optJSONObject("data");
			{
				JSONArray jsonArray = jsonData
						.getJSONArray("environment_small");
				if (jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject mentObject = jsonArray.getJSONObject(i);
						Advertisement advment = new Advertisement();

						advment.setAdvertisementid(getJsonString(mentObject,
								"id"));
						advment.setPic(getJsonString(mentObject, "picture"));
						advment.setHash(getJsonString(mentObject, "md5"));

						list.add(advment);
					}
				} else {
					list = null;
				}
			}

		} catch (Exception e) {
			list = null;
			e.printStackTrace();
		}

		return list;
	}

	// 解析餐厅信息
	public static Restaurant getRestinfo(JSONObject jsonObj) {

		Restaurant rtrt = new Restaurant();
		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");

				JSONObject rtrtObject = jsonData.getJSONObject("shop_info");
				rtrt.setAndroid_app_qrcode(getJsonString(rtrtObject,
						"android_app_qrcode"));
				rtrt.setIos_app_qrcode(getJsonString(rtrtObject,
						"ios_app_qrcode"));
				rtrt.setShop_name(getJsonString(rtrtObject, "shop_name"));
				rtrt.setBusiness_hours_begin(getJsonInt(rtrtObject,
						"business_hours_begin"));
				rtrt.setBusiness_hours_end(getJsonInt(rtrtObject,
						"business_hours_end"));
				rtrt.setShop_phone(getJsonString(rtrtObject, "shop_phone"));
				rtrt.setShop_address(getJsonString(rtrtObject, "shop_address"));
				rtrt.setHost_domain(getJsonString(rtrtObject, "host_domain"));
				rtrt.setOrder_time_limit(getJsonInt(rtrtObject,
						"order_time_limit"));
				rtrt.setOrder_times_perday_limit(getJsonInt(rtrtObject,
						"order_times_perday_limit"));
				rtrt.setDiscount_num_perorder_limit(getJsonInt(rtrtObject,
						"discount_num_perorder_limit"));
			} else {
				rtrt = null;
			}
		} catch (Exception e) {
			rtrt = null;
		}
		return rtrt;
	}

	/**
	 * 获取全部餐桌信息
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static List<RestaurantTableInfo> getTableInfo(JSONObject jsonObj) {
		List<RestaurantTableInfo> list = new ArrayList<RestaurantTableInfo>();
		List<RestaurantTableInfo> listBan = new ArrayList<RestaurantTableInfo>();
		List<RestaurantTableInfo> listTotal = new ArrayList<RestaurantTableInfo>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData.getJSONArray("seatinfo");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject TableObject = jsonArray.getJSONObject(i);
							RestaurantTableInfo tableInfo = new RestaurantTableInfo();
							if ((getJsonInt(TableObject, "seat_amount")) < 100) {
								if ((getJsonInt(TableObject, "isBan") == 0)) {
									tableInfo.setTableID(getJsonString(TableObject,
											"id"));
									tableInfo.setTableIsFree(getJsonInt(
											TableObject, "is_free"));
									tableInfo.setTableLocationId(getJsonInt(
											TableObject, "location_id"));
									tableInfo.setTableType(getJsonInt(TableObject,
											"type"));
									tableInfo.setTableNO(getJsonInt(TableObject,
											"NO"));
									tableInfo.setTableSeatCount(getJsonInt(
											TableObject, "seat_amount"));
									tableInfo.setTableMergeInfo(getJsonInt(
											TableObject, "merge_info"));
									tableInfo.setTableName(getJsonString(
											TableObject, "location_name"));
									tableInfo.setTableOrderInfo(getJsonString(
											TableObject, "order_info"));
									tableInfo.setIsBan(getJsonInt(TableObject,
											"isBan"));
									list.add(tableInfo);
								} else {
									// 禁台list
									tableInfo.setTableID(getJsonString(TableObject,
											"id"));
									tableInfo.setTableIsFree(getJsonInt(
											TableObject, "is_free"));
									tableInfo.setTableLocationId(getJsonInt(
											TableObject, "location_id"));
									tableInfo.setTableType(getJsonInt(TableObject,
											"type"));
									tableInfo.setTableNO(getJsonInt(TableObject,
											"NO"));
									tableInfo.setTableSeatCount(getJsonInt(
											TableObject, "seat_amount"));
									tableInfo.setTableMergeInfo(getJsonInt(
											TableObject, "merge_info"));
									tableInfo.setTableName(getJsonString(
											TableObject, "location_name"));
									tableInfo.setTableOrderInfo(getJsonString(
											TableObject, "order_info"));
									tableInfo.setIsBan(getJsonInt(TableObject,
											"isBan"));
									listBan.add(tableInfo);
								}
							}
						}
					} else {
						list = null;
					}

				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		list.addAll(listBan);
		listTotal = list;
		return listTotal;
	}

	/**
	 * 获取空闲餐桌信息
	 * 
	 */
	public static List<RestaurantTableInfo> getFreeTableInfo(JSONObject jsonObj) {
		List<RestaurantTableInfo> list = new ArrayList<RestaurantTableInfo>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData.getJSONArray("seatinfo");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject TableObject = jsonArray.getJSONObject(i);
							RestaurantTableInfo tableInfo = new RestaurantTableInfo();
							if ((getJsonInt(TableObject, "is_free") == 1)&&
									(getJsonInt(TableObject, "seat_amount")) < 100 &&
									(getJsonInt(TableObject, "isBan") == 0)) {
								tableInfo.setTableID(getJsonString(TableObject,
										"id"));
								tableInfo.setTableIsFree(getJsonInt(
										TableObject, "is_free"));
								tableInfo.setTableLocationId(getJsonInt(
										TableObject, "location_id"));
								tableInfo.setTableType(getJsonInt(TableObject,
										"type"));
								tableInfo.setTableNO(getJsonInt(TableObject,
										"NO"));
								tableInfo.setTableSeatCount(getJsonInt(
										TableObject, "seat_amount"));
								tableInfo.setTableMergeInfo(getJsonInt(
										TableObject, "merge_info"));
								tableInfo.setTableName(getJsonString(
										TableObject, "location_name"));
								tableInfo.setTableOrderInfo(getJsonString(
										TableObject, "order_info"));
								tableInfo.setIsBan(getJsonInt(TableObject,
										"isBan"));
								list.add(tableInfo);
							}
						}
					} else {
						list = null;
					}
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}
	
	/**
	 * 获取已开台餐桌信息
	 * 
	 */
	public static List<RestaurantTableInfo> getOpenTableInfo(JSONObject jsonObj) {
		List<RestaurantTableInfo> list = new ArrayList<RestaurantTableInfo>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData.getJSONArray("seatinfo");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject TableObject = jsonArray.getJSONObject(i);
							RestaurantTableInfo tableInfo = new RestaurantTableInfo();
							if ((getJsonInt(TableObject, "is_free") == 2)&&
									(getJsonInt(TableObject, "seat_amount")) < 100 &&
									(getJsonInt(TableObject, "isBan") == 0)) {
								tableInfo.setTableID(getJsonString(TableObject,
										"id"));
								tableInfo.setTableIsFree(getJsonInt(
										TableObject, "is_free"));
								tableInfo.setTableLocationId(getJsonInt(
										TableObject, "location_id"));
								tableInfo.setTableType(getJsonInt(TableObject,
										"type"));
								tableInfo.setTableNO(getJsonInt(TableObject,
										"NO"));
								tableInfo.setTableSeatCount(getJsonInt(
										TableObject, "seat_amount"));
								tableInfo.setTableMergeInfo(getJsonInt(
										TableObject, "merge_info"));
								tableInfo.setTableName(getJsonString(
										TableObject, "location_name"));
								tableInfo.setTableOrderInfo(getJsonString(
										TableObject, "order_info"));
								tableInfo.setIsBan(getJsonInt(TableObject,
										"isBan"));
								list.add(tableInfo);
							}
						}
					} else {
						list = null;
					}
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}
	
	/**
	 * 获取已下单餐桌信息
	 * 
	 */
	public static List<RestaurantTableInfo> getOrderedTableInfo(JSONObject jsonObj) {
		List<RestaurantTableInfo> list = new ArrayList<RestaurantTableInfo>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData.getJSONArray("seatinfo");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject TableObject = jsonArray.getJSONObject(i);
							RestaurantTableInfo tableInfo = new RestaurantTableInfo();
							if ((getJsonInt(TableObject, "is_free") == 0)&&
									(getJsonInt(TableObject, "seat_amount")) < 100 &&
									(getJsonInt(TableObject, "isBan") == 0)) {
								tableInfo.setTableID(getJsonString(TableObject,
										"id"));
								tableInfo.setTableIsFree(getJsonInt(
										TableObject, "is_free"));
								tableInfo.setTableLocationId(getJsonInt(
										TableObject, "location_id"));
								tableInfo.setTableType(getJsonInt(TableObject,
										"type"));
								tableInfo.setTableNO(getJsonInt(TableObject,
										"NO"));
								tableInfo.setTableSeatCount(getJsonInt(
										TableObject, "seat_amount"));
								tableInfo.setTableMergeInfo(getJsonInt(
										TableObject, "merge_info"));
								tableInfo.setTableName(getJsonString(
										TableObject, "location_name"));
								tableInfo.setTableOrderInfo(getJsonString(
										TableObject, "order_info"));
								tableInfo.setIsBan(getJsonInt(TableObject,
										"isBan"));
								list.add(tableInfo);
							}
						}
					} else {
						list = null;
					}
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}
	
	/**
	 * 获取订单信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static List<OrderInfo> getOrderData(JSONObject jsonObject) {
		List<OrderInfo> orderInfo = new ArrayList<OrderInfo>();
		try {
			if (jsonObject.has("data")) {
				JSONObject json = jsonObject.getJSONObject("data");
				if (json != null) {
					JSONArray jsonArray = json.optJSONArray("historyorder");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonOrder = jsonArray.getJSONObject(i);

							OrderInfo info = new OrderInfo();
							info.setPay_status(getJsonInt(jsonOrder,
									"pay_status"));
							info.setPay_ways(getJsonInt(jsonOrder, "pay_ways"));
							info.setOrder_status(getJsonInt(jsonOrder,
									"order_status"));

							info.setOrderTotalPrice(jsonOrder
									.optDouble("final_price"));
							info.setTotal_discounts(jsonOrder
									.optDouble("total_discounts"));
							info.setOriginal_price(jsonOrder
									.optDouble("original_price"));

							info.setOrderTiem(getJsonString(jsonOrder,
									"order_time"));

							info.setDinner_time(getJsonString(jsonOrder,
									"dinner_time"));
							info.setPay_time(getJsonString(jsonOrder,
									"pay_time"));
							info.setTable_id(getJsonString(jsonOrder,
									"table_id"));

							info.setCustomer_id(getJsonString(jsonOrder,
									"customer_id"));
							info.setIdentify_code(getJsonString(jsonOrder,
									"identify_code"));
							info.setName(getJsonString(jsonOrder, "name"));
							info.setPhone(getJsonString(jsonOrder, "phone"));
							info.setWaiter_id(getJsonString(jsonOrder,
									"waiter_id"));
							info.setRemarks(getJsonString(jsonOrder, "remarks"));
							info.setOrderNo(getJsonString(jsonOrder, "order_id"));
							orderInfo.add(info);
						}
					} else {
						orderInfo = null;
					}
				} else {
					orderInfo = null;
				}

			} else {
				orderInfo = null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			orderInfo = null;
		}

		return orderInfo;

	}

	/**
	 * 订单信息的historyorder 数组的内容 转换成JSONObject的List集合
	 */

	public static List<JSONObject> getJsonList(JSONObject jsonObject) {
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		JSONArray jsonArray = new JSONArray();
		if (jsonObject != null) {
			if (jsonObject.has("data")) {
				try {
					JSONObject json = jsonObject.getJSONObject("data");
					if (json != null) {
						jsonArray = json.optJSONArray("historyorder");// 获取key为historyorder的数组
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonOrder = jsonArray
										.getJSONObject(i);
								jsonObjectList.add(jsonOrder);
							}
						} else {
							jsonObjectList = null;
						}
					} else

					{
						jsonObjectList = null;
					}
				}

				catch (JSONException e) {
					// TODO Auto-generated catch block
					jsonObjectList = null;
					e.printStackTrace();
				}// 获取key为data的值

			} else {
				jsonObjectList = null;
			}
		} else {
			jsonObjectList = null;
		}
		return jsonObjectList;
	}

	/**
	 * 获取订单详情中菜品的信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static List<MenuDetails> getMenuData(JSONObject jsonObject) {

		List<MenuDetails> menuInfo = new ArrayList<MenuDetails>();
		try {
			JSONArray jsonArray = jsonObject.optJSONArray("menuDetail");// 获取key为historyorder的数组
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonMenu = jsonArray.optJSONObject(j);
					MenuDetails mDetails = new MenuDetails();
					mDetails.setAmount(getJsonInt(jsonMenu, "amount"));
					mDetails.setDiscount(jsonMenu.optDouble("discount"));
					mDetails.setId(getJsonString(jsonMenu, "id"));
					mDetails.setMenu_id(getJsonString(jsonMenu, "menu_id"));
					mDetails.setName(getJsonString(jsonMenu, "name"));
					mDetails.setOrder_id(getJsonString(jsonMenu, "order_id"));
					mDetails.setPrice(jsonMenu.optDouble("price"));
					menuInfo.add(mDetails);
				}
			} else

			{
				menuInfo = null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			menuInfo = null;
		}

		return menuInfo;

	}

	/**
	 * 获取拆并台操作结果
	 */
	public static String getMergeResult(JSONObject mergeObject) {
		String description = null;
		try {
			description = mergeObject.getString("scode");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return description;

	}

	/**
	 * 获取拆并台返回结果描述
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static String getMergeDescription(JSONObject mergeObject) {
		String description = null;
		try {
			description = mergeObject.getString("description");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return description;

	}
	
	/**
	 * 获取开台操作结果
	 */
	public static String getOperaResult(JSONObject operaObject) {
		String description = null;
		try {
			description = operaObject.getString("scode");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return description;

	}

	/**
	 * 获取开台返回结果描述
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static String getOperaDescription(JSONObject operaObject) {
		String description = null;
		try {
			description = operaObject.getString("description");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return description;

	}

	/**
	 * 获取菜品类型数据
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static List<MenuCategory> getMenuCategory(JSONObject jsonObj) {
		List<MenuCategory> list = new ArrayList<MenuCategory>();
		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");
				{
					JSONArray jsonArray = jsonData
							.getJSONArray("menu_catogory");
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject catObject = jsonArray.getJSONObject(i);
							if (catObject.optString("goods_type").equals("0")) {
								MenuCategory cat = new MenuCategory();
								cat.setCategory_id(getJsonString(catObject,
										"id"));// 菜品id
								cat.setCategory_name(getJsonString(catObject,
										"name"));// 菜品名
								cat.setCategory_description(getJsonString(
										catObject, "description"));// 菜品类描述
								list.add(cat);
							}

						}
					} else {
						list = null;
					}
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	// 解析菜品信息
	public static List<Menu> getMenus(JSONObject jsonObj) {
		List<Menu> list = new ArrayList<Menu>();

		try {
			if (jsonObj.has("data")) {
				JSONObject jsonData = jsonObj.optJSONObject("data");

				JSONArray jsonArray = jsonData.getJSONArray("menu");
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject menuObject = jsonArray.getJSONObject(i);

						Menu menu = new Menu();
						menu.setMenuid(getJsonString(menuObject, "id"));
						menu.setName(getJsonString(menuObject, "name"));
						menu.setPrice(Float.parseFloat(getJsonString(
								menuObject, "price")));
						menu.setType(getJsonString(menuObject, "category_id"));
						menu.setMenu_md5(getJsonString(menuObject, "md5"));
						// System.out.println("rebate1:"+Float.parseFloat(getJsonString(menuObject,
						// "rebate")));
						menu.setRebate(Float.parseFloat(getJsonString(
								menuObject, "rebate")));
						menu.setState(getJsonInt(menuObject, "id"));

						menu.setPic(getClearPictrue(getJsonString(menuObject,
								"picture")));
						menu.setDescription(getJsonString(menuObject,
								"description"));
						list.add(menu);

					}

				} else {
					list = null;
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	private static String getClearPictrue(String originalName) {
		String fileName = originalName;
		if (originalName.contains("_thumb")) {
			fileName = originalName
					.substring(0, originalName.indexOf("_thumb")) + ".jpg";
		}
		return fileName;
	}

	/**
	 * 创建订单json数据
	 * 
	 * @param orderlist
	 * @param order
	 * @return
	 */
	public static String createOrder(List<OrderList> orderlist, Order order) {
		StringBuffer myorder = new StringBuffer();
		StringBuffer myolist = new StringBuffer();
		myolist = myolist.append("[");
		int ols = orderlist.size(), i = 0;
		for (OrderList ol : orderlist) {
			myolist = myolist.append("{\"menuid\":\"" + ol.getMenuid() + "\",");
			myolist = myolist.append("\"amount\":\"" + ol.getAmount() + "\",");
			myolist = myolist.append("\"rebate\":\"" + ol.getRebate() + "\",");
			i++;
			if (ols != i) {
				myolist = myolist.append("\"price\":\"" + ol.getPrice()
						+ "\"},");
			} else {
				myolist = myolist
						.append("\"price\":\"" + ol.getPrice() + "\"}");
			}
		}
		myolist = myolist.append("]");
		myorder = myorder.append("{\"products\":" + myolist.toString() + ",");
		myorder = myorder.append("\"totalprice\":\"" + order.getTotalprice()
				+ "\",");
		myorder = myorder.append("\"seatid\":\"" + order.getSeatid() + "\",");
		myorder = myorder.append("\"time\":\"" + order.getTime() + "\",");
		myorder = myorder.append("\"remarks\":\"" + order.getRemarks() + "\",");
		//防止tableflag为空时传输null字符串，增加判断
		if (order.getTableflag()!= null) {
			myorder = myorder.append("\"tableflag\":\"" + order.getTableflag()
					+ "\",");
		}else{
			myorder = myorder.append("\"tableflag\":\"" + ""
					+ "\",");
		}
		myorder = myorder.append("\"waiterid\":\"" + order.getWaiterid()
				+ "\",");
		// myorder = myorder.append("\"dinner_time\":\"" + order.getFoodtime()
		// + "\"}");
		myorder = myorder.append("\"phone\":\"" + order.getPhone() + "\"}");
		System.out.println("myorder:" + myorder.toString());

		return myorder.toString();
	}
}
