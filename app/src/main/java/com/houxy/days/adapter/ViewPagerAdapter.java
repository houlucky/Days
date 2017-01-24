package com.houxy.days.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Houxy on 2017/1/24.
 * <p>
 * Info :  ViewPager的万能适配器
 */
public abstract class ViewPagerAdapter<T> extends FragmentStatePagerAdapter{

    private List<T> mList;

    public ViewPagerAdapter(FragmentManager manager, List<T> list){
        super(manager);
        mList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return getItem(mList.get(position), position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public abstract Fragment getItem(T t, int pos);
}
