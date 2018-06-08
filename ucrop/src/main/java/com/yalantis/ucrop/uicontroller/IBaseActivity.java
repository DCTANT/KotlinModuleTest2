package com.yalantis.ucrop.uicontroller;

import android.content.Context;

/**
 * Created by @cola .
 */
interface IBaseActivity {
	Context getContext();

	void dismissDialog();

	void showLoading(String msg);

	void showLoadingNoCancel(String msg);

	void showSuccess(String msg);

	void showErrorSmallSize(String msg);

	void showErrorLargeSize(String msg);

	void showErrorLargeSizeTwoLines(String msg);

	void showNoNetwork();

	void showNetworkFail(String msg);

	void showDialog(String msg);

}
