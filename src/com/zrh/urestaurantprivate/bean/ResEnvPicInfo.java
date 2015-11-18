package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function  餐厅环境图片信息
 * @author 吴强
 * @version v1.1
 * @data 2015-01-31
 * 
 */
public class ResEnvPicInfo {
	private String hash;//图片编码
	private String enviromentid;// 图片id
	private String pic;// 图片地址
	// 图片md5是否有变化，用于比对，0 - MD5值不变，不需要下载，1 - MD5值有变化，需要下载，2 - 新增加
	private int changeStatus = 0;
	
	
	/**********************************************************
	  Function: Enviroment 
	  Description: 构造函数 
	  Calls:  被本函数（方法）调用的函数（方法）清单
	  Called By:  调用本函数（方法）的函数（方法）清单 
	  Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） 
	  Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序） 
	  Input: 无 
	  Output:  对输出参数的说明。 
	  Return: 无
	  Others:  其它说明
	 *********************************************************/
	public ResEnvPicInfo() {
		super();
	}

	/**********************************************************
	  Function: Enviroment 
	  Description: 构造函数 
	  Calls:  被本函数（方法）调用的函数（方法）清单
	  Called By:  调用本函数（方法）的函数（方法）清单 
	  Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） 
	  Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	   Input:环境id，环境值 
	   Output:  对输出参数的说明。 
	   Return: 无 
	   Others:  其它说明
	 *********************************************************/
	public ResEnvPicInfo(String pic, String hash,String enviromentid) {
		super();
		this.hash = hash;
		this.enviromentid = enviromentid;
		this.pic = pic;
		
	}
	
	public void setChangedStatus(int changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public int getChangedStatus() {
		return changeStatus;
	}

	/**********************************************************
	Function: getEnviromentid
	Description: get方法
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 环境id
	Output: 对输出参数的说明。
	Return: 无
	Others:  其它说明
	 *********************************************************/
	public String getEnviromentid() {
		return enviromentid;
	}

	/**********************************************************
	 Function: setEnviromentid
	Description: set方法
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By: 调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 环境id
	Others:  其它说明
	 *********************************************************/
	public void setEnviromentid(String enviromentid) {
		this.enviromentid = enviromentid;
	}

	/**********************************************************
	 Function: getPic
	Description: get方法
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 环境值
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	 *********************************************************/
	public String getPic() {
		return pic;
	}

	/**********************************************************
	 Function: setPic
	Description: set方法
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 环境值
	Others:  其它说明
	 *********************************************************/
	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	


	/**********************************************************
	Function: toString
	Description: 输出字符串方法
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	
	public String toString() {
		return "Enviroment [hash=" + hash + ", enviromentid=" + enviromentid
				+ ", pic=" + pic + "]";
	}
}
