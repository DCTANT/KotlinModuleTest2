package com.yalantis.ucrop.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.yalantis.ucrop.model.M_Img;
import com.yalantis.ucrop.model.M_Settings;
import com.yalantis.ucrop.uicontroller.Activity_Camera;
import com.yalantis.ucrop.uicontroller.Activity_Gallery;
import com.yalantis.ucrop.uicontroller.Activity_Preview;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dechert on 2018-02-02.
 * Company: www.chisalsoft.co
 */

public class StartGalleryAndCamera {
	private static M_Settings m_settings;
	public static String DEFAULT_PATH;
	private ArrayList<M_Img> selectedList;
	private Context context;
	private Activity activity;
	private Fragment fragment;

	public static String SELECTED_IMG = "SELECTED_IMG";
	public static String SETTINGS = "SETTINGS";
	public static String SELECTED_NUM = "SELECTED_NUM";
	public static String SELECTED_ALBUMN_NAME = "SELECTED_ALBUMN_NAME";
	public static String TOTAL_IMG = "TOTAL_IMG";
	public static String CROP = "CROP";
	public static int REQUEST_CODE_GALLERY = 101;
	public static int REQUEST_CODE_CAMERA = 102;
	public static int REQUEST_CODE_CROP = 104;
	public static int REQUEST_CODE_PREVIEW = 108;
	public static int FLASH_MODE_OFF = 0;
	public static int FLASH_MODE_AUTO = 1;
	public static int FLASH_MODE_ON = 2;

	public StartGalleryAndCamera(Context context, Activity activity, ArrayList<M_Img> selectedList) {
		init(context, activity,null, selectedList);
	}

	public StartGalleryAndCamera(Context context, Fragment fragment,ArrayList<M_Img> selectedList){
		init(context,null,fragment,selectedList);
	}

	private void init(Context context, Activity activity,Fragment fragment, ArrayList<M_Img> selectedList) {
		this.context = context;
		this.activity = activity;
		this.selectedList = selectedList;
		this.fragment=fragment;
		if (m_settings == null) {
			m_settings = new M_Settings();
		}
		m_settings.setMaxSize(1024 * 1024);
		m_settings.setNeedCrop(false);
		m_settings.setMaxImgs(9);
		m_settings.setWidth(800);
		m_settings.setHeight(800);
		m_settings.setNeedPreview(true);
		m_settings.setAspectRatioX(1);
		m_settings.setAspectRatioY(1);
		if(activity==null){
			activity=fragment.getActivity();
		}
//		DEFAULT_PATH = activity.getApplication().getExternalCacheDir() + "/image/";
		DEFAULT_PATH =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/ClearNotepad/";
		File file = new File(DEFAULT_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
	}


	public void startGallery() {
		if(activity!=null){
			activity.startActivityForResult(new Intent(context, Activity_Gallery.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList), REQUEST_CODE_GALLERY);
		}else{
			fragment.startActivityForResult(new Intent(context, Activity_Gallery.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList), REQUEST_CODE_GALLERY);
		}

	}

	public void startCamera() {
		if(activity!=null){
			activity.startActivityForResult(new Intent(context, Activity_Camera.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList), REQUEST_CODE_CAMERA);
		}else{
			fragment.startActivityForResult(new Intent(context, Activity_Camera.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList), REQUEST_CODE_CAMERA);
		}

	}

	public void startPreview(int listPosition) {
		if(activity!=null){
			activity.startActivityForResult(new Intent(context, Activity_Preview.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList).putExtra(SELECTED_NUM, listPosition).putExtra(TOTAL_IMG,selectedList), REQUEST_CODE_PREVIEW);
		}else{
			fragment.startActivityForResult(new Intent(context, Activity_Preview.class).putExtra(SETTINGS, m_settings).putExtra(SELECTED_IMG, selectedList).putExtra(SELECTED_NUM, listPosition).putExtra(TOTAL_IMG,selectedList), REQUEST_CODE_PREVIEW);
		}

	}

	public StartGalleryAndCamera setMaxImgs(int max) {
		m_settings.setMaxImgs(max);
		return this;
	}

	public StartGalleryAndCamera setNeedCrop(boolean isNeed) {
		m_settings.setNeedCrop(isNeed);
		return this;
	}

	public StartGalleryAndCamera setImgMaxSize(int maxSize) {
		m_settings.setMaxSize(maxSize * 8);
		return this;
	}

	public StartGalleryAndCamera setImgWidth(int width) {
		m_settings.setWidth(width);
		return this;
	}

	public StartGalleryAndCamera setImgHeight(int height) {
		m_settings.setWidth(height);
		return this;
	}

	public StartGalleryAndCamera setNeedPreview(boolean preview) {
		m_settings.setNeedPreview(preview);
		return this;
	}

	public StartGalleryAndCamera setFlashMode(int flashMode) {
		m_settings.setFlashMode(flashMode);
		return this;
	}

	public StartGalleryAndCamera setAspectRatioX(float aspectRatioX) {
		m_settings.setAspectRatioX(aspectRatioX);
		return this;
	}

	public StartGalleryAndCamera setAspectRatioY(float aspectRatioY) {
		m_settings.setAspectRatioY(aspectRatioY);
		return this;
	}

	public StartGalleryAndCamera setIsPreviewShowNumber(boolean isShow) {
		m_settings.setPreviewShowNumber(isShow);
		return this;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data, @Nullable RecyclerView.Adapter adapter) {
		if (resultCode == Activity.RESULT_OK) {
			ArrayList<M_Img> selectedResults = (ArrayList<M_Img>) data.getSerializableExtra(SELECTED_IMG);
			selectedList.clear();
			if(m_settings.isNeedCrop()){
				selectedList.add(selectedResults.get(selectedResults.size()-1));
			}else{
				selectedList.addAll(selectedResults);
			}
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			if (requestCode == REQUEST_CODE_GALLERY) {

			} else if (requestCode == REQUEST_CODE_CAMERA) {

			} else if (requestCode == REQUEST_CODE_PREVIEW) {

			}
		}
	}
}
