package com.yalantis.ucrop.uicontroller;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.M_Img;
import com.yalantis.ucrop.model.M_Settings;
import com.yalantis.ucrop.util.CameraPicUtil;
import com.yalantis.ucrop.util.DensityUtil;
import com.yalantis.ucrop.util.PermissionCheckUtil;
import com.yalantis.ucrop.util.StartGalleryAndCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated by Android Module Generator By:DecentAnt V2.7
 * Company： www.chisalsoft.com
 * Time： 2017-12-28 14:39:18
 * Usage：
 */
public class Activity_Camera extends AppBaseCompatActivity<View_DctCamera> {

	private File saveImg;
	private ArrayList<M_Img> selectedList;
	private String imgSaveCropPath;
	private String fileName;
	private M_Img takedPic;
	private Bitmap catchBitmap;

	@Override
	protected View_DctCamera initViewObject(Context context) {
		return new View_DctCamera(context);
	}

	private final String TAG = Activity_Camera.class.getName();
	private SurfaceView display;
	private boolean isOpen = false;
	private Camera camera;
	private Handler handler;
	public String imgSavePath;
	private int flashMode = 0;//0无闪光灯，1自动闪光灯，2一直都是闪光灯
	private int cameraOrientation = 0;
	private int cameraCount;
	private int result;
	private String imgName = "";
	private M_Settings m_settings;
	private String defaultPath;

	@Override
	protected void initVariable() {
		defaultPath = this.getApplication().getExternalCacheDir() + "/image/";
		selectedList = (ArrayList<M_Img>) getIntent().getSerializableExtra(StartGalleryAndCamera.SELECTED_IMG);
		m_settings = (M_Settings) getIntent().getSerializableExtra(StartGalleryAndCamera.SETTINGS);
		handler = new Handler();
		display = getViewObject().getSurfaceView_cameraPreview();
		cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
		if (m_settings.getSavePath() == null || m_settings.getSavePath().length() == 0) {
			imgSaveCropPath = defaultPath;
		} else {
			imgSaveCropPath = m_settings.getSavePath();
		}
		flashMode = m_settings.getFlashMode();
		deleteAllFilesOfDir(new File(imgSaveCropPath));
	}

