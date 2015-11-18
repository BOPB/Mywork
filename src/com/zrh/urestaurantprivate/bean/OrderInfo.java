package com.zrh.urestaurantprivate.bean;

import java.io.Serializable;

/**
 * @copyright 中荣恒科技有限公司
 * @function  查询的订单信息类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class OrderInfo  implements Serializable{
	private int order_status;// 订单状态，0预定订单,1待处理订单,2异常订单
	private int pay_status;// 支付状态，0未付款，1已付款
	private int pay_ways;// 支付方式，0餐到付款，1预付款付款，2支付宝付款
	private double orderTotalPrice;// 订单总价
	private double original_price;// 原价
	private double total_discounts;// 优惠总价
	private String orderTiem;// 订单时间
	private String pay_time;// 支付时间
	private String dinner_time;// 就餐时间

	private String table_id;// 桌位id
	private String customer_id;// 用户id
	private String waiter_id;// 服务员工号
	
	private String orderNo;// 订单号
	private String phone;// 手机号
	private String name;// 姓名
	private String remarks;// 备注
	private String identify_code;// 验证码

	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderInfo(String orderNo, double orderTotalPrice, String orderTiem,
			int order_status, String customer_id, double original_price,
			int pay_status, int pay_ways, String waiter_id, String phone,
			String name, String remarks, String pay_time, String identify_code,
			String table_id, String dinner_time, double total_discounts) {
		super();
		this.orderNo = orderNo;
		this.orderTotalPrice = orderTotalPrice;
		this.orderTiem = orderTiem;
		this.order_status = order_status;
		this.customer_id = customer_id;
		this.original_price = original_price;
		this.pay_status = pay_status;
		this.pay_ways = pay_ways;
		this.waiter_id = waiter_id;
		this.phone = phone;
		this.name = name;
		this.remarks = remarks;
		this.pay_time = pay_time;
		this.identify_code = identify_code;
		this.table_id = table_id;
		this.dinner_time = dinner_time;
		this.total_discounts = total_discounts;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public double getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getOrderTiem() {
		return orderTiem;
	}

	public void setOrderTiem(String orderTiem) {
		this.orderTiem = orderTiem;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}

	public int getPay_status() {
		return pay_status;
	}

	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	public int getPay_ways() {
		return pay_ways;
	}

	public void setPay_ways(int pay_ways) {
		this.pay_ways = pay_ways;
	}

	public String getWaiter_id() {
		return waiter_id;
	}

	public void setWaiter_id(String waiter_id) {
		this.waiter_id = waiter_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getIdentify_code() {
		return identify_code;
	}

	public void setIdentify_code(String identify_code) {
		this.identify_code = identify_code;
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public String getDinner_time() {
		return dinner_time;
	}

	public void setDinner_time(String dinner_time) {
		this.dinner_time = dinner_time;
	}

	public double getTotal_discounts() {
		return total_discounts;
	}

	public void setTotal_discounts(double total_discounts) {
		this.total_discounts = total_discounts;
	}

}
