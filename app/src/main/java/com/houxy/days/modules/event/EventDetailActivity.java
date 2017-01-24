package com.houxy.days.modules.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.adapter.ViewPagerAdapter;
import com.houxy.days.base.ToolbarActivity;
import com.houxy.days.bean.SpecialEvent;
import com.houxy.days.common.ACache;
import com.houxy.days.common.StatusBarUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/12/3.
 * <p>
 * Info :  EventDetailActivity.java
 */
public class EventDetailActivity extends ToolbarActivity {

    @Bind(R.id.viewPager) ViewPager mViewPager;

    public static Intent getIntentStartActivity(Context context, int pos) {

        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("pos", pos);
        return intent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        setStatusBar();
    }

    @SuppressWarnings("unchecked")
    private void initView() {

        int pos = getIntent().getIntExtra("pos", 0);
        setToolBarTitle("days");
        mToolBar.setBackgroundColor(0);
        final List<SpecialEvent> specialEvents = (ArrayList<SpecialEvent>) ACache.getDefault().getAsObject(C.EVENT_CACHE);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), specialEvents){
            @Override
            public Fragment getItem(Object o, int pos) {
                return EventDetailFragment.newInstance((SpecialEvent) o, pos);
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(pos);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    private void initView() {
//        mToolBar.setBackgroundColor(0);
//        setToolBarTitle("days");
//        SpecialEvent event = (SpecialEvent) getIntent().getSerializableExtra("event");
//        Log.d("TAG", "event : " + event.toString());
//        Calendar calendarAssign = TimeUtil.getAssignCalendar(event.getDate());
//
//        if (TimeUtil.isPastDay(calendarAssign)) {
//            mEventTitleTv.setText("" + event.getEventName() + " 已经");
//            mEventTitleTv.setBackgroundResource(R.color.past_event_bg_color);
//            mEventDetailTimeTv.setText("起始日: " + event.getDate() + "");
//        } else {
//            mEventTitleTv.setText("距离 " + event.getEventName() + " 还有");
//            mEventTitleTv.setBackgroundResource(R.color.colorPrimary);
//            mEventDetailTimeTv.setText("目标日: " + event.getDate() + "");
//        }
//        mEventDaysTv.setText(TimeUtil.getDaysApart(calendarAssign));
//
//
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_select){
            ToastUtils.show("oh");
            Logger.d("oh");
            return true;
        }else if(item.getItemId() == R.id.action_modify){
            ToastUtils.show("hh");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
