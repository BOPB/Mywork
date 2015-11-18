package com.zrh.urestaurantprivate.bean;

import java.io.Serializable;
/**
 * @copyright 中荣恒科技有限公司
 * @function  菜品详情数据
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class MenuDetails implements Serializable{

	private String name;//菜名
	private String  id;//
	private String order_id;//订单号
	private String menu_id;//菜品id
	private int amount;//数量
	private double price;//单价
	private double discount;//折扣
	
	public MenuDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MenuDetails(String name, String id, String order_id, String menu_id,
			int amount, double price, double discount) {
		super();
		this.name = name;
		this.id = id;
		this.order_id = order_id;
		this.menu_id = menu_id;
		this.amount = amount;
		this.price = price;
		this.discount = discount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
