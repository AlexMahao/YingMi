package com.mahao.alex.yingmi.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdataManager {

	/**
	 * 下载进度条
	 */
	private ProgressDialog dialog;

	/**
	 * 当前文件下载的进度
	 */
	private int progress;


	private AppVersion appVersion;

	public UpdataManager() {


		dialog = new ProgressDialog(AppManager.getAppManager().currentActivity());
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条

		dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
		dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		dialog.setTitle("下载进度");
		dialog.setMax(100);

	}

	/**
	 * 联网请求版本号，检测是否有更新
	 */
	public void requestVer() {

		RetrofitManager
				.getInstance()
				.getAppVersion()
				.subscribe(new ResultSubscriber<AppVersion>(){
					@Override
					public void onNext(AppVersion o) {
						appVersion = o;
						String version = o.getVersionName();
						String oldVersion = getAppVersionName(App.getContext());
						if(Integer.parseInt(version.replace(".",""))>
								Integer.parseInt(oldVersion.replace(".",""))){
							AlertDialog dialog = new AlertDialog.Builder(AppManager.getAppManager().currentActivity())
									.setTitle("更新").setMessage("有新的更新，请主人点击我吧！")

									.setCancelable(false)

									.setPositiveButton("确定", new OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
															int which) {

											start();

										}
									})
									.setNegativeButton("取消", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {

										}
									})
									.create();
							dialog.show();
						} else{
							Tt.showShort("当前已是最新版本");
						}



					}
				});


	}

	public void start() {
		dialog.show();
		downloadApk();
	}

	private void downloadApk() {
		Thread downLoadThread = new Thread(downApkRunnable);
		downLoadThread.start();
	}

	private Runnable downApkRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				// 服务器上新版apk地址
				URL url = new URL(appVersion.getFilePath());
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/updateApkFile/ym");
				
				if(file.exists()){
					deleteDir(file);
				}
				
				if (!file.exists()) {
					// 如果文件夹不存在,则创建
					System.out.println("创建是否成功：" + file.mkdirs());
				}

				// 下载服务器中新版本软件（写文件）
				String apkFile = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/updateApkFile/ym";
				File ApkFile = new File(apkFile + "/yingmi.apk");
			
				FileOutputStream fos = new FileOutputStream(ApkFile);
				int count = 0;
				byte buf[] = new byte[1024];
				int numRead = -1;
				while ((numRead = is.read(buf)) != -1) {

					count += numRead;
					// 更新进度条
					progress = (int) (((float) count / length) * 100);
					handler2.sendEmptyMessage(1);
					fos.write(buf, 0, numRead);
				}

				handler2.sendEmptyMessage(0);

			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 更新进度情况
				dialog.setProgress(progress);
				// pb.setProgress(progress);
				break;
			case 0:
				dialog.dismiss();
				// 安装apk文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	private void installApk() {
		// 获取当前sdcard存储路径
		File apkfile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/updateApkFile/ym/yingmi.apk");
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		// 安装，如果签名不一致，可能出现程序未安装提示
		i.setDataAndType(Uri.fromFile(new File(apkfile.getAbsolutePath())),
				"application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AppManager.getAppManager().currentActivity().startActivity(i);
	}
	
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }



	/**
	 * 获取系统版本号
	 *
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null|| versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.i("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}
