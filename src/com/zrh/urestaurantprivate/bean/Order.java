package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function  下订订单信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class Order {
	private String seatid;// 座位ID
	private String totalprice;// 总价
	private String remarks; // 订单人名、备注
	private String tableflag;// 座位标记, 拆台之后下单A桌或B桌, 不拆台不用传, 大写字母’A’或’B’
	private String waiterid;// 服务员ID(工号)
	private String time;// 下单时间
	private String foodtime;// 用餐时间
    private String phone;//电话号码
    
    /**********************************************************
	 * Function: cleanAll Description: 清除所有 Calls: 被本函数（方法）调用的函数（方法

）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵

扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无 Output: 对输出

参数的说明。 Return: 无
	 * Others: 其它说明
	 *********************************************************/
	public void cleanAll() {
		seatid = null;
		totalprice = null;
		remarks = null;
		phone = null;
		seatid = null;
		tableflag = null;
		waiterid = null;
		time = null;
		foodtime = null;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String seatid, String totalprice, String remarks,
			String tableflag, String waiterid, String time, String foodtime,
			String phone) {
		super();
		this.seatid = seatid;
		this.totalprice = totalprice;
		this.remarks = remarks;
		this.tableflag = tableflag;
		this.waiterid = waiterid;
		this.time = time;
		this.foodtime = foodtime;
		this.phone = phone;
	}
	public String getSeatid() {
		return seatid;
	}
	public void setSeatid(String seatid) {
		this.seatid = seatid;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTableflag() {
		return tableflag;
	}
	public void setTableflag(String tableflag) {
		this.tableflag = tableflag;
	}
	public String getWaiterid() {
		return waiterid;
	}
	public void setWaiterid(String waiterid) {
		this.waiterid = waiterid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFoodtime() {
		return foodtime;
	}
	public void setFoodtime(String foodtime) {
		this.foodtime = foodtime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Order [seatid=" + seatid + ", totalprice=" + totalprice
				+ ", remarks=" + remarks + ", tableflag=" + tableflag
				+ ", waiterid=" + waiterid + ", time=" + time + ", foodtime="
				+ foodtime + ", phone=" + phone + "]";
	}
	

}
