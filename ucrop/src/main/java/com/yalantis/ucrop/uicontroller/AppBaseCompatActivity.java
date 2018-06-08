package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

/**
 * Activity基类
 */

 abstract class AppBaseCompatActivity<T extends ViewObject> extends BaseActivity {
	private T viewObject;
	public T getViewObject() {
		if (viewObject == null) {
			viewObject = initViewObject(this);
		}
		return viewObject;
	}

	private boolean isResume;
	protected Handler handler;

	protected abstract T initViewObject(Context context);
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getViewObject().getRootView());
		// 隐藏输入法
		if (!isSoftInputShown()) {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		}
		handler=new Handler();
		initVariable();
		setListener();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}


	protected abstract void initVariable();
	protected abstract void setListener();

	protected boolean isSoftInputShown() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		isResume = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isResume = false;
	}

}
