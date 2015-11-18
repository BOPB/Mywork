package com.zrh.urestaurantprivate.httputils;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @版权: 2015, 深圳市中荣恒科技有限公司 zonroh.com
 * @功能: Map参数传入
 * @作者: 李文华
 * @版本: v1.2
 * @日期: 2015-3-6下午5:26:32 
 * @修改日期:
 * @修改人:
 * @修改内容简述:
 */
public class RequestData {

	private Map<String, String> datas;
	
	public RequestData(){
		datas = new HashMap<String, String>();
	}
	
	
	public static RequestData getInstance(){
		return new RequestData();
	}
	
	
	public RequestData put(String key,String value){
		datas.put(key, value);
		return this;
	}
	public Map<String, String> getParamMap(){
		return datas;
	}
}
