package com.houxy.days.modules.special.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.modules.diary.adapter.holder.BaseViewHolder;
import com.houxy.days.modules.diary.adapter.holder.DiaryHolder;
import com.houxy.days.modules.special.bean.SpecialEvent;
import com.houxy.days.widget.AnimRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/21.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SpecialEvent> specialEvents;

    public EventAdapter(){
        specialEvents = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(specialEvents.get(position));
//        showItemAnim(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return specialEvents.size();
    }


    public void setEventList(List<SpecialEvent> specialEventList) {
        if( null != specialEventList){
            for(SpecialEvent specialEvent: specialEventList){
                this.specialEvents.add(specialEvent);
            }
        }
    }


    public ArrayList<SpecialEvent> getEventList() {
        return specialEvents;
    }
}