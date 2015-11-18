package com.zrh.urestaurantprivate.umenu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * @copyright 中荣恒科技有限公司
 * @function 关于U餐界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 * 
 */
public class OrderDealFour extends Activity implements OnClickListener {
	private TextView tv_back;
	private TextView tv_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_four);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_version = (TextView) findViewById(R.id.tv_version);
		try {
			String version = getVersionName();
			tv_version.setText(version);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// Intent intent = new Intent(OrderDealFour.this, OrderActivity.class);
		// startActivity(intent);
		finish();
	}
/**
 * 获取当前应用版本信息
 * @return
 * @throws Exception
 */
	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}
}
