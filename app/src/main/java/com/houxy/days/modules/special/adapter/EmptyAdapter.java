package com.houxy.days.modules.special.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.widget.AnimRecyclerViewAdapter;

/**
 * Created by Houxy on 2016/9/25.
 */

public class EmptyAdapter extends RecyclerView.Adapter{

    public EmptyAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
