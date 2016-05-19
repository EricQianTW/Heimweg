package com.hmwg.control.UpdateVersion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;

public class LoadAnsyReadVersionXML extends AsyncTask<Void, Void, Void> {
	private static String TAG = "LoadAnsyReadVersionXML";
	public static final String UPDATE_SERVER = "http://mm.tonggo.net/down/";
	public static final String UPDATE_VERXML = "Update_matan.xml";
	private Context mContext;
	public String appanme;
	public String apkName;
	public String versionName;
	public int versionCode;
	public int localversionCode;
	public String localversionName;
	public ProgressDialog pressdialog;
	Handler handler = new Handler();
	private int count = 0;
	private Dialog dialog;
	
	public LoadAnsyReadVersionXML(Context context){
		mContext = context;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		// 进行版本号的比较
		bijiaoVersion();
		super.onPostExecute(result);
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		// 进行url的连接并进行解析xml数据
		try {

			URL url = new URL(UPDATE_SERVER + UPDATE_VERXML + "?dt="
					+ System.currentTimeMillis());

			URLConnection connection = (URLConnection) url.openConnection();
			connection.connect();
			InputStream inputstream = connection.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(inputstream, "utf-8");
			int event = xml.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if ("appname".equals(xml.getName())) {
						appanme = xml.nextText();
					}
					if ("apkName".equals(xml.getName())) {
						apkName = xml.nextText();
					}
					if ("versionName".equals(xml.getName())) {
						versionName = xml.nextText();
					}
					if ("versionCode".equals(xml.getName())) {
						versionCode = Integer.parseInt(xml.nextText());
					}
					break;

				default:
					break;
				}
				event = xml.next();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public void bijiaoVersion() {

		localversionCode = VersionMsg.getVersionCode(mContext);
		localversionName = VersionMsg.getVersionName(mContext);
		if (localversionCode < versionCode) {
			// 当code和name都满足以上条件的时候则进行更新
			downNewVersionUpdate();
		} else {
            if(mOnCompareFinishListen != null){
                mOnCompareFinishListen.CompareFinishListening();
            }
		}

	}
	
	void downNewVersionUpdate() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("当前版本：");
		buffer.append(localversionName);
		buffer.append(",发现新版本:");
		buffer.append(versionName);
		dialog = new AlertDialog.Builder(mContext)
				.setTitle("软件更新")
				.setMessage((buffer).toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {

					@SuppressWarnings("static-access")
					@Override
					public void onClick(DialogInterface dialog, int which) {

						pressdialog = new ProgressDialog(mContext);
						pressdialog.setTitle("正在下载");
						pressdialog.setMessage("请稍后...");
						
						pressdialog.setProgressStyle(pressdialog.STYLE_HORIZONTAL);
						
						pressdialog.incrementProgressBy(1);  
						
						downFile(UPDATE_SERVER + apkName + "?dt="
								+ System.currentTimeMillis());

					}
				})
				.setNegativeButton("取消更新",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
//								initJump();
							}
						}).create();
		dialog.show();
	}

	protected void downFile(final String url) {
		pressdialog.show();
		OkHttpUtils//
				.get()//
				.url(url)//
				.build()//
				.execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")//
				{
					@Override
					public void inProgress(float progress, long total) {
						pressdialog.setProgress((int) (100 * progress));
					}

					@Override
					public void onError(Call call, Exception e) {
						Log.e(TAG, "onError :" + e.getMessage());
					}

					@Override
					public void onResponse(File file)
					{
						Log.e(TAG, "onResponse :" + file.getAbsolutePath());
						// 自动安装
						install();
					}
				});

	}

	void install() {

		handler.post(new Runnable() {

			@Override
			public void run() {

				pressdialog.cancel();
				update();

			}
		});

	}

	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), apkName)),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);

	}

    public interface OnCompareFinishListening{
        void CompareFinishListening();
    }

    OnCompareFinishListening mOnCompareFinishListen = null;

    public void setOnCompareFinishListen(OnCompareFinishListening listening){
        mOnCompareFinishListen = listening;
    }

    public interface OnCancelUpdateListening{
        void CompareFinishListening();
    }

    OnCancelUpdateListening mOnCancelUpdateListening = null;

    public void setOnCancelUpdateListen(OnCancelUpdateListening listening){
        mOnCancelUpdateListening = listening;
    }
}
