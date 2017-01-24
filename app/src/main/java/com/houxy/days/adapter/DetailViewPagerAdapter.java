package com.houxy.days.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.houxy.days.bean.SpecialEvent;
import com.houxy.days.modules.event.EventDetailFragment;

import java.util.List;

/**
 * Created by Houxy on 2016/12/8.
 * <p>
 * Info :  DetailViewPagerAdapter.java
 * 已经被{@link ViewPagerAdapter} 取代
 */
@Deprecated
public class DetailViewPagerAdapter extends FragmentStatePagerAdapter{

    private List<SpecialEvent> mEvents;
    private EventDetailFragment mFragment;

    public DetailViewPagerAdapter(FragmentManager fm, List<SpecialEvent> events) {
        super(fm);
        mEvents = events;
    }

    @Override
    public Fragment getItem(int position) {
        return EventDetailFragment.newInstance(mEvents.get(position), position);
    }


    @Override
    public int getCount() {
        return mEvents.size();
    }
}
