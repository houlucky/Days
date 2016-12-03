package com.houxy.days.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.bean.Diary;
import com.houxy.days.widget.AnimRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/2.
 */
public class DiaryAdapter extends AnimRecyclerViewAdapter {

    private ArrayList<Diary> diaryList;
    private OnItemClickListener onItemClickListener;

    public DiaryAdapter(){

        diaryList = new ArrayList<>();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryHolder(parent.getContext(), parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(diaryList.get(position));
        showItemAnim(holder.itemView, position);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
