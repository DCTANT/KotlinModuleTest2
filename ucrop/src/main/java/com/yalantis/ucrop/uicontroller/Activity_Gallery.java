package com.yalantis.ucrop.uicontroller;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.listener.OnAlbumChangeListener;
import com.yalantis.ucrop.model.M_Album;
import com.yalantis.ucrop.model.M_Img;
import com.yalantis.ucrop.model.M_Settings;
import com.yalantis.ucrop.uicontroller.popwindow.Popwindow_Album;
import com.yalantis.ucrop.util.DensityUtil;
import com.yalantis.ucrop.util.StartGalleryAndCamera;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Activity_Gallery extends AppBaseCompatActivity<View_DctGallery> {

	@Override
	protected View_DctGallery initViewObject(Context context) {
		return new View_DctGallery(context);
	}

	private final String TAG = "Activity_Gallery";
	private static final int request_albums = 11;
	private static final String[] STORE_IMAGES = {
			MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
			MediaStore.Images.Media.DATA,
			MediaStore.Images.Media.LONGITUDE, // 经度
			MediaStore.Images.Media._ID, // id
			MediaStore.Images.Media.BUCKET_ID, // dir id 目录
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME // dir name 目录名字
	};
	private int maxNumber = 9;
	private Handler handler;
	private Runnable afterTraverseAlbum;
	private Popwindow_Album popwindow_album;
	private UcropRecyclerAdapter<Item_ImgItem, M_Img> ucropRecyclerAdapter;
	private RecyclerView imgRecyclerView;
	private ArrayList<M_Img> totalList;
	private ArrayList<M_Img> selectedList;
	private ArrayList<M_Album> m_albums;
	//用于单张裁剪的时候用的
	private String fileName = "";
	private String imgSaveCropPath;
	private String defaultPath;


	private boolean isSelected = false;
	private boolean isAllPic = true;
	private boolean isInit = true;
	private int allImgNumber = 0;
	private M_Settings m_settings;
	private String selectedalbumName = "";

	@Override
	protected void initVariable() {
		selectedList = (ArrayList<M_Img>) getIntent().getSerializableExtra(StartGalleryAndCamera.SELECTED_IMG);
		m_settings = (M_Settings) getIntent().getSerializableExtra(StartGalleryAndCamera.SETTINGS);
		maxNumber = m_settings.getMaxImgs();
		if (selectedList != null) {
			int tempSize = selectedList.size();
			for (M_Img img : selectedList) {
				Log.i(TAG, "tempSize:" + tempSize + ",img:" + img.toString());
			}
			for (int i = 0; i < selectedList.size(); i++) {
				M_Img img = selectedList.get(i);
				if (img.getViewType() == 1) {
					selectedList.remove(i);
					break;
				}
			}
		} else {
			selectedList = new ArrayList<>();
		}
		totalList = new ArrayList<>();
		m_albums = new ArrayList<>();
		handler = new Handler();
		initRv();
		initPopWindow();
		requestPermission();
		afterTraverseAlbum = new Runnable() {
			@Override
			public void run() {
				popwindow_album.getAlbumAdapter().notifyDataSetChanged();
				isInit = false;
			}
		};
		defaultPath = this.getApplication().getExternalCacheDir() + "/image/";
		if (m_settings.getSavePath() == null || m_settings.getSavePath().length() == 0) {
			imgSaveCropPath = defaultPath;
		} else {
			imgSaveCropPath = m_settings.getSavePath();
		}
		fileName = imgSaveCropPath + "tempCrop" + System.currentTimeMillis() + ".jpg";

	}

	private void requestPermission() {
		AndPermission.with(context)
				.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.callback(new PermissionListener() {
					@Override
					public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
						Log.i(TAG, "PermissionListener onSuccess");
						listImg(false, "");
					}

					@Override
					public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
						Log.i(TAG, "PermissionListener onFail");
					}
				}).start();
	}

	private void initRv() {
		imgRecyclerView = getViewObject().getRecyclerView_gallery();
		imgRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
		ucropRecyclerAdapter = new UcropRecyclerAdapter<Item_ImgItem, M_Img>(context, totalList) {
			@Override
			public Item_ImgItem setViewHolder(ViewGroup parent) {
				return new Item_ImgItem(context, parent);
			}

			@Override
			public void setData(Item_ImgItem v, final int position, final M_Img m) {
				int screenWidth = DensityUtil.getScreenWidth(context);
				int width = (screenWidth - DensityUtil.dip2px(context, 25)) / 4;
				RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) v.getRelativeLayout_wholeItem().getLayoutParams();
				layoutParams.width = width;
				layoutParams.height = width;
				if (position % 4 == 3) {
					layoutParams.setMargins(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), 0);
				} else {
					layoutParams.setMargins(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), 0, 0);
				}
				v.getRelativeLayout_wholeItem().setLayoutParams(layoutParams);
