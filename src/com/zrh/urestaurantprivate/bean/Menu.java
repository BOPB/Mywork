package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function  菜单类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class Menu {
	private String menuid;// 菜单id
	private String name; // 菜单名
	private String menu_md5;// MD5
	private float price;// 菜单价格
	private String type;// 菜单类型
	private String pic;// 图片地址
	private float rebate;// 折扣
	private int state;// 菜单状态
	// 增加详情属性
	private String description;// 详情

	// 图片md5是否有变化，用于比对，0 - MD5值不变，不需要下载，1 - MD5值有变化，需要下载，2 - 新增加
	private int changeStatus = 0;

	/**********************************************************
	 * Function: Menu Description: 构造函数 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public Menu() {
		super();
	}

	/**********************************************************
	 * Function: Menu Description: 构造函数 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 菜单id，姓名，单价，类型，图片，折扣，状态，改变次数 Output:
	 * 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public Menu(String menuid, String name, String menu_md5, float price,
			String type, String pic, float rebate, int state, String description) {
		super();
		this.menuid = menuid;
		this.name = name;
		this.menu_md5 = menu_md5;
		this.price = price;
		this.type = type;
		this.pic = pic;
		this.rebate = rebate;
		this.state = state;
		this.description = description;
	}

	public void setChangedStatus(int changeStatus) {
		this.changeStatus = changeStatus;
	}

	public int getChangedStatus() {
		return changeStatus;
	}

	/**********************************************************
	 * Function: getMenuid Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 菜单id Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public String getMenuid() {
		return menuid;
	}

	/**********************************************************
	 * Function: setMenuid Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 菜单id
	 * Others: 其它说明
	 *********************************************************/
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	/**********************************************************
	 * Function: getName Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 姓名 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public String getName() {
		return name;
	}

	/**********************************************************
	 * Function: setName Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 姓名
	 * Others: 其它说明
	 *********************************************************/
	public void setName(String name) {
		this.name = name;
	}

	/**********************************************************
	 * Function: getPrice Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 单价 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public float getPrice() {
		return price;
	}

	/**********************************************************
	 * Function: setPrice Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 单价
	 * Others: 其它说明
	 *********************************************************/
	public void setPrice(float price) {
		this.price = price;
	}

	/**********************************************************
	 * Function: getType Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 类型 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public String getType() {
		return type;
	}

	/**********************************************************
	 * Function: setType Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 类型
	 * Others: 其它说明
	 *********************************************************/
	public void setType(String string) {
		this.type = string;
	}

	/**********************************************************
	 * Function: getPic Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 图片 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	public String getPic() {
		return pic;
	}

	/**********************************************************
	 * Function: setPic Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called By:
	 * 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated:
	 * 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 图片 Others:
	 * 其它说明
	 *********************************************************/
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**********************************************************
	 * Function: getRebate Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 折扣 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public float getRebate() {
		return rebate;
	}

	/**********************************************************
	 * Function: setRebate Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 折扣
	 * Others: 其它说明
	 *********************************************************/
	public void setRebate(float rebate) {
		this.rebate = rebate;
	}

	/**********************************************************
	 * Function: getState Description: get方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 状态 Output: 对输出参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public int getState() {
		return state;
	}

	/**********************************************************
	 * Function: setState Description: set方法 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出参数的说明。 Return: 状态
	 * Others: 其它说明
	 *********************************************************/
	public void setState(int state) {
		this.state = state;
	}

	public String getMenu_md5() {
		return menu_md5;
	}

	public void setMenu_md5(String menu_md5) {
		this.menu_md5 = menu_md5;
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
		return "Menu [menuid=" + menuid + ", name=" + name + ", menu_md5="
				+ menu_md5 + ", price=" + price + ", type=" + type + ", pic="
				+ pic + ", rebate=" + rebate + ", state=" + state
				+ ",description=" + description + "]";
	}
}
