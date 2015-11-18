package com.zrh.urestaurantprivate.httputils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
/**
 * @copyright 中荣恒科技有限公司
 * @function 常量类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class MyConstants {

//	public static String HTTP="http://192.168.1.119/i.ubuybserv1.ufood.biz/";
//	public static String HTTP="http://ubuyyunint.testing.ufood.biz/";
//	public static String HTTP="http://192.168.1.134/test/localint/";
//	public static String HTTP="http://ubuyinterface.demo.ufood.biz/";
//	public static String HTTP="http://192.168.1.239/demo/interface/";
	public static String HTTP="http://192.168.1.239/testing/interface/";
//	public static String HTTP="http://192.168.1.103/ubuy/1.2/interface/";
//	public static String HTTP = "http://192.168.1.103/ubuy/1.2/interface/";
//	public static String VALIDATION_HTTP = "http://ubuy.dev.auth.ufood.biz/interface/auth/";
	// public static String STAND_HTTP =
	// "http://192.168.1.119/i.ubuybserv1.ufood.biz/";

	// public static final String HTTP =
	// "http://192.168.1.119/i.branch.uft.ufood.biz/";
	// public static final String HTTP =
	// "http://ubuy11main-interface.ufood.biz/";
	// public static final String HTTP =
	// "http://192.168.1.119/i.bserv1.ufood.biz";
	// public static final String HTTP = "http://int.online.ufood.biz/";
	public static final String HTTP2 = "http://svr.ufood.biz/";// 服务器url
	public static final String ACTION = "com.zrh.message";// 消息广播action
	public static final String MSGKEY = "message";// 消息的key
	public static final String SAVE_USER = "saveUser";// 保存用户信息的xml文件名
	public static final String BACKKEY_ACTION = "com.zrh.backKey";// 返回键发送广播的action
	public static final String DBNAME = "umenuPrivate.db";// 数据库名称
	public static final int NOTIFY_ID = 0x911;// 通知ID
	public static final String ROLEID = "1003";//送餐员角色标识符
	// 图片目录
	public static final String D_DIR = "/UmenuPrivate/downloads/";
	public static final String DBPATH = "/UmenuPrivate/";
	public static final String D_IMG_DIR = "/UmenuPrivate/downloads/umenu/images/";
	public static final String D_IMG_DIR_ENV = "/UmenuPrivate/downloads/umenu/images/env/";
	public static final String D_IMG_DIR_MENU_CAT = "/UmenuPrivate/downloads/umenu/images/menu/category/";
	public static final String D_IMG_DIR_MENU = "/UmenuPrivate/downloads/umenu/images/menu/";
	public static final String D_IMG_DIR_LOG = "/UmenuPrivate/downloads/umenu/images/log/";
	private static boolean m_bIsCatDownload = false;// 是否已经下载菜品类信息

	public static boolean isCatDownload() {
		return m_bIsCatDownload;
	}

	public static void setCatIsDownload() {
		m_bIsCatDownload = true;
	}

	/**
	 * 文件是否存在
	 * 
	 * @param strType
	 * @param strUrl
	 * @return
	 */
	public static boolean isExistsFile(String strType, String strUrl) {
		boolean bExists = false;
		String strDir = "";
		if (strType.equals("download"))
			strDir = D_DIR;
		else if (strType.equals("images"))
			strDir = D_IMG_DIR;
		else if (strType.equals("env"))
			strDir = D_IMG_DIR_ENV;
		else if (strType.equals("cat"))
			strDir = D_IMG_DIR_MENU_CAT;
		else if (strType.equals("menu"))
			strDir = D_IMG_DIR_MENU;

		if (strDir.equals("")) {
			return false;
		}

		String fileName = strUrl.substring(strUrl.lastIndexOf("/") + 1);

		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String path = sdcardDir.getPath() + strDir;
		File file = new File(path, fileName);
		if (file.exists()) {
			// 若不存在，创建目录，可以在应用启动的时候创建
			bExists = true;
		}

		return bExists;
	}

}
