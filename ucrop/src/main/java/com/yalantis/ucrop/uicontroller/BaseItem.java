package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CS002 on 2016/11/2.
 */

class BaseItem extends RecyclerView.ViewHolder {
	protected View rootView;
	protected Context context;

	public BaseItem(Context context, ViewGroup parent, int layoutRes) {
		super(LayoutInflater.from(context).inflate(layoutRes, parent, false));
		this.context = context;
		this.rootView = itemView;
	}

	public View getRootView() {
		return rootView;
	}
}