package com.houxy.days.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.bean.SpecialEvent;
import com.houxy.days.widget.AnimRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Houxy on 2016/9/21.
 */

public class EventAdapter extends AnimRecyclerViewAdapter {

    private ArrayList<SpecialEvent> specialEvents;
    private OnItemClickListener mOnItemClickListener;

    public EventAdapter(){
        specialEvents = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(parent.getContext(), parent, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(specialEvents.get(position));
        showItemAnim(holder.itemView, position);
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

//    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(int position) {
//            Intent intent = EventDetailActivity.getIntentStartActivity(getContext(), specialEvent);
//            getContext().startActivity(intent);
//        }
//    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}