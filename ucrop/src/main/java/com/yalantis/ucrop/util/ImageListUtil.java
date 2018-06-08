package com.yalantis.ucrop.util;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import com.yalantis.ucrop.model.M_Img;

import java.util.ArrayList;

/**
 * Created by Dechert on 2018-02-08.
 * Company: www.chisalsoft.co
 */

public class ImageListUtil {
	private static final String[] STORE_IMAGES = {
			MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
			MediaStore.Images.Media.DATA,
			MediaStore.Images.Media.LONGITUDE, // 经度
			MediaStore.Images.Media._ID, // id
			MediaStore.Images.Media.BUCKET_ID, // dir id 目录
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME // dir name 目录名字
	};
	public static ArrayList<M_Img> listImg(Activity activity, String albumName){
		ArrayList<M_Img> arrayList=new ArrayList<>();
		Cursor cursor = MediaStore.Images.Media.query(activity.getContentResolver(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
		boolean needFilter=false;
		if(albumName.length()==0){
			needFilter=false;
		}else{
			needFilter=true;
		}
		while (cursor.moveToNext()) {
			String path = cursor.getString(1);
			String id = cursor.getString(3);
			String dirId = cursor.getString(4);
			String dir = cursor.getString(5);
			String fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
			if (needFilter) {
				if (albumName.equals(dir)) {
					arrayList.add(addImgToList(path, id, dirId, dir, fileName));
				}
			} else {
				arrayList.add(addImgToList(path, id, dirId, dir, fileName));
			}
		}
		cursor.close();
		return arrayList;
	}

	private static M_Img addImgToList(String path, String id, String dirId, String dir, String fileName) {
		M_Img img = new M_Img();
		img.setViewType(M_Img.PIC);
		img.setPath(path);
//		img.setId(id);
//		img.setDirID(dirId);
//		img.setDirName(dir);
		img.setImgName(fileName);
		img.setEcho(false);
//		totalList.add(img);
		return img;
	}

}