	@Override
	protected void setListener() {
		getViewObject().getImageView_openCamera().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isOpen) {
					showLoading("照片信息处理中……");
					camera.autoFocus(new Camera.AutoFocusCallback() {
						@Override
						public void onAutoFocus(boolean success, Camera camera) {
							try {
								if (success) {

								} else {
									Log.i(TAG, "无法自动对焦");

								}
								takePhoto();
							} catch (Exception e) {
							}

						}
					});
				} else {
					startCamera();
				}

			}
		});
		getViewObject().getImageView_reverse().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cameraCount == 1) {
					return;
				}
				Log.e(TAG, "cameraCount:" + cameraCount);
				camera.stopPreview();
				camera.release();
				camera = null;
				System.gc();
				if (cameraOrientation == 0) {
					cameraOrientation = 1;
					getViewObject().getImageView_flashMode().setVisibility(View.INVISIBLE);
//                    camera = Camera.open(1);
					flashMode = 0;
//                    preFlashMode = flashMode;
//
//                    setFlashMode();
				} else {
					cameraOrientation = 0;
					getViewObject().getImageView_flashMode().setVisibility(View.VISIBLE);
//                    camera = Camera.open(0);
					flashMode = m_settings.getFlashMode();

//                    flashMode = preFlashMode;
//                    setFlashMode();
				}
				startCamera();
				setFlashMode();
			}
		});

		getViewObject().getImageView_cancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		getViewObject().getImageView_flashMode().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Camera.Parameters parameters = camera.getParameters();
				switch (flashMode) {
					case 0:
						flashMode = 1;
						parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
						getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_auto_selected);
						break;
					case 1:
						flashMode = 2;
						parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
						getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_on_selected);
						break;
					case 2:
						flashMode = 0;
						parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
						getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_off_select);
						break;
				}
				m_settings.setFlashMode(flashMode);
				camera.setParameters(parameters);
			}
		});

	}

	private void setFlashMode() {
		Camera.Parameters parameters = camera.getParameters();
		if (cameraOrientation == 1) {
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		} else {
			switch (flashMode) {
				case 0:
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
					getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_off_select);
					break;
				case 1:
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
					getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_auto_selected);
					break;
				case 2:
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
					getViewObject().getImageView_flashMode().setImageResource(R.mipmap.flash_on_selected);
					break;
			}
		}
		camera.setParameters(parameters);
	}

	private void startCamera() {
		camera = Camera.open(cameraOrientation);
		Log.e(TAG, "openCamera");
		setParameter();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					camera.setPreviewDisplay(display.getHolder());
				} catch (IOException e) {
					e.printStackTrace();
				}
				camera.startPreview();
			}
		}, 500);
		isOpen = true;
	}

	private void takePhoto() {
		camera.takePicture(new Camera.ShutterCallback() {
			@Override
			public void onShutter() {
				Log.e(TAG, "onShutter");
			}
		}, new Camera.PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
			}
		}, new Camera.PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, final Camera camera) {
				Log.i(TAG, new String(data));
				showLoading("照片信息处理中……");
				catchBitmap = CameraPicUtil.bytes2Bimap(data);
				CameraPicUtil.getCompressedImage(catchBitmap, m_settings.getMaxSize(), new CameraPicUtil.OnCompressFinish() {
					@Override
					public void onFinish(Bitmap bitmap) {
						handleImg(bitmap, camera);
					}
				});

			}
		});
	}

	private void handleImg(Bitmap bitmap, Camera camera) {
		catchBitmap = bitmap;
		if (cameraOrientation == 0) {
			catchBitmap = CameraPicUtil.rotateBitmap(catchBitmap, result);
		} else {
			catchBitmap = CameraPicUtil.rotateBitmap(catchBitmap, result + 180);
		}
		if (cameraOrientation == 1) {//前置摄像头水平镜像
			Matrix m = new Matrix();
			m.postScale(-1, 1); // 镜像水平翻转
			catchBitmap = Bitmap.createBitmap(catchBitmap, 0, 0, catchBitmap.getWidth(), catchBitmap.getHeight(), m, true);
		}
		imgName = "czbdc" + System.currentTimeMillis() + ".jpg";
		File dic = new File(imgSaveCropPath);
		if (!dic.exists()) {
			dic.mkdirs();
		}
		saveImg = new File(imgSaveCropPath + imgName);
		if (!saveImg.exists()) {
			try {
				saveImg.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		takedPic = new M_Img();
		try {
			takedPic.setTitle("聊天");
			takedPic.setPath(imgSaveCropPath + imgName);
			FileOutputStream fileOutputStream = new FileOutputStream(saveImg);
			fileOutputStream.write(CameraPicUtil.Bitmap2Bytes(catchBitmap));
			fileOutputStream.flush();
			fileOutputStream.close();
			Log.i(TAG, "img path:" + takedPic.getPath());
			dismissDialog();
			if (m_settings.isNeedCrop()) {
				startCrop(new File(takedPic.getPath()), m_settings);

			} else {
//					ArrayList<M_Img> arrayList=new ArrayList<>();
//					arrayList.add(img);
				selectedList.add(takedPic);
				takedPic.setSequence(selectedList.size());
				if (m_settings.isNeedPreview()) {
					startActivityForResult(new Intent(context, Activity_Preview.class).putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList).putExtra(StartGalleryAndCamera.TOTAL_IMG, selectedList).putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList).putExtra(StartGalleryAndCamera.SELECTED_NUM, selectedList.size() - 1).putExtra(StartGalleryAndCamera.SETTINGS, m_settings), StartGalleryAndCamera.REQUEST_CODE_CAMERA);
				} else {
					setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList));
					finish();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.stopPreview();
		catchBitmap = null;
		System.gc();
	}


	/**
	 * 设置照片格式
	 */
	private void setParameter() {
		Camera.Parameters parameters = camera.getParameters(); // 获取各项参数
		parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
		parameters.setJpegQuality(100); // 设置照片质量
		if (cameraOrientation == 0) {
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		}
		//获得相机支持的照片尺寸,选择合适的尺寸
		List<Camera.Size> sizes = parameters.getSupportedPictureSizes();

		for (Camera.Size size : sizes) {
			Log.i(TAG, "support size：width" + size.width + ",height:" + size.height);
		}
		sizes.remove(0);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Log.i(TAG, "width:" + parameters.getPreviewSize().width + ",height:" + parameters.getPreviewSize().height);
		double ratio = parameters.getPreviewSize().height * 1.0f / parameters.getPreviewSize().width;
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewObject().getSurfaceView_cameraPreview().getLayoutParams();
//		int height = (int) (ratio * parameters.getPreviewSize().height);
//		int width = DensityUtil.getScreenWidth(context);
		int height;
		int width;
		if (ratio > DensityUtil.getScreenHeight(context) * 1.0f / DensityUtil.getScreenWidth(context)) {
			height = DensityUtil.getScreenHeight(context);
			width = (int) (ratio * height);
		} else {
			width = DensityUtil.getScreenWidth(context);
			height = (int) (1.0 * width / ratio);
		}
		layoutParams.width = width;
		layoutParams.height = height;
		Log.i(TAG, "Surface width" + width + ",heigth:" + height);
		getViewObject().getSurfaceView_cameraPreview().setLayoutParams(layoutParams);
		Log.i(TAG, "拍摄完成：width:" + parameters.getPictureSize().width + ",height:" + parameters.getPictureSize().height);
		Camera.Size sizePic = getCloselyResultSizeEvo(sizes);
		parameters.setPictureSize(sizePic.width, sizePic.height);

//		Camera.Size size = getCloselyPreSizeEvo(DensityUtil.getScreenWidth(context), DensityUtil.getScreenHeight(context), sizes);
//		Log.i(TAG, "width:" + size.width + ",height:" + size.height);
//		parameters.setPreviewSize(size.width, size.height);
//		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(size.height, size.width);
//		display.setLayoutParams(layoutParams);

		int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
		}
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(0, info);
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
		camera.setParameters(parameters);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (PermissionCheckUtil.checkPermission(context, Manifest.permission.CAMERA) && PermissionCheckUtil.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			startCamera();
			setFlashMode();
		} else {
			PermissionCheckUtil.requestPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (camera != null) {
			Log.e(TAG, "进入onPause，release");
			camera.release();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (camera != null) {
			Log.e(TAG, "进入onDestroy，release");
			camera.release();
		}
	}

	public void deleteAllFilesOfDir(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (File file : files) {
			deleteAllFilesOfDir(file);
		}
		if (path.delete()) {
			Log.i(TAG, "相册缓存删除成功!");
		}
	}

	protected Camera.Size getCloselyResultSizeEvo(List<Camera.Size> preSizeList) {
		int lessOffset = Integer.MAX_VALUE;
		int lessOffsetPosition = 0;
		int sizeOfScreen = DensityUtil.getScreenHeight(context) * DensityUtil.getScreenWidth(context);
		for (int i = 0; i < preSizeList.size(); i++) {
			Camera.Size size = preSizeList.get(i);
			int area = size.width * size.height;
			int offset = Math.abs(sizeOfScreen - area);
			if (offset < lessOffset) {
				lessOffset = offset;
				lessOffsetPosition = i;
			}

		}
		Camera.Size result = preSizeList.get(lessOffsetPosition);
		Log.i(TAG, "最终选择的图片大小为：width:" + result.width + ",height:" + result.height);
		return result;

	}

	/**
	 * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
	 *
	 * @param surfaceWidth  需要被进行对比的原宽
	 * @param surfaceHeight 需要被进行对比的原高
	 * @param preSizeList   需要对比的预览尺寸列表
	 * @return 得到与原宽高比例最接近的尺寸
	 */
	protected Camera.Size getCloselyPreSizeEvo(int surfaceWidth, int surfaceHeight,
											   List<Camera.Size> preSizeList) {
		int standard = 0;
		double ratio = 0.0;
		Log.i(TAG, "surfaceWidth:" + surfaceWidth + ",surfaceHeight:" + surfaceHeight);
		if (surfaceWidth > surfaceHeight) {
			standard = surfaceHeight;
			ratio = 1.0 * surfaceWidth / surfaceHeight;
		} else {
			standard = surfaceWidth;
			ratio = 1.0 * surfaceHeight / surfaceWidth;
		}
		Log.i(TAG, "ratio:" + ratio);
		int resultStandard = 0;
		int absSizeOffset = Integer.MAX_VALUE;
		double absRatioOffest = Double.MAX_VALUE;
		int positionSize = 0;
		Camera.Size retSize = null;
		for (int i = 0; i < preSizeList.size(); i++) {
			Camera.Size size = preSizeList.get(i);
			int tempStandard = 0;
			double tempRatio = 0.0;
			if (size.width > size.height) {
				tempRatio = 1.0 * size.width / size.height;
				tempStandard = size.height;
			} else {
				tempRatio = 1.0 * size.height / size.width;
				tempStandard = size.width;
			}
			double tempRatioOffset = Math.abs(tempRatio - ratio);
			int tempSizeOffset = Math.abs(standard - tempStandard);
			Log.i(TAG, "width:" + size.width + ",height:" + size.height + ",tempRatio:" + tempRatio + ",tempRatioOffset:" + tempRatioOffset + ",tempSizeOffset:" + tempSizeOffset + ",absRatioOffest:" + absRatioOffest + ",absSizeOffset:" + absSizeOffset);
			if (tempRatioOffset <= absRatioOffest) {
				Log.i(TAG, "tempRatioOffset <= absRatioOffest");
				absRatioOffest = tempRatioOffset;
				if (tempSizeOffset < absSizeOffset) {
					Log.i(TAG, "tempSizeOffset < absSizeOffset");
					absSizeOffset = tempSizeOffset;
					positionSize = i;
				}
			}
		}
		retSize = preSizeList.get(positionSize);
		Log.i(TAG, "result width:" + retSize.width + ",height:" + retSize.height);
		return retSize;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionCheckUtil.REQUEST_PERMISSION_CODE) {
			boolean isGranted = true;
			for (int i = 0; i < grantResults.length; i++) {
				if (grantResults[i] != 0) {
					isGranted = false;
				}
			}
			if (isGranted) {
				startCamera();
				setFlashMode();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (requestCode == UCrop.REQUEST_CROP) {
				M_Img m_img = new M_Img();
				m_img.setPath(fileName);
				selectedList.add(m_img);
				setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList));
				finish();
			} else if (requestCode == StartGalleryAndCamera.REQUEST_CODE_CAMERA) {
				ArrayList<M_Img> selected = (ArrayList<M_Img>) data.getSerializableExtra(StartGalleryAndCamera.SELECTED_IMG);
				setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selected));
				finish();
			}
