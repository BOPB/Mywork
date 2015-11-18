package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function  菜单类别信息
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class MenuCategory {
	private String category_id;//菜单id
	private String category_name;//菜单名
	private String category_description;//菜单描述
	
	
	/**********************************************************
	Function: Enviroment
	Description: 构造函数
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public MenuCategory() {
		super();
	}
	
	/**********************************************************
	Function: Enviroment
	Description: 构造函数
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 环境id，环境值
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public MenuCategory(String category_id, String category_name, String category_picture, 
			String category_picture_pressed, String category_description){
		super();
		this.category_id = category_id;
		this.category_name = category_name;
		this.category_description = category_description;
	}
	

	@Override
	public String toString() {
		return "EnviromentCat [category_id=" + category_id + ", category_name="
				+ category_name + ", category_description="
				+ category_description + "]";
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	public String getCategory_description() {
		return category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
	
}

