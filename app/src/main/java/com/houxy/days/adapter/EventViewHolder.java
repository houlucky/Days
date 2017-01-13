package com.houxy.days.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.bean.SpecialEvent;
import com.houxy.days.common.utils.TimeUtil;

import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by Houxy on 2016/9/21.
 */

public class EventViewHolder extends BaseViewHolder {


    @Bind(R.id.event_desc_tv)
    TextView eventDescTv;
    @Bind(R.id.event_days_tv)
    TextView eventDaysTv;
    @Bind(R.id.end_tv)
    TextView endTv;

    public EventViewHolder(Context context, ViewGroup root, OnItemClickListener onItemClickListener) {
        super(context, root, R.layout.item_recyclerview_event, onItemClickListener);
    }


    @Override
    public void bindData(Object o) {
        final SpecialEvent specialEvent = (SpecialEvent)o;
        Calendar calendarAssign = TimeUtil.getAssignCalendar(specialEvent.getDate());
        eventDaysTv.setText(TimeUtil.getDaysApart(calendarAssign));

        if( TimeUtil.isPastDay(calendarAssign) ){
            eventDescTv.setText("" + specialEvent.getEventName() + "已经");
            eventDaysTv.setBackgroundResource(R.color.past_event_bg_color);
            endTv.setBackgroundResource(R.color.past_event_bg_color_dark);
        }else {
            eventDescTv.setText("距离"+ specialEvent.getEventName() + "还有");
            eventDaysTv.setBackgroundResource(R.color.upcoming_event_bg_color);
            endTv.setBackgroundResource(R.color.upcoming_event_bg_color_dark);
        }

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = EventDetailActivity.getIntentStartActivity(getContext(), specialEvent);
//                getContext().startActivity(intent);
//            }
//        });

    }
}
