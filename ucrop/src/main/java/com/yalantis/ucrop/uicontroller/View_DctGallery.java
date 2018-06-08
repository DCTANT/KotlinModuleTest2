package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.ucrop.R;


/**
 * Generated by Android Module Generator By:DecentAnt V2.8
 * Company： www.chisalsoft.com
 * Time： 2018-01-03 14:30:12
 * Usage：
 */
public class View_DctGallery extends ViewObject {
	protected LinearLayout linearLayout_titlebarLl;
	protected LinearLayout linearLayout_backLl;
	protected TextView textView_back;
	protected TextView textView_chooseFolderBtn;
	protected RecyclerView recyclerView_gallery;
	protected TextView textView_lineGalleyButtom;
	protected LinearLayout linearLayout_buttomLl;
	protected RelativeLayout relativeLayout_burnAfterReadRl;
	protected TextView textView_sendBtn;
	protected View view_popLine;
	protected ImageView imageView_burnAfterReadIv;

	public View_DctGallery(Context context) {
		super(context, R.layout.activity_dct_gallery);
		initView(rootView);
	}

	private void initView(View rootView) {
		linearLayout_titlebarLl = (LinearLayout) rootView.findViewById(R.id.titlebarLl);
		linearLayout_backLl = (LinearLayout) rootView.findViewById(R.id.backLl);
		textView_back = (TextView) rootView.findViewById(R.id.back);
		textView_chooseFolderBtn = (TextView) rootView.findViewById(R.id.chooseFolderBtn);
		recyclerView_gallery = (RecyclerView) rootView.findViewById(R.id.gallery);
		textView_lineGalleyButtom = (TextView) rootView.findViewById(R.id.lineGalleyButtom);
		linearLayout_buttomLl = (LinearLayout) rootView.findViewById(R.id.buttomLl);
		relativeLayout_burnAfterReadRl = (RelativeLayout) rootView.findViewById(R.id.burnAfterReadRl);
		textView_sendBtn = (TextView) rootView.findViewById(R.id.sendBtn);
		view_popLine = (View) rootView.findViewById(R.id.popLine);
		imageView_burnAfterReadIv = (ImageView) rootView.findViewById(R.id.burnAfterReadIv);
	}

	public LinearLayout getLinearLayout_titlebarLl() {
		return linearLayout_titlebarLl;
	}

	public LinearLayout getLinearLayout_backLl() {
		return linearLayout_backLl;
	}

	public TextView getTextView_back() {
		return textView_back;
	}

	public TextView getTextView_chooseFolderBtn() {
		return textView_chooseFolderBtn;
	}

	public RecyclerView getRecyclerView_gallery() {
		return recyclerView_gallery;
	}

	public TextView getTextView_lineGalleyButtom() {
		return textView_lineGalleyButtom;
	}

	public LinearLayout getLinearLayout_buttomLl() {
		return linearLayout_buttomLl;
	}

	public RelativeLayout getRelativeLayout_burnAfterReadRl() {
		return relativeLayout_burnAfterReadRl;
	}

	public TextView getTextView_sendBtn() {
		return textView_sendBtn;
	}

	public View getView_popLine() {
		return view_popLine;
	}

	public ImageView getImageView_burnAfterReadIv() {
		return imageView_burnAfterReadIv;
	}
}