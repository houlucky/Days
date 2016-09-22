package com.houxy.days.modules.main.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.houxy.days.R;
import com.houxy.days.common.ResUtil;

import java.util.List;

/**
 * Created by Houxy on 2016/9/22.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;


    public TabPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    

}