//			Log.i(TAG, "进入result ok状态");
//			ArrayList<M_Img> imgs= (ArrayList<M_Img>) data.getSerializableExtra(Activity_WxGallery.EXTRA_KEY);
//			boolean isBurn=data.getBooleanExtra(S_Extra.BURN_AFTER_READ,false);
//			Log.i(TAG,"img:"+imgs.toString()+",isBurn:"+isBurn);
//			setResult(RESULT_OK, data.putExtra(Activity_WxGallery.EXTRA_KEY,imgs).putExtra(S_Extra.BURN_AFTER_READ,isBurn));
//			dismissDialog();
//			finish();
		}
		if (requestCode == UCrop.RESULT_ERROR) {
			Throwable cropError = UCrop.getError(data);
			cropError.printStackTrace();
		}
//		Throwable cropError = UCrop.getError(data);
//		cropError.printStackTrace();
//		deleteAllFilesOfDir(new File(imgSavePath));
	}

	protected void startCrop(File imgFile, M_Settings m_settings) {
		Uri uriOrigin = null;
		Uri uriDestination = null;
		fileName = imgSaveCropPath + "tempCrop" + System.currentTimeMillis() + ".jpg";
		File tempCropFile = new File(fileName);
		String destinationFileName = "tempCrop" + System.currentTimeMillis() + ".jpg";
		if (!tempCropFile.exists()) {
			try {
				tempCropFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Build.VERSION.SDK_INT <= 23) {
			uriOrigin = Uri.fromFile(imgFile);
			uriDestination = Uri.fromFile(tempCropFile);
		} else {
			uriOrigin = FileProvider.getUriForFile(this, getString(R.string.ucrop_authority), imgFile);
			uriDestination = FileProvider.getUriForFile(this, getString(R.string.ucrop_authority), new File(getApplication().getExternalCacheDir(), destinationFileName));
		}
		UCrop.of(uriOrigin, uriDestination)
				.withAspectRatio(m_settings.getAspectRatioX(), m_settings.getAspectRatioY())
				.withMaxResultSize(m_settings.getWidth(), m_settings.getHeight())
				.start(this);
	}
}