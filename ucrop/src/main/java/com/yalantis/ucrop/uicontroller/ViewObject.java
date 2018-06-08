package com.yalantis.ucrop.uicontroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by CS002 on 2016/12/22.
 */

public abstract class ViewObject {
	protected View rootView;
	protected Context context;
	private int layoutRes;

	public ViewObject(Context context, int layoutRes) {
		this.layoutRes = layoutRes;
		this.context = context;
		rootView = LayoutInflater.from(context).inflate(layoutRes,null);
	}

	public View getRootView() {
		return rootView;
	}

	public int getLayoutRes() {
		return layoutRes;
	}
}
