package com.zrh.urestaurantprivate.thread;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.zrh.urestaurantprivate.httputils.MyConstants;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @copyright 中荣恒科技有限公司
 * @function 下载数据的线程
 * @author 吴强
 * @version v1.1
 * @data 2015-02-04
 * 
 */
public class DownLoadThread extends Thread {
	// private ProgressDialog mDialog;
	private Context context;
	private String[] params;

	// 构造函数
	public DownLoadThread() {
	}

	public DownLoadThread(Context con, String[] params) {
		context = con;
		this.params = params;
		createSDCardDir();
	}

	/**********************************************************
	 * Function: createSDCardDir Description: 创建sd卡的目录 Calls:
	 * 被本函数（方法）调用的函数（方法）清单 Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed:
	 * 被访问的表（此项仅对于牵扯到数据库操作的程序） Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input: 无
	 * Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public static void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();

			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + MyConstants.D_IMG_DIR_MENU;// test
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
			}
			String ps = sdcardDir.getPath() + MyConstants.D_IMG_DIR_ENV;// test
			File path2 = new File(ps);
			if (!path2.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path2.mkdirs();
			}
		} else {
			return;
		}

	}

	// 下载图片

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		String fileName;
		String backstring = "yes";
		File directory = Environment.getExternalStorageDirectory();
		HttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter("http.socket.timeout", 30000);// 设置超时10秒
		for (String url : params) {
			if (url == null || url.equals(""))
				continue;
			url = url.trim();
			// toLowerCase()函数把所有字母转换成小写字母返回
			if (!url.substring(0, 7).toLowerCase().equals("http://")
					&& !url.substring(0, 8).toLowerCase().equals("https://")) {
				url = MyConstants.HTTP2 + url;
			}
			String p = url.substring(url.lastIndexOf("/") - 4,
					url.lastIndexOf("/"));
			fileName = url.substring(url.lastIndexOf("/") + 1);

			fileName = URLDecoder.decode(fileName);
			File file = new File(directory, fileName);
			if (file.exists()) {
				backstring = fileName;
			}
			try {
				System.out.println("directory fileName = " + directory + "/"
						+ fileName);
				HttpGet get = new HttpGet(url);
				HttpResponse response = client.execute(get);
				if (HttpStatus.SC_OK == response.getStatusLine()
						.getStatusCode()) {
					HttpEntity entity = response.getEntity();
					InputStream input = entity.getContent();
					writeToSDCard(fileName, input, p);
					input.close();
					backstring = fileName;
				} else {
					backstring = "yes";
				}
			} catch (Exception e) {
				e.printStackTrace();
				backstring = "yes";
			}
		}
		// MyApplication a = (MyApplication) context.getApplicationContext();
		// a.isstop = true;
	}

	/**********************************************************
	 * Function: writeToSDCard Description: 写到sd卡 Calls: 被本函数（方法）调用的函数（方法）清单
	 * Called By: 调用本函数（方法）的函数（方法）清单 Table Accessed: 被访问的表（此项仅对于牵扯到数据库操作的程序）
	 * Table Updated: 被修改的表（此项仅对于牵扯到数据库操作的程序） Input:
	 * fileName：文件名，InputStream：写入流 Output: 对输出参数的说明。 Return: 无 Others: 其它说明
	 *********************************************************/
	public void writeToSDCard(String fileName, InputStream input, String p)
			throws IOException {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File directory = Environment.getExternalStorageDirectory();
			File file = new File(directory.getCanonicalPath()
					+ MyConstants.D_IMG_DIR + p, fileName);// test
			try {
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[2048];
				int j = 0;
				while ((j = input.read(b)) != -1) {
					fos.write(b, 0, j);
				}
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast t = Toast.makeText(context, "\n请插入SD卡！\n", Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}
}
