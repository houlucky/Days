package com.houxy.days.modules.special.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.common.ResUtil;
import com.houxy.days.common.TimeUtil;
import com.houxy.days.modules.diary.adapter.holder.BaseViewHolder;
import com.houxy.days.modules.special.bean.SpecialEvent;

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

    public EventViewHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_recyclerview_event);
    }


    @Override
    public void bindData(Object o) {
        SpecialEvent specialEvent = (SpecialEvent)o;
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

    }
}
