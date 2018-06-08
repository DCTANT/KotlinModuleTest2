package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.ucrop.R;


public class Item_ImgItem extends BaseItem {
	protected LinearLayout linearLayout_selectLl;
	private String TAG = Item_ImgItem.class.getName();
	protected RelativeLayout relativeLayout_wholeItem;
	protected ImageView imageView_showPic;
	protected TextView textView_checkSelect;

	public Item_ImgItem(Context context, ViewGroup parent) {
		super(context, parent, R.layout.item_img_item);
		initView(rootView);
	}

	private void initView(View rootView) {
		relativeLayout_wholeItem = (RelativeLayout) rootView.findViewById(R.id.wholeItem);
		imageView_showPic = (ImageView) rootView.findViewById(R.id.showPic);
		textView_checkSelect = (TextView) rootView.findViewById(R.id.checkSelect);
		linearLayout_selectLl = (LinearLayout) rootView.findViewById(R.id.selectLl);
	}

	public LinearLayout getLinearLayout_selectLl() {
		return linearLayout_selectLl;
	}

	public String getTAG() {
		return TAG;
	}

	public RelativeLayout getRelativeLayout_wholeItem() {
		return relativeLayout_wholeItem;
	}

	public ImageView getImageView_showPic() {
		return imageView_showPic;
	}

	public TextView getTextView_checkSelect() {
		return textView_checkSelect;
	}
}