package com.yalantis.ucrop.uicontroller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.model.M_Img;
import com.yalantis.ucrop.model.M_Settings;
import com.yalantis.ucrop.util.ImageListUtil;
import com.yalantis.ucrop.util.StartGalleryAndCamera;

import java.io.File;
import java.util.ArrayList;

/**
 * Generated by Android Module Generator By:DecentAnt V2.8
 * Company： www.chisalsoft.com
 * Time： 2018-02-08 13:45:33
 * Usage：
 */
public class Activity_Preview extends AppBaseCompatActivity<View_Preview> {

	private UcropRecyclerAdapter<Item_WholePic, M_Img> ucropRecyclerAdapter;
	private ArrayList<M_Img> selectedList = new ArrayList<>();
	private ArrayList<M_Img> totalList = new ArrayList<>();
	private LinearLayoutManager linearLayoutManager;
	private int nowPosition = 0;
	private M_Settings m_settings;
	private int mode = 0;//0为相册进入，1为相机进入

	@Override
	protected View_Preview initViewObject(Context context) {
		return new View_Preview(context);
	}

	private final String TAG = Activity_Preview.class.getName();

	@Override
	protected void initVariable() {
		initRv();
		initList();
		getViewObject().getTextView_functionBtn().setVisibility(View.GONE);
	}

	private void initList() {
		m_settings = (M_Settings) getIntent().getSerializableExtra(StartGalleryAndCamera.SETTINGS);
		int selectedNumber = getIntent().getIntExtra(StartGalleryAndCamera.SELECTED_NUM, 0);
		ArrayList<M_Img> imgs = (ArrayList<M_Img>) getIntent().getSerializableExtra(StartGalleryAndCamera.SELECTED_IMG);
		String albumString = getIntent().getStringExtra(StartGalleryAndCamera.SELECTED_ALBUMN_NAME);
		ArrayList<M_Img> totalImgs = null;
		if (albumString == null) {
			totalImgs = (ArrayList<M_Img>) getIntent().getSerializableExtra(StartGalleryAndCamera.TOTAL_IMG);
			mode = 1;
		} else {
			totalImgs = ImageListUtil.listImg(this, albumString);
			mode = 0;
		}
		selectedList.clear();
		selectedList.addAll(imgs);
		totalList.clear();
		totalList.addAll(totalImgs);
		imgs = null;
		totalImgs = null;
		System.gc();
		ucropRecyclerAdapter.notifyDataSetChanged();
		getViewObject().getTextView_totalSize().setText(totalList.size() + "");
		getViewObject().getTextView_currentSize().setText((selectedNumber + 1) + "");
		getViewObject().getRecyclerView_picRv().scrollToPosition(selectedNumber);
		if(m_settings.isPreviewShowNumber()){
			getViewObject().getRelativeLayout_selectRl().setVisibility(View.VISIBLE);
		}else{
			getViewObject().getRelativeLayout_selectRl().setVisibility(View.GONE);
		}
		refreshComfirmBtn();
	}

	private void refreshComfirmBtn() {
		if (selectedList.size() == 0) {
			getViewObject().getTextView_confirmBtn().setText("确定");
		} else {
			getViewObject().getTextView_confirmBtn().setText("确定(" + selectedList.size() + "/" + m_settings.getMaxImgs() + ")");
		}
	}

	private void initRv() {
		ucropRecyclerAdapter = new UcropRecyclerAdapter<Item_WholePic, M_Img>(context, totalList) {
			@Override
			public Item_WholePic setViewHolder(ViewGroup parent) {
				return new Item_WholePic(context, parent);
			}

			@Override
			public void setData(Item_WholePic v, int position, M_Img m) {
				Glide.with(context).load(new File(m.getPath())).skipMemoryCache(false).into(v.getImageView_showPic());

			}
		};
		PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
		pagerSnapHelper.attachToRecyclerView(getViewObject().getRecyclerView_picRv());
		getViewObject().getRecyclerView_picRv().setAdapter(ucropRecyclerAdapter);
		linearLayoutManager = new LinearLayoutManager(context);
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		getViewObject().getRecyclerView_picRv().setLayoutManager(linearLayoutManager);

	}

	@Override
	protected void setListener() {
		getViewObject().getRecyclerView_picRv().addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				Log.i(TAG, "newState:" + newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
				int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
				Log.i(TAG, "firstPosition:" + firstPosition + ",lastPosition:" + lastPosition);
				if (firstPosition == lastPosition) {
					nowPosition = firstPosition;
					getViewObject().getTextView_currentSize().setText((nowPosition + 1) + "");
					M_Img nowImg = totalList.get(nowPosition);
					String nowPath = nowImg.getPath();
					boolean isSelected = false;
					for (int i = 0; i < selectedList.size(); i++) {
						M_Img selectedImg = selectedList.get(i);
						String path = selectedImg.getPath();
						if (path.equals(nowPath)) {
							isSelected = true;
							getViewObject().getImageView_selectIv().setSelected(true);
							getViewObject().getTextView_selectedNumber().setText(selectedImg.getSequence() + "");
							getViewObject().getTextView_selectedNumber().setVisibility(View.VISIBLE);
							break;
						}
					}
					if (!isSelected) {
						getViewObject().getImageView_selectIv().setSelected(false);
						getViewObject().getTextView_selectedNumber().setVisibility(View.INVISIBLE);
					}

				}

			}
		});

		getViewObject().getTextView_back().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mode == 1) {
					selectedList.remove(selectedList.size() - 1);
				}
				finish();
			}
		});

		getViewObject().getTextView_confirmBtn().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK, getIntent().putExtra(StartGalleryAndCamera.SELECTED_IMG, selectedList));
				finish();
			}
		});

		getViewObject().getRelativeLayout_selectRl().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				M_Img m_img = totalList.get(nowPosition);
				boolean isSelected = false;
				for (int i = 0; i < selectedList.size(); i++) {
					M_Img selected = selectedList.get(i);
					if (selected.getPath().equals(m_img.getPath())) {
						isSelected = true;
						int sequence = selected.getSequence();
						selected.setSequence(-1);
						selectedList.remove(i);
						getViewObject().getImageView_selectIv().setSelected(false);
						getViewObject().getTextView_selectedNumber().setVisibility(View.INVISIBLE);
						for (int j = 0; j < selectedList.size(); j++) {
							M_Img temp = selectedList.get(j);
							if (temp.getSequence() > sequence) {
								temp.setSequence(temp.getSequence() - 1);
							}
						}
						break;
					}
				}
				if (!isSelected) {
					M_Img select = new M_Img();
					select.setPath(m_img.getPath());
					select.setSelected(true);
					select.setId(m_img.getImgName());
					selectedList.add(select);
					select.setSequence(selectedList.size());
					getViewObject().getImageView_selectIv().setSelected(true);
					getViewObject().getTextView_selectedNumber().setVisibility(View.VISIBLE);
					getViewObject().getTextView_selectedNumber().setText(selectedList.size() + "");
				}
				refreshComfirmBtn();
			}
		});
	}

}