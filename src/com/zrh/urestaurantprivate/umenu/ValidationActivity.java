package com.zrh.urestaurantprivate.umenu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.zrh.urestaurantprivate.httputils.MyConstants;
import com.zrh.urestaurantprivate.httputils.HttpUtils;
import com.zrh.urestaurantprivate.httputils.JsonTools;
import com.zrh.urestaurantprivate.util.DialogFactory;
import com.zrh.urestaurantprivate.util.MD5;
import com.zrh.urestaurantprivate.util.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @copyright 中荣恒科技有限公司
 * @function 授权认证界面
 * @author 吴强
 * @version v1.1
 * @date 2015-02-04
 */
public class ValidationActivity extends Activity implements OnClickListener {
	private Button btn_sure;
	private Button btn_cancel;
	private EditText et_validation;
	private EditText et_goodsNum;
	private SharePreferenceUtil sp;
	private Dialog mDialog = null;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x110) {
				Log.e("Test", "42 is running ");
				setData();
				closeReflashDialog();
				Intent intent = new Intent(ValidationActivity.this,
						WelcomeActivity.class);
				startActivity(intent);
				ValidationActivity.this.finish();
			} else if (msg.what == 0x123) {
				Log.e("Test", "50 is running ");
				closeReflashDialog();
				Toast.makeText(ValidationActivity.this, description,
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 0x111) {
				Log.e("Test", "55 is running ");
				closeReflashDialog();
				Toast.makeText(ValidationActivity.this, "获取数据失败，请检查网络",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 0x112) {
				Log.e("Test", "58 is running ");
				closeReflashDialog();
				Toast.makeText(ValidationActivity.this, "服务器数据错误",
						Toast.LENGTH_LONG).show();
			}
		};
	};
	private String data;
	private String description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		sp = new SharePreferenceUtil(this, MyConstants.SAVE_USER);
		if (!sp.getValidation().equals("")) {
			Intent intent = new Intent(ValidationActivity.this,
					WelcomeActivity.class);
			startActivity(intent);
			this.finish();
			return;
		}
		setContentView(R.layout.validation);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		et_validation = (EditText) findViewById(R.id.et_validation);
		et_goodsNum = (EditText) findViewById(R.id.et_goodsNum);
		btn_cancel.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
	}

	// private String getMD5String(String s) {
	// return getMD5String(s.getBytes());
	// }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_sure:
			showRequestDialog("正在授权，请稍等...");
			new GetValidation().start();
			break;
		case R.id.btn_cancel:
			Log.e("Test", "95 is running ");
			ValidationActivity.this.finish();
			break;
		default:
			break;
		}

	}

	/**
	 * 获取验证的线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetValidation extends Thread {
		private String backString;
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			MyConstants.HTTP = MyConstants.VALIDATION_HTTP;
//			Map<String, String> map = new HashMap<String, String>();
//
//			map.put("authkey", "" + et_validation.getText().toString());
//			map.put("ve",
//					MD5.GetMD5Code(et_validation.getText().toString()
//							+ "zrh20150105" + et_goodsNum.getText().toString()));
//			map.put("shjid", "" + et_goodsNum.getText().toString());
//			backString = HttpUtils.getJsoneq(map);
//			if (backString.equals("false")) {
//				handler.sendEmptyMessage(0x111);
//			} else {
//				ParseJsonData(backString);
//			}
//		}
	}

	private void ParseJsonData(String jsonString) {
		Log.e("Test", "95 is running ");
		try {
			JSONObject jsonObject = JsonTools.parseJson(jsonString);
			int code = jsonObject.optInt("code");
			String scode = jsonObject.optString("scode");
			description = jsonObject.optString("description");
			data = jsonObject.optString("data");
			if (code == 0) {
				handler.sendEmptyMessage(0x110);
			} else if (code == 1) {
				handler.sendEmptyMessage(0x123);
			}
		} catch (Exception e) {
			// TODO: handle exception
			handler.sendEmptyMessage(0x111);
		}

	}

	/**
	 * 把返回的地址保存在本地
	 */
	private void setData() {
		sp.setValidation(data);
		Log.e("Test", sp.getValidation());

	}

	// 旋转加载框
	public void showRequestDialog(String msg) {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory
				.creatRequestDialog(ValidationActivity.this, msg);
		mDialog.show();
		mDialog.setCancelable(false);
	}

	// 关闭旋转框
	public void closeReflashDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

}
