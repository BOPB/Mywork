package com.zrh.urestaurantprivate.bean;

import java.io.Serializable;

/**
 * @copyright 中荣恒科技有限公司
 * @function  订单信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
@SuppressWarnings("serial")
public class OrderList implements Serializable {

	private String menuid;// 菜品id
	private String name;// 菜品名
	private int amount;// 菜品数
	private float price;// 菜品单价
	private float rebate;// 菜品折扣
	// 增加详情属性
	private String description;// 货品详情

	/**********************************************************
	 * Function: OrderList Description: 构造函数 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public OrderList() {
		super();
	}

	/**********************************************************
	 * Function: OrderList Description: 构造函数 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 菜品id、菜品名、菜品数、菜品单价、菜品折扣 Output:
	 * 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public OrderList(String menuid, String name, int amount, float price,
			float rebate, String description) {
		super();
		this.menuid = menuid;
		this.name = name;
		this.amount = amount;
		this.price = price;
		this.rebate = rebate;
		this.description = description;
	}

	/**********************************************************
	 * Function: getRebate Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 折扣 Others: 其它说明
	 *********************************************************/
	public float getRebate() {
		return rebate;
	}

	/**********************************************************
	 * Function: setRebate Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 折扣
	 * Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void setRebate(float rebate) {
		this.rebate = rebate;
	}

	/**********************************************************
	 * Function: getMenuid Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 菜品id Others: 其它说明
	 *********************************************************/
	public String getMenuid() {
		return menuid;
	}

	/**********************************************************
	 * Function: getName Description: 函数（方法）功能、性能等的描述 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return:
	 * 姓名 Others: 其它说明
	 *********************************************************/
	public String getName() {
		return name;
	}

	/**********************************************************
	 * Function: setName Description: 函数（方法）功能、性能等的描述 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 姓名 Output: 对输出参数的说明。
	 * Return: 无 Others: 其它说明
	 *********************************************************/
	public void setName(String name) {
		this.name = name;
	}

	/**********************************************************
	 * Function: setMenuid Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input:
	 * 菜品id Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	/**********************************************************
	 * Function: getAmount Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 数量 Others: 其它说明
	 *********************************************************/
	public int getAmount() {
		return amount;
	}

	/**********************************************************
	 * Function: setAmount Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 数量
	 * Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**********************************************************
	 * Function: getPrice Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 单价 Others: 其它说明
	 *********************************************************/
	public float getPrice() {
		return price;
	}

	/**********************************************************
	 * Function: setPrice Description: 函数（方法）功能、性能等的描述 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 单价
	 * Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**********************************************************
	 * Function: toString Description: 输出字符串方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	@Override
	public String toString() {
		return menuid + ":" + name + ": " + amount + "份￥" + price + " "
				+ rebate + "折" + "详情：" + description;
	}

}
