package com.zrh.urestaurantprivate.entitydb;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zrh.urestaurantprivate.bean.Menu;
/**
 * @copyright 中荣恒科技有限公司
 * @function  菜品信息数据库
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class MenuDB {
	private DBHelper helper;
	private SQLiteDatabase db;

	public MenuDB(Context context) {
		helper = new DBHelper(context,true);
		db = helper.getWritableDatabase();
		helper.onCreate(db);
	}

	/**********************************************************
	Function: selectInfo
	Description: 查找信息
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 查找关键字
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public Menu selectInfo(String name) {
		Menu m = new Menu();
		Cursor c = db.rawQuery("select * from menu where name=?",
				new String[] { name });//phone + ""
		if (c.moveToFirst()) {
			m.setName(c.getString(c.getColumnIndex("name")));
		}
		c.close();
		return m;
	}
	
	/**********************************************************
	Function: selectPrice
	Description: 查找价格
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 菜品
	Output:  对输出参数的说明。
	Return: 菜品对应价格
	Others:  其它说明
	*********************************************************/
	public List<Float> selectPrice(List<String> ns) {
		List<Float> pricelist = new ArrayList<Float>();
		for(int i=0;i<ns.size();i++){
			Cursor c = db.rawQuery("select price,rebate from menu where name=?",
					new String[] { ns.get(i) });
			if (c.moveToFirst()) {
				Float p = Float.parseFloat(c.getString(c.getColumnIndex("price")))*Float.parseFloat(c.getString(c.getColumnIndex("rebate")))/10;
				pricelist.add(new Float(p));
			}
			c.close();
		}
		return pricelist;
	}

	/**********************************************************
	Function: addMenu
	Description: 添加菜单
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By: 调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 菜单信息
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public void addMenu(List<Menu> list) {
		for (Menu m : list) {
			db.execSQL("insert into menu (id,name,menu_md5,price,type,rebate,state,pic,description) " +"values(?,?,?,?,?,?,?,?,?)",
					new Object[] { m.getMenuid(), m.getName(),m.getMenu_md5(),m.getPrice(),m.getType(),m.getRebate(),m.getState(),m.getPic(),m.getDescription()});
		}
	}

	/**********************************************************
	Function: updateUser
	Description: 更新信息
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 菜品信息
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public void updateUser(List<Menu> list) {
		if (list.size() > 0) {
			delete();
			addMenu(list);
		}
	}

	/**********************************************************
	Function: getMenu
	Description: 获取菜品信息
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 菜品类别
	Output:  对输出参数的说明。
	Return: 菜品信息
	Others:  其它说明
	*********************************************************/
	public List<Menu> getMenu(String type) {
		List<Menu> list = new ArrayList<Menu>();
//		Cursor c = db.rawQuery("select id,name,price,rebate,state,pic from menu", null);
		Cursor c = db.rawQuery("select id,name,menu_md5,price,rebate,state,pic,description from menu where type='"+type+"'", null);
		while (c.moveToNext()) {
			Menu m = new Menu();
			m.setMenuid(c.getString(c.getColumnIndex("id")));
			m.setName(c.getString(c.getColumnIndex("name")));
			m.setMenu_md5(c.getString(c.getColumnIndex("menu_md5")));
			m.setPrice(c.getFloat(c.getColumnIndex("price")));
			m.setRebate(c.getFloat(c.getColumnIndex("rebate")));
			m.setState(c.getInt(c.getColumnIndex("state")));
			m.setPic(c.getString(c.getColumnIndex("pic")));
			m.setDescription(c.getString(c.getColumnIndex("description")));
			list.add(m);
		}
		c.close();
		return list;
	}
	
	public List<Menu> getAllMenu() {
		List<Menu> list = new ArrayList<Menu>();
//		Cursor c = db.rawQuery("select id,name,price,rebate,state,pic from menu", null);
		Cursor c = db.rawQuery("select id,name,menu_md5,price,rebate,state,pic,description from menu ", null);
		while (c.moveToNext()) {
			Menu m = new Menu();
			m.setMenuid(c.getString(c.getColumnIndex("id")));
			m.setName(c.getString(c.getColumnIndex("name")));
			m.setMenu_md5(c.getString(c.getColumnIndex("menu_md5")));
			m.setPrice(c.getFloat(c.getColumnIndex("price")));
			m.setRebate(c.getFloat(c.getColumnIndex("rebate")));
			m.setState(c.getInt(c.getColumnIndex("state")));
			m.setPic(c.getString(c.getColumnIndex("pic")));
			m.setDescription(c.getString(c.getColumnIndex("description")));
			list.add(m);
		}
		c.close();
		return list;
	}
	//查询所有货品类型id
	public String getGoodsType_id(int id) {
		Cursor c = db.rawQuery("select type from menu where id='" + id + "'", null);
		String type_id = null;
		while (c.moveToNext()) {
			type_id = c.getString(c.getColumnIndex("type"));
		}
		c.close();
		return type_id;
	}
	/**********************************************************
	Function: delete
	Description: 删除菜品信息
	Calls:  被本函数（方法）调用的函数（方法）清单
	Called By:  调用本函数（方法）的函数（方法）清单
	Table Accessed:  被访问的表（此项仅对于牵扯到数据库操作的程序）
	Table Updated:  被修改的表（此项仅对于牵扯到数据库操作的程序）
	Input: 无
	Output:  对输出参数的说明。
	Return: 无
	Others:  其它说明
	*********************************************************/
	public void delete() {
		db.execSQL("delete from menu");
	}
	/*******************************************************
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}
}
