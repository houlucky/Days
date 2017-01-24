package com.houxy.days.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.bean.MeiZhi;
import com.houxy.days.modules.welfare.PhotoFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/24.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter{


    private ArrayList<MeiZhi> meiZhis;
    private FragmentManager mFragmentManager;
    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            PhotoFragment photoFragment = new PhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imgUrl", meiZhis.get(position).getUrl());
            photoFragment.setArguments(bundle);
            photoFragment.show(mFragmentManager, "TAG");
            Logger.d("okkkkkkkkkkkk");
        }
    };

    public MeiZhiAdapter(FragmentManager fragmentManager){
        meiZhis = new ArrayList<>();
        mFragmentManager = fragmentManager;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent.getContext(), parent, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(meiZhis.get(position));
    }

    @Override
    public int getItemCount() {
        return meiZhis.size();
    }


    public void setMeiZhiList(List<MeiZhi> meiZhiList) {
        if( null != meiZhiList){
            for(MeiZhi meiZhi: meiZhiList){
                this.meiZhis.add(meiZhi);
            }
        }
    }


    public ArrayList<MeiZhi> getMeiZhiList() {
        return meiZhis;
    }
}
