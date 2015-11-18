package com.zrh.urestaurantprivate.httputils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @copyright 中荣恒科技有限公司
 * @function  网络请求数据类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class HttpUtils {

	private static int num2 = -1;

	public HttpUtils() {

	}

	/**
	 * 获取发送请求的url
	 * 
	 * @param map
	 * @return
	 */
	public static String getRequestUrl(Map<String, String> map) {
		String strUrl = "";
		try {
			// 发送的时候使用URLEncoder.encode编码，接收的时候使用URLDecoder.decode解码，
			String strUrlParam = "v=" + URLEncoder.encode("v0.1.0", "utf-8");
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				strUrlParam += "&" + key + "="
						+ URLEncoder.encode(value, "utf-8");
			}
			Log.e("Test", MyConstants.HTTP + "?_r=" + strUrlParam);

			String _r = Base64.encode(strUrlParam.getBytes());
			strUrl = MyConstants.HTTP + "?_r=" + _r;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strUrl;
	}

	/**********
	 * 
	 * Description: 访问网络获取返回的String数据
	 * 
	 *****/
	public static String getJsoneq(Map<String, String> map) {
		String strResult = "false";

		try {
			String strUrl = getRequestUrl(map);
			if (strUrl.equals("")) {
				return strResult; // URL地址错误返回
			}
			URL url = new URL(strUrl);
			System.out.println("getJsoneq地址为：" + url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(10000); // 设置超时时间10秒

			connection.setRequestMethod("GET");
			connection.setDoInput(true);// 以后就可以使用connection.getInputStream().read();
			int code = connection.getResponseCode();
			System.out.println("code的值为：" + code);
			if (code == 200) {
				String backstring = changeInputStream(connection
						.getInputStream());
				if (backstring.length() > 0) {
					String padunstring = backstring.substring(0, 1);
					if (padunstring.equals("﻿") || padunstring.equals("﻿")) {
						backstring = backstring.substring(1,
								backstring.length());
					}
				}
				if (backstring == null) {
					backstring = "";
				}
				strResult = backstring.trim();
				System.out.println("返回结果为：" + strResult.length());
				if (strResult.equals("")) {
					return strResult; // URL地址错误返回
				}
				byte b[] = strResult.getBytes();
				int k = 0;
				while (true) {
					if (b[k] >= 'a' && b[k] <= 'z' || b[k] >= 'A'
							&& b[k] <= 'Z' || b[k] >= '0' && b[k] <= '9') {
						break;
					}
					b[k] = ' ';
					k++;
				}
				if (k != 0) {
					strResult = (new String(b)).trim();
				}
				strResult = new String(Base64.decode(strResult), "UTF-8");// ISO-8859-1
				System.out.println("解密结果为：" + strResult);
			}
		} catch (Exception e) {
			// 对无网的操作，待写
			strResult = "false";
		}
		return strResult; // 超时返回
	}

	/**********************************************************
	 * Function: sendOrder Description: 发送订单信息 Calls: 被本函数（方法）调用的函数（方法）清单 Called
	 * By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序） Table
	 * Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: order：用户订定详细信息，urladd：网络url地址
	 * Output: 对输出参数的说明。 Return: 网络返回值，读取不成功则为false Others: 其它说明
	 *********************************************************/
	public static String sendOrder(String order, String urladd) {
		// System.out.println("num2:"+num2);
		if (num2 == -1) {
			num2 = 1;
		}
		HttpEntityEnclosingRequestBase httpRequest = new HttpPost(
				MyConstants.HTTP + urladd);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json", Base64.encode(order
				.getBytes())));// 封装订单信息
		String strResult = "false";
		System.out.println("false-init");
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
				System.out.println(strResult);
				if (strResult.length() > 0) {
					String padunstring = strResult.substring(0, 1);
					if (padunstring.equals("﻿")) {
						strResult = strResult.substring(1, strResult.length())
								.trim();
						// strResult =
						// strResult.substring(1,strResult.length());
					}
				}
				try {
					byte b[] = strResult.getBytes();
					int k = 0;
					while (true) {
						if (b[k] >= 'a' && b[k] <= 'z' || b[k] >= 'A'
								&& b[k] <= 'Z' || b[k] >= '0' && b[k] <= '9') {
							break;
						}
						b[k] = ' ';
						k++;
					}
					// if ((b[0] & 0xFF) == 0xef && (b[1] & 0xFF) == 0xbb &&
					// (b[2] & 0xFF) == 0xbf)
					if (k != 0) {
						strResult = (new String(b)).trim();
					}

					strResult = new String(Base64.decode(strResult), "UTF-8");
				} catch (NullPointerException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
			}
		} catch (Exception e) {
			if (num2 > 0) {
				num2--;
				sendOrder(order, urladd);
			} else {
				strResult = "false";
				System.out.println("false-exception");
			}
		}
		if ((num2 > 0) && (strResult.equals("false"))) {
			num2--;
			sendOrder(order, urladd);
		}
		num2 = -1;
		System.out.println(strResult);
		return strResult;
	}

	/**********************************************************
	 * Function: changeInputStream Description: 将一个输入流转换成指定编码的字符串 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input:
	 * 要转换的字节流 Output: 对输出参数的说明。 Return: 指定编码的字符串 Others: 其它说明
	 *********************************************************/
	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
			// jsonString = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
