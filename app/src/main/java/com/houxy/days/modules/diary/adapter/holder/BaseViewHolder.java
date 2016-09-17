package com.houxy.days.modules.diary.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.houxy.days.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/12.
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder{


    public BaseViewHolder(Context context, ViewGroup root, int res) {
        super(LayoutInflater.from(context).inflate(res, root, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindData(){}

    public void bindData(T t){}

    public Context getContext() {
        return itemView.getContext();
    }

}
