package com.yalantis.ucrop.uicontroller;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Dechert on 2017-09-14.
 */

public abstract class UcropRecyclerAdapter<Item extends BaseItem, Model> extends RecyclerView.Adapter {
    private final String TAG=UcropRecyclerAdapter.class.getName();
    private ArrayList<Model> arrayList;
    private Context context;

    public UcropRecyclerAdapter(Context context, ArrayList<Model> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return setViewHolder(parent);
    }

    public abstract Item setViewHolder(ViewGroup parent);

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		Model m=arrayList.get(position);
        setData((Item) holder,position,m);
    }

    public abstract void setData(Item v,int position,Model m);

    public ArrayList<Model> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Model> arrayList) {
        this.arrayList = arrayList;
    }

    public void notifyDataChange(ArrayList<Model> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }


}
