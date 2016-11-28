package com.houxy.days.modules.welfare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.modules.welfare.bean.MeiZhi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/24.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter{


    private ArrayList<MeiZhi> meiZhis;

    public MeiZhiAdapter(){
        meiZhis = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent.getContext(), parent);
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
