package com.yalantis.ucrop.uicontroller.popwindow;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.ucrop.listener.OnAlbumChangeListener;
import com.yalantis.ucrop.uicontroller.Item_ChooseAlbum;


/**
 * Class :
 * Create : Chisalsoft-Dechert
 * Company: www.chisalsoft.co
 * Time : 2017/8/22
 */
public  class AlbumAdapter extends RecyclerView.Adapter<Item_ChooseAlbum> {
	private Popwindow_Album popwindow_album;
	private String TAG=AlbumAdapter.class.getName();
	private int clickPosition;
	private OnAlbumChangeListener onAlbumChangeListener;

	public AlbumAdapter(Popwindow_Album popwindow_album,OnAlbumChangeListener onAlbumChangeListener) {
		this.popwindow_album = popwindow_album;
		this.onAlbumChangeListener=onAlbumChangeListener;
	}

	@Override
	public Item_ChooseAlbum onCreateViewHolder(ViewGroup parent, int viewType) {
		return new Item_ChooseAlbum(popwindow_album.context, parent,onAlbumChangeListener);
	}

	@Override
	public void onBindViewHolder(final Item_ChooseAlbum holder, final int position) {
		holder.setData(popwindow_album.albumModels.get(position));
		holder.getLinearLayout_allItem().setTag(holder);
		holder.getLinearLayout_allItem().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w(TAG,"点击的位置为："+position);
				clickPosition=position;
				Item_ChooseAlbum item_chooseAlbum= (Item_ChooseAlbum) v.getTag();
				item_chooseAlbum.onAlbumChangeListener.albumChangeListener(item_chooseAlbum.getM_Album().getAlbumName(),position);
			}
		});

	}

	@Override
	public int getItemCount() {
		//返回list的长度
		return popwindow_album.albumModels.size();
	}

}
