package com.houxy.days.modules.diary.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.base.i.OnItemLongClickListener;

import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/12.
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BaseViewHolder(Context context, ViewGroup root, int res) {
        super(LayoutInflater.from(context).inflate(res, root, false));
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(Context context, ViewGroup root, int res, OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(res, root, false));
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    public BaseViewHolder(Context context, ViewGroup root, int res, OnItemClickListener onItemClickListener,
                          OnItemLongClickListener onItemLongClickListener) {
        super(LayoutInflater.from(context).inflate(res, root, false));
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(){}

    public void bindData(T t){}

    public Context getContext() {
        return itemView.getContext();
    }

    @Override public void onClick(View v) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    @Override public boolean onLongClick(View v) {
        if (onItemLongClickListener != null){
            onItemLongClickListener.onItemLongClick(getAdapterPosition());
        }
        return true;
    }

}
