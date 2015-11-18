package com.zrh.urestaurantprivate.util;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zrh.urestaurantprivate.umenu.MyApplication;
import com.zrh.urestaurantprivate.umenu.R;
import com.zrh.urestaurantprivate.umenu.TableInfoActivity;
import com.zrh.urestaurantprivate.umenu.Upagetwo;
/**
 * @copyright 中荣恒科技有限公司
 * @function 对话框选择，选择A或B桌台下单
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class ChoiceDialog extends Dialog implements OnClickListener {

	private MyApplication application;
	private TextView choice_a;
	private TextView choice_b;
	private Toast mToast;

	Context context;

	public ChoiceDialog(Context context) {
		super(context);
		this.context = context;
	}

	public ChoiceDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.choice);
		application = (MyApplication) context.getApplicationContext();
		initView();
	}

	// 初始化
	private void initView() {
		choice_a=(TextView)findViewById(R.id.choice_a);
		choice_b=(TextView)findViewById(R.id.choice_b);
		choice_a.setOnClickListener(this);
		choice_b.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v == choice_a) {
			showToast(context, "您选择了A桌台,现在您可以点菜下单了", Toast.LENGTH_SHORT);
			application.order.setTableflag("A");
			dismiss();
			intent.setClass(context, Upagetwo.class);
			context.startActivity(intent);
		}
		if (v == choice_b) {// 确定
			application.order.setTableflag("B");
			showToast(context, "您选择了B桌台,现在您可以点菜下单了", Toast.LENGTH_SHORT);
			dismiss();
			intent.setClass(context, Upagetwo.class);
			context.startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		dismiss();
	}

	/**********************************************************
	 * Function: showToast Description: 自定义系统提示 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input:
	 * context：当前环境，msg：提示消息，showtime：提示时间 Output: 对输出参数的说明。 Return: 无 Others:
	 * 其它说明
	 *********************************************************/
	// 不让Toast多显示长时间
	private void showToast(Context context, String msg, int showtime) {
		// 实例化一个Toast对象
		if (mToast != null) {
			mToast.setText(msg);
		} else {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		mToast.show();

	}

}
