package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.R;
import com.yalantis.ucrop.listener.OnAlbumChangeListener;
import com.yalantis.ucrop.model.M_Album;

import java.io.File;

public class Item_ChooseAlbum extends BaseItem {
	private final String TAG = "Item_ChooseAlbum";
	protected LinearLayout linearLayout_allItem;
	protected LinearLayout linearLayout_allPic;
	protected ImageView imageView_picIv;
	protected TextView textView_albumName;
	protected TextView textView_totalCount;
	protected ImageView imageView_isSelectIv;
	protected ImageView imageView_buttomLineIv;
	public OnAlbumChangeListener onAlbumChangeListener;
	private M_Album albumModel;

	public Item_ChooseAlbum(Context context, ViewGroup parent, OnAlbumChangeListener onAlbumChangeListener) {
		super(context, parent, R.layout.item_choose_album);
		this.onAlbumChangeListener=onAlbumChangeListener;
		initView(rootView);
	}

	private void initView(View rootView) {
		linearLayout_allItem = (LinearLayout) rootView.findViewById(R.id.allItem);
		linearLayout_allPic = (LinearLayout) rootView.findViewById(R.id.allPic);
		imageView_picIv = (ImageView) rootView.findViewById(R.id.picIv);
		textView_albumName = (TextView) rootView.findViewById(R.id.albumName);
		textView_totalCount = (TextView) rootView.findViewById(R.id.totalCount);
		imageView_isSelectIv = (ImageView) rootView.findViewById(R.id.isSelectIv);
		imageView_buttomLineIv = (ImageView) rootView.findViewById(R.id.buttomLineIv);
	}

	public void setData(M_Album itemEntity) {
		albumModel=itemEntity;
		if(itemEntity.getCoverImgPath()!=null){
//			ImageHelper.display(context,itemEntity.getCoverImgPath(),imageView_picIv);
			Glide.with(context).load(new File(itemEntity.getCoverImgPath())).skipMemoryCache(false).into(imageView_picIv);
		}
		textView_albumName.setText(itemEntity.getAlbumName());
		textView_totalCount.setText(itemEntity.getTotalCount()+"张");
		if(itemEntity.getIsSelected()){
			imageView_isSelectIv.setVisibility(View.VISIBLE);
		}else{
			imageView_isSelectIv.setVisibility(View.GONE);
		}
	}

	public String getTAG() {
		return TAG;
	}

	public LinearLayout getLinearLayout_allItem() {
		return linearLayout_allItem;
	}

	public LinearLayout getLinearLayout_allPic() {
		return linearLayout_allPic;
	}

	public ImageView getImageView_picIv() {
		return imageView_picIv;
	}

	public TextView getTextView_albumName() {
		return textView_albumName;
	}

	public TextView getTextView_totalCount() {
		return textView_totalCount;
	}

	public ImageView getImageView_isSelectIv() {
		return imageView_isSelectIv;
	}

	public ImageView getImageView_buttomLineIv() {
		return imageView_buttomLineIv;
	}

	public M_Album getM_Album() {
		return albumModel;
	}

	public void setM_Album(M_Album albumModel) {
		this.albumModel = albumModel;
	}
}