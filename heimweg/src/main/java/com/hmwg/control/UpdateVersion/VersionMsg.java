package com.hmwg.control.UpdateVersion;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.hmwg.eric.R;
import com.hmwg.utils.AppUtils;

public class VersionMsg {


	public static int getVersionCode(Context context) {
		int code = 0;
		try {
			code = context.getPackageManager().getPackageInfo(
					AppUtils.getPackageName(context), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	public static String getAppName(Context context) {
		return context.getResources().getText(R.string.app_name).toString();

	}
	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Context context) {
		String version = "";
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        version = info.versionName;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return version;
	}

}
