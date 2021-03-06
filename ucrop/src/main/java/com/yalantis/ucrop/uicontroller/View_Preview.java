package com.yalantis.ucrop.uicontroller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.ucrop.R;


/**
 * Generated by Android Module Generator By:DecentAnt V2.8
 * Company： www.chisalsoft.com
 * Time： 2018-02-08 13:45:33
 * Usage：
 */
public class View_Preview extends ViewObject {
	protected TextView textView_back;
	protected TextView textView_currentSize;
	protected TextView textView_totalSize;
	protected RelativeLayout relativeLayout_selectRl;
	protected ImageView imageView_selectIv;
	protected TextView textView_selectedNumber;
	protected RecyclerView recyclerView_picRv;
	protected TextView textView_empty;
	protected TextView textView_functionBtn;
	protected TextView textView_confirmBtn;

	public View_Preview(Context context) {
		super(context, R.layout.activity_preview);
		initView(rootView);
	}

	private void initView(View rootView) {
		textView_back = (TextView) rootView.findViewById(R.id.back);
		textView_currentSize = (TextView) rootView.findViewById(R.id.currentSize);
		textView_totalSize = (TextView) rootView.findViewById(R.id.totalSize);
		relativeLayout_selectRl = (RelativeLayout) rootView.findViewById(R.id.selectRl);
		imageView_selectIv = (ImageView) rootView.findViewById(R.id.selectIv);
		textView_selectedNumber = (TextView) rootView.findViewById(R.id.selectedNumber);
		recyclerView_picRv = (RecyclerView) rootView.findViewById(R.id.picRv);
		textView_empty = (TextView) rootView.findViewById(R.id.empty);
		textView_functionBtn = (TextView) rootView.findViewById(R.id.functionBtn);
		textView_confirmBtn = (TextView) rootView.findViewById(R.id.confirmBtn);
	}

	public TextView getTextView_back() {
		return textView_back;
	}

	public TextView getTextView_currentSize() {
		return textView_currentSize;
	}

	public TextView getTextView_totalSize() {
		return textView_totalSize;
	}

	public RelativeLayout getRelativeLayout_selectRl() {
		return relativeLayout_selectRl;
	}

	public ImageView getImageView_selectIv() {
		return imageView_selectIv;
	}

	public TextView getTextView_selectedNumber() {
		return textView_selectedNumber;
	}

	public RecyclerView getRecyclerView_picRv() {
		return recyclerView_picRv;
	}

	public TextView getTextView_empty() {
		return textView_empty;
	}

	public TextView getTextView_functionBtn() {
		return textView_functionBtn;
	}

	public TextView getTextView_confirmBtn() {
		return textView_confirmBtn;
	}

}