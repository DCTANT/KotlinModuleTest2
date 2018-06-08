package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yalantis.ucrop.util.dialog.ProgressDialog;


/**
 * Activity工具类
 */

abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

	private final static int Delay_Finish = 2000;
	protected Context context;
	protected ProgressDialog progressDialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		progressDialog = new ProgressDialog(context);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public void dismissDialog() {
		if (context != null && !isFinishing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void showLoading(String msg) {
		if (context != null && !isFinishing()) {
			progressDialog.showLoading(msg);
		}
	}

	public void showLoading(int stringRes) {
		showLoading(getString(stringRes));
	}

	@Override
	public void showLoadingNoCancel(String msg) {

	}

	@Override
	public void showSuccess(String msg) {
		if (context != null && !isFinishing()) {
			progressDialog.showSuccess(msg);
		}
	}

	@Override
	public void showErrorSmallSize(String msg) {
		if (context != null && !isFinishing()) {
			progressDialog.showErrorSmallSize(msg);
		}
	}

	@Override
	public void showErrorLargeSize(String msg) {

	}

	@Override
	public void showErrorLargeSizeTwoLines(String msg) {

	}

	public void setMesLoading(String msg) {
		progressDialog.setMes(msg);
	}

	@Override
	public void showNoNetwork() {
		if (context != null && !isFinishing()) {
			progressDialog.showNoNetwork();
		}
	}

	@Override
	public void showNetworkFail(String msg) {
		if (context != null && !isFinishing()) {
			progressDialog.showNetworkFail();
		}
	}

	@Override
	public void showDialog(String msg) {
		if (context != null && !isFinishing()) {
			progressDialog.show(msg);
		}
	}

	protected void delayFinish() {
		delayFinish(null);
	}

	protected void delayFinish(final Runnable runnable) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (runnable != null) {
					runnable.run();
				}
				if (progressDialog != null && progressDialog.isShowing()) {
					dismissDialog();
				}
				finish();
			}
		}, Delay_Finish);
	}

	@Override
	protected void onDestroy() {
		if (context != null && progressDialog != null && !isFinishing()) {
			progressDialog.dismiss();
		}
		super.onDestroy();
	}

	public void back(View view) {
		onBackPressed();
	}
}
