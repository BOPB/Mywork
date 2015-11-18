package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function 餐厅信息类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class Restaurant {

	private String android_app_qrcode;//安卓二维码
	private String ios_app_qrcode;//ios二维码
	private String shop_name;//餐厅名字
	private int business_hours_begin;//餐厅开始营业时间
	private int business_hours_end;//餐厅结束营业时间
	private String shop_phone;//餐厅电话
	private String shop_address;//餐厅地址
	private String host_domain;//餐厅官网
	private int order_time_limit;//限制订单天数
	private int order_times_perday_limit;//限制订单次数
	private int discount_num_perorder_limit;//折扣菜限定次数
	
	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Restaurant(String android_app_qrcode, String ios_app_qrcode,
			String shop_name, int business_hours_begin, int business_hours_end,
			String shop_phone, String shop_address, String host_domain,
			int order_time_limit, int order_times_perday_limit,int discount_num_perorder_limit) {
		super();
		this.android_app_qrcode = android_app_qrcode;
		this.ios_app_qrcode = ios_app_qrcode;
		this.shop_name = shop_name;
		this.business_hours_begin = business_hours_begin;
		this.business_hours_end = business_hours_end;
		this.shop_phone = shop_phone;
		this.shop_address = shop_address;
		this.host_domain = host_domain;
		this.order_time_limit = order_time_limit;
		this.order_times_perday_limit = order_times_perday_limit;
		this.discount_num_perorder_limit = discount_num_perorder_limit;
	}

	public String getAndroid_app_qrcode() {
		return android_app_qrcode;
	}

	public void setAndroid_app_qrcode(String android_app_qrcode) {
		this.android_app_qrcode = android_app_qrcode;
	}

	public String getIos_app_qrcode() {
		return ios_app_qrcode;
	}

	public void setIos_app_qrcode(String ios_app_qrcode) {
		this.ios_app_qrcode = ios_app_qrcode;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public int getBusiness_hours_begin() {
		return business_hours_begin;
	}

	public void setBusiness_hours_begin(int business_hours_begin) {
		this.business_hours_begin = business_hours_begin;
	}

	public int getBusiness_hours_end() {
		return business_hours_end;
	}

	public void setBusiness_hours_end(int business_hours_end) {
		this.business_hours_end = business_hours_end;
	}

	public String getShop_phone() {
		return shop_phone;
	}

	public void setShop_phone(String shop_phone) {
		this.shop_phone = shop_phone;
	}

	public String getShop_address() {
		return shop_address;
	}

	public void setShop_address(String shop_address) {
		this.shop_address = shop_address;
	}

	public String getHost_domain() {
		return host_domain;
	}

	public void setHost_domain(String host_domain) {
		this.host_domain = host_domain;
	}

	public int getOrder_time_limit() {
		return order_time_limit;
	}

	public void setOrder_time_limit(int order_time_limit) {
		this.order_time_limit = order_time_limit;
	}

	public int getOrder_times_perday_limit() {
		return order_times_perday_limit;
	}

	public void setOrder_times_perday_limit(int order_times_perday_limit) {
		this.order_times_perday_limit = order_times_perday_limit;
	}

	
	public int getDiscount_num_perorder_limit() {
		return discount_num_perorder_limit;
	}

	public void setDiscount_num_perorder_limit(int discount_num_perorder_limit) {
		this.discount_num_perorder_limit = discount_num_perorder_limit;
	}

	@Override
	public String toString() {
		return "Restaurant [android_app_qrcode=" + android_app_qrcode
				+ ", ios_app_qrcode=" + ios_app_qrcode + ", shop_name="
				+ shop_name + ", business_hours_begin=" + business_hours_begin
				+ ", business_hours_end=" + business_hours_end
				+ ", shop_phone=" + shop_phone + ", shop_address="
				+ shop_address + ", host_domain=" + host_domain
				+ ", order_time_limit=" + order_time_limit
				+ ", order_times_perday_limit=" + order_times_perday_limit
				+ ",discount_num_perorder_limit="+discount_num_perorder_limit+"]";
	}
	
	
	
	


}
