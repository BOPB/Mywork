package com.zrh.urestaurantprivate.util;
import com.zrh.urestaurantprivate.umenu.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * @copyright 中荣恒科技有限公司
 * @function 对话框工厂
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class DialogFactory {

	/**
	 * 旋转框
	 * 
	 * @param context
	 * @param tip
	 * @return
	 */
	public static Dialog creatRequestDialog(final Context context, String tip) {

		final Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.dialog_layout);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		int width = Utils.getScreenWidth(context);
		lp.width = (int) (0.6 * width);
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
		if (tip == null || tip.length() == 0) {
			titleTxtv.setText(R.string.sending_request);
		} else {
			titleTxtv.setText(tip);
		}
		return dialog;
	}

	/**
	 * 旋转框
	 * 
	 * @param context
	 * @param tip
	 * @return
	 */
	public static void ToastDialog(Context context, String title, String msg) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(msg).setPositiveButton("确定", null).create();
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
	}
}