//				DynamicItemUtil.changeMarginNoSize(context, v.getRelativeLayout_wholeItem(), 4, 0, position);
				Glide.with(context).load(new File(m.getPath())).skipMemoryCache(false).into(v.getImageView_showPic());
				if (m.isSelected()) {
					v.getTextView_checkSelect().setBackgroundResource(R.drawable.shape_circle_blue);
					v.getTextView_checkSelect().setText(m.getSequence() + "");
				} else {
					v.getTextView_checkSelect().setBackgroundResource(R.drawable.shape_no_selected_num);
					v.getTextView_checkSelect().setText("");
				}
				v.getLinearLayout_selectLl().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (m.isSelected()) {
							onPicDeselected(m);
						} else {
							onPicSelected(m, position);
						}
						refreshSendBtn();
					}
				});
				v.getRelativeLayout_wholeItem().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (m_settings.isNeedPreview()) {
							startActivityForResult(new Intent(context, Activity_Preview.class).putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList).putExtra(StartGalleryAndCamera.SELECTED_NUM, position).putExtra(StartGalleryAndCamera.SETTINGS, m_settings).putExtra(StartGalleryAndCamera.SELECTED_ALBUMN_NAME, selectedalbumName), StartGalleryAndCamera.REQUEST_CODE_GALLERY);
						}
					}
				});
			}
		};
		imgRecyclerView.setAdapter(ucropRecyclerAdapter);
	}

	private void onPicDeselected(M_Img m) {
		m.setSelected(false);
		for (int i = 0; i < selectedList.size(); i++) {
			if (m.getPath().equals(selectedList.get(i).getPath())) {
				selectedList.remove(i);
				break;
			}
		}
		int selectNum = m.getSequence();
		m.setSequence(-1);
		for (M_Img imgModel : totalList) {
			if (imgModel.getSequence() > selectNum) {
				imgModel.setSequence(imgModel.getSequence() - 1);
			}
		}
		for (M_Img img : selectedList) {
			if (img.getSequence() > selectNum) {
				img.setSequence(img.getSequence() - 1);
			}
		}
		ucropRecyclerAdapter.notifyDataSetChanged();
	}

	private void onPicSelected(final M_Img m, int position) {
		if (selectedList.size() < maxNumber) {
			m.setSelected(true);
			selectedList.add(m);
			m.setSequence(selectedList.size());
		} else {
			Toast.makeText(context, "你最多只能选择" + maxNumber + "张照片", Toast.LENGTH_SHORT).show();
			return;
		}
		ucropRecyclerAdapter.notifyItemChanged(position);
	}

	private void initPopWindow() {
		popwindow_album = new Popwindow_Album(context, m_albums, new OnAlbumChangeListener() {
			@Override
			public void albumChangeListener(String albumName, int position) {
				selectedalbumName = albumName;
				Log.i(TAG, "选择的相册的名字是" + albumName + "，位置为：" + position);
				getViewObject().getTextView_chooseFolderBtn().setText(albumName);
				for (int i = 0; i < m_albums.size(); i++) {
					M_Album MAlbum = m_albums.get(i);
					if (position != 0) {
						isAllPic = false;
					} else {
						isAllPic = true;
					}
					if (i == position) {
						MAlbum.setIsSelected(true);
					} else {
						MAlbum.setIsSelected(false);
					}
				}
				if (position == 0) {
					listImg(false, "");
				} else {
					listImg(true, albumName);
				}
				popwindow_album.dismiss();
				popwindow_album.getAlbumAdapter().notifyDataSetChanged();
			}
		});
	}

	private void listImg(boolean needFilter, String albumFilter) {
		Log.i(TAG, "进入了listImg");
		totalList.clear();
		Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
		while (cursor.moveToNext()) {
			String path = cursor.getString(1);
			String id = cursor.getString(3);
			String dirId = cursor.getString(4);
			String dir = cursor.getString(5);
			String fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
			if (needFilter) {
				if (albumFilter.equals(dir)) {
					addImgToList(path, id, dirId, dir, fileName);
				}
			} else {
				addImgToList(path, id, dirId, dir, fileName);
			}
		}
		cursor.close();
		refreshSelected();
		ucropRecyclerAdapter.notifyDataSetChanged();
		if (!needFilter) {
			allImgNumber = totalList.size();
			Log.i(TAG, "总数量为：" + allImgNumber);
		}
	}

	private void addImgToList(String path, String id, String dirId, String dir, String fileName) {
		M_Img img = new M_Img();
		img.setViewType(M_Img.PIC);
		img.setPath(path);
		img.setId(id);
		img.setDirID(dirId);
		img.setDirName(dir);
		img.setImgName(fileName);
		img.setEcho(false);
		totalList.add(img);
	}

	private void refreshSelected() {
		for (int i = 0; i < totalList.size(); i++) {
			M_Img total = totalList.get(i);
			boolean isSelected = false;
			for (int j = 0; j < selectedList.size(); j++) {
				M_Img select = selectedList.get(j);
				if (total.getPath().equals(select.getPath())) {
					isSelected = true;
					total.setSequence(select.getSequence());
					total.setSelected(true);
					break;
				}
			}
			if (!isSelected) {
				total.setSequence(-1);
				total.setSelected(false);
			}
		}
		refreshSendBtn();
		ucropRecyclerAdapter.notifyDataSetChanged();
	}

	@Override
	protected void setListener() {
		getViewObject().getLinearLayout_backLl().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clickBack();
			}
		});
		getViewObject().getTextView_chooseFolderBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popwindow_album.showAtLocation(getViewObject().getView_popLine(), Gravity.BOTTOM, 0, DensityUtil.dip2px(context, 22));
				if (isInit) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								initAlbumnList();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}).start();
				}
			}
		});

		getViewObject().getTextView_sendBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isSelected) {
					if (!(m_settings.isNeedCrop() && selectedList.size() == 1)) {
						for (M_Img imgModel : selectedList) {
							Log.i(TAG, "send中imgModel为：" + imgModel.toString());
						}
						setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList));
						finish();
					} else {
						startCrop(new File(selectedList.get(0).getPath()), m_settings);
					}
				} else {

				}
			}
		});
	}

	private void initAlbumnList() {
		m_albums.clear();
		M_Album all = new M_Album();
		all.setCoverImgPath(totalList.get(0).getPath());
		all.setAlbumName("所有照片");
		all.setTotalCount(allImgNumber);
		all.setIsSelected(isAllPic);
		m_albums.add(all);
		HashSet<String> hashSet = new HashSet();
		for (int i = 0; i < totalList.size(); i++) {
			hashSet.add(totalList.get(i).getDirName());
		}
		for (String string : hashSet) {
			M_Album MAlbum = new M_Album();
			MAlbum.setAlbumName(string);
			m_albums.add(MAlbum);
		}
		for (int i = 1; i < m_albums.size(); i++) {
			int totalCount = 0;
			boolean hasCover = false;
			for (int j = 0; j < totalList.size(); j++) {
				try {
					if (m_albums.get(i).getAlbumName().equals(totalList.get(j).getDirName())) {
						totalCount++;
						if (!hasCover) {
							m_albums.get(i).setCoverImgPath(totalList.get(j).getPath());
							hasCover = true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			m_albums.get(i).setTotalCount(totalCount);
		}
		handler.post(afterTraverseAlbum);
	}

	private void clickBack() {
		finish();
	}

	private void refreshSendBtn() {
		if (selectedList.size() > 0) {
			getViewObject().getTextView_sendBtn().setText("确定(" + selectedList.size() + "/" + maxNumber + ")");
			getViewObject().getTextView_sendBtn().setVisibility(View.VISIBLE);
			isSelected = true;
		} else {
			getViewObject().getTextView_sendBtn().setText("确定");
			getViewObject().getTextView_sendBtn().setVisibility(View.INVISIBLE);
			isSelected = false;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		clickBack();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == StartGalleryAndCamera.REQUEST_CODE_GALLERY) {
				ArrayList<M_Img> resultList = (ArrayList<M_Img>) data.getSerializableExtra(StartGalleryAndCamera.SELECTED_IMG);
				selectedList.clear();
				selectedList.addAll(resultList);
				refreshSelected();
			} else if (requestCode == UCrop.REQUEST_CROP) {
				M_Img m_img = new M_Img();
				m_img.setPath(fileName);
				selectedList.add(m_img);
				setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList));
				finish();
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		popwindow_album.dismiss();
	}

}