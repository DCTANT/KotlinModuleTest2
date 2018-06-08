package com.yalantis.ucrop.uicontroller.popwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.listener.OnAlbumChangeListener;
import com.yalantis.ucrop.model.M_Album;

import java.util.ArrayList;

/**
 * Class :
 * Create : Chisalsoft-Dechert
 * Company: www.chisalsoft.co
 * Time : 2017/8/22
 */
public class Popwindow_Album extends PopupWindow {
	protected LinearLayout linearLayout_allView;
	private String TAG = Popwindow_Album.class.getName();

	private final View contentView;
	public final Context context;
	protected View rootView;
	protected RecyclerView recyclerView_albumList;
	public AlbumAdapter albumAdapter;
	public ArrayList<M_Album> albumModels;
	private OnAlbumChangeListener onAlbumChangeListener;

	public Popwindow_Album(Context context, ArrayList<M_Album> albumModels, OnAlbumChangeListener onAlbumChangeListener) {
		super(context);
		this.context = context;
		contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_album, null, false);
		Log.e(TAG, "进入了popwindown");
		setContentView(contentView);
		initView(contentView);
		setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
		this.albumModels = albumModels;
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		this.onAlbumChangeListener=onAlbumChangeListener;
		albumAdapter = new AlbumAdapter(this,onAlbumChangeListener);
		recyclerView_albumList.setLayoutManager(new LinearLayoutManager(context));
		recyclerView_albumList.setAdapter(albumAdapter);
		setBackgroundDrawable(new ColorDrawable(0x44000000));
		setOutsideTouchable(false);
		setFocusable(true);
		setOnClick();
	}

	private void setOnClick() {
		linearLayout_allView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void initView(View rootView) {
		recyclerView_albumList = (RecyclerView) rootView.findViewById(R.id.albumList);
		linearLayout_allView = (LinearLayout) rootView.findViewById(R.id.allView);
	}

	public AlbumAdapter getAlbumAdapter() {
		return albumAdapter;
	}

	public void setAlbumAdapter(AlbumAdapter albumAdapter) {
		this.albumAdapter = albumAdapter;
	}

	public ArrayList<M_Album> getM_Albums() {
		return albumModels;
	}

	public void setM_Albums(ArrayList<M_Album> albumModels) {
		this.albumModels = albumModels;
	}
}
