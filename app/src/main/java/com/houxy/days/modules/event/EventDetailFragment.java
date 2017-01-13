package com.houxy.days.modules.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.bean.SpecialEvent;
import com.houxy.days.common.utils.TimeUtil;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/12/8.
 * <p>
 * Info :  EventDetailFragment.java
 */
public class EventDetailFragment extends BaseFragment {


    @Bind(R.id.eventTitleTv) TextView mEventTitleTv;
    @Bind(R.id.eventDaysTv) TextView mEventDaysTv;
    @Bind(R.id.eventDetailTimeTv) TextView mEventDetailTimeTv;
    private SpecialEvent mEvent;

    public static EventDetailFragment newInstance(SpecialEvent event, int paramInt) {
        EventDetailFragment localDetailFragment = new EventDetailFragment();
        Bundle localBundle = new Bundle();
//        localBundle.putInt("dateId", paramDateCardItem.getId());
//        localBundle.putString("date", String.valueOf(paramDateCardItem.getDays()));
//        localBundle.putString("dateTitle", paramDateCardItem.getTitle());
//        localBundle.putLong("dueDate", paramDateCardItem.getDueDate().getTime());
//        if (paramDateCardItem.getHasEndDate())
//            localBundle.putLong("endDate", paramDateCardItem.getEndDate().getTime());
//        localBundle.putBoolean("isOutOfDate", paramDateCardItem.getIsOutOfDate());
//        localBundle.putBoolean("isOutOfEndDate", paramDateCardItem.getIsOutOfEndDate());
//        localBundle.putBoolean("isLunarCalendar", paramDateCardItem.isLunarCalendar());
//        localBundle.putInt("repeatType", paramDateCardItem.getRepeatType());
        localBundle.putInt("index", paramInt);
        localBundle.putSerializable("event", event);
        localDetailFragment.setArguments(localBundle);
        return localDetailFragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mEvent = (SpecialEvent) args.getSerializable("event");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        Calendar calendarAssign = TimeUtil.getAssignCalendar(mEvent.getDate());
        if (TimeUtil.isPastDay(calendarAssign)) {
            mEventTitleTv.setText("" + mEvent.getEventName() + " 已经");
            mEventTitleTv.setBackgroundResource(R.color.past_event_bg_color);
            mEventDetailTimeTv.setText("起始日: " + mEvent.getDate() + "");
        } else {
            mEventTitleTv.setText("距离 " + mEvent.getEventName() + " 还有");
            mEventTitleTv.setBackgroundResource(R.color.colorPrimary);
            mEventDetailTimeTv.setText("目标日: " + mEvent.getDate() + "");
        }
        mEventDaysTv.setText(TimeUtil.getDaysApart(calendarAssign));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
