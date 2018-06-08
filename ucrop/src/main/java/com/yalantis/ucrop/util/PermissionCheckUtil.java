package com.yalantis.ucrop.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Dechert on 2017-12-28.
 * Company: www.chisalsoft.co
 */

public class PermissionCheckUtil {
	public static final int REQUEST_PERMISSION_CODE = 21412;

	/**
	 * eg:Manifest.permission.READ_CONTACTS
	 *
	 * @param context
	 * @param permission
	 * @return true为有这个权限，false为没有这个权限
	 */
	public static boolean checkPermission(Context context, String permission) {
		if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * eg:new String[]{Manifest.permission.READ_CONTACTS}
	 *
	 * @param activity
	 * @param permissionStrings
	 */
	public static void requestPermission(Activity activity, String[] permissionStrings) {
		ActivityCompat.requestPermissions(activity,	permissionStrings,REQUEST_PERMISSION_CODE);
	}
}
