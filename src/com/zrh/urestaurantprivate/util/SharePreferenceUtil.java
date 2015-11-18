package com.zrh.urestaurantprivate.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * @copyright 中荣恒科技有限公司
 * @function 保存简单数据类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
@SuppressLint("CommitPrefEdits")
public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setSession(String session) {
		editor.putString("SESSION", session);
		editor.commit();
	}

	public String getSession() {
		return sp.getString("SESSION", "");
	}

	public void setProblem(int pro) {
		editor.putInt("PROBLEM", pro);
		editor.commit();
	}

	public int getProblem() {
		return sp.getInt("PROBLEM", 1);
	}

	// 锁屏时间存取
	public void setOuttime(int outtime) {
		editor.putInt("OUTTIME", outtime);
		editor.commit();
	}

	public int getOuttime() {
		return sp.getInt("OUTTIME", -1);
	}

	public void setCtxtime(int ctxtime) {
		editor.putInt("CTXTIME", ctxtime);
		editor.commit();
	}

	public int getCtxtime() {
		return sp.getInt("CTXTIME", 0);
	}

	// 密码存取
	public void setPasswd(String pswd) {
		editor.putString("PASSWORD", pswd);
		editor.commit();
	}

	public String getPasswd() {
		return sp.getString("PASSWORD", "");
	}

	// 欢迎词存取
	public void setWelcomWord(String welcomWord) {
		editor.putString("WELCOMWORD", welcomWord);
		editor.commit();
	}

	public String getWelcomWord() {
		return sp.getString("WELCOMWORD", "");
	}

	// 第一次使用apk状态存取
	public void setIsFirst(boolean IsFirst) {
		editor.putBoolean("ISFIRST", IsFirst);
		editor.commit();
	}

	public boolean getIsFirst() {
		return sp.getBoolean("ISFIRST", true);
	}

	// 环境图片改变状态存取
	public void setMent(int changement) {
		editor.putInt("MENTCHNG", changement);
		editor.commit();
	}

	public int getMent() {
		return sp.getInt("MENTCHNG", 0);
	}

	// 用户id存取
	public void setCustomid(String customid) {
		editor.putString("CUSTOMID", customid);
		editor.commit();
	}

	public String getCustomid() {
		return sp.getString("CUSTOMID", "");
	}

	// 用户电话存取
	public void setPhone(String phone) {
		editor.putString("phone", phone);
		editor.commit();
	}

	public String getPhone() {
		return sp.getString("phone", "");
	}

	// 用户userid存取
	public void setUserid(String userid) {
		editor.putString("userid", userid);
		editor.commit();
	}

	public String getUserid() {
		return sp.getString("userid", "");
	}

	// 用户login_name存取
	public void setLogin_name(String login_name) {
		editor.putString("login_name", login_name);
		editor.commit();
	}

	public String getLogin_name() {
		return sp.getString("login_name", "");
	}

	// 用户name存取
	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	public String getName() {
		return sp.getString("name", "");
	}

	// 启动锁状态存取
	public void setStartPass(boolean startpass) {
		editor.putBoolean("STARTPASS", startpass);
		editor.commit();
	}

	public boolean getStartPass() {
		return sp.getBoolean("STARTPASS", false);
	}

	// 预定前锁状态存取
	public void setOrderPass(boolean orderpass) {
		editor.putBoolean("ORDERPASS", orderpass);
		editor.commit();
	}

	public boolean getOrderPass() {
		return sp.getBoolean("ORDERPASS", false);
	}

	// 支付前锁状态存取
	public void setPayPass(boolean paypass) {
		editor.putBoolean("PAYPASS", paypass);
		editor.commit();
	}

	public boolean getPayPass() {
		return sp.getBoolean("PAYPASS", false);
	}

	// 吃饭时间提醒状态存取
	public void setEatPass(boolean eatpass) {
		editor.putBoolean("EATPASS", eatpass);
		editor.commit();
	}

	public boolean getEatPass() {
		return sp.getBoolean("EATPASS", false);
	}

	// 提醒时间存取
	public void setTxtime(String time) {
		editor.putString("TXTIME", time);
		editor.commit();
	}

	public String getTxtime() {
		return sp.getString("TXTIME", "");
	}

	// 提醒备注存取
	public void setMessage(String message) {
		editor.putString("TXMESSAGE", message);
		editor.commit();
	}

	public String getMessage() {
		return sp.getString("TXMESSAGE", "");
	}

	// 菜品改变状态存取
	public void setMenuChange(int menuchange) {
		editor.putInt("MENUCHANGE", menuchange);
		editor.commit();
	}

	public int getMenuChange() {
		return sp.getInt("MENUCHANGE", 0);
	}

	// 在线状态存取
	public void setIsonline(boolean isonline) {
		editor.putBoolean("ISONLINE", isonline);
		editor.commit();
	}

	public boolean getIsonline() {
		return sp.getBoolean("ISONLINE", false);
	}

	// 提醒备注存取
	public void setYhqn(String message) {
		editor.putString("YHMESSAGE", message);
		editor.commit();
	}

	public String getYhqn() {
		return sp.getString("YHMESSAGE", "");
	}

	// 提醒备注存取
	public void setType(int type) {
		editor.putInt("TYPE", type);
		editor.commit();
	}

	public int getType() {
		return sp.getInt("TYPE", 1);
	}

	// 登陆密码保存存取
	public void setCheck(boolean ischeck) {
		editor.putBoolean("CHECK", ischeck);
		editor.commit();
	}

	public boolean getCheck() {
		return sp.getBoolean("CHECK", false);
	}

	// 是否从注册页面跳转到welcome页面状态存取
	public void setIsFromRegister(boolean isfrom) {
		editor.putBoolean("FROM", isfrom);
		editor.commit();
	}

	public boolean getIsFromRegister() {
		return sp.getBoolean("FROM", false);
	}

	// 获取本机订单数据
	public void setAppID(String appid) {
		editor.putString("appid", appid);
		editor.commit();
	}

	public String getAppID() {
		return sp.getString("appid", "");
	}

	// 获取授权md5
	public void setValidation(String validation) {
		editor.putString("validation", validation);
		editor.commit();
	}

	public String getValidation() {
		return sp.getString("validation", "");
	}

	// 餐厅环境是否更新
	public void setIsEnvUpdate(long change) {
		editor.putLong("validation", change);
		editor.commit();
	}

	public long getIsEnvUpdate() {
		return sp.getLong("validation", 0);
	}
}
