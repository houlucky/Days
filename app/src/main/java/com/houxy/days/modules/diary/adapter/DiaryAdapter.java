package com.houxy.days.modules.diary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.houxy.days.common.Utils;
import com.houxy.days.modules.diary.adapter.holder.BaseViewHolder;
import com.houxy.days.modules.diary.adapter.holder.DiaryHolder;
import com.houxy.days.modules.diary.bean.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/2.
 */
public class DiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Diary> diaryList;

    public DiaryAdapter(){

        diaryList = new ArrayList<>();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(diaryList.get(position));
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }


    public void setDiaryList(List<Diary> diaryList) {
        if( null != diaryList){
            for(Diary diary: diaryList){
                this.diaryList.add(diary);
            }
        }
    }


    public ArrayList<Diary> getDiaryList() {
        return diaryList;
    }
}
