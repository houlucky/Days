package com.houxy.days.modules.welfare.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;

/**
 * Created by Houxy on 2016/9/22.
 */

public class MeiZhiFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        return view;
    }
}
