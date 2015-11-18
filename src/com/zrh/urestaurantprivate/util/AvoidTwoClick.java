package com.zrh.urestaurantprivate.util;

import java.util.Timer;
import java.util.TimerTask;
/**
 * @copyright 中荣恒科技有限公司
 * @function 避免两次重复点击
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class AvoidTwoClick {
	public static boolean canClick = true;

	public static void avoidTwoClick() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				canClick = true;
			}
		}, 1000);
	}
}
