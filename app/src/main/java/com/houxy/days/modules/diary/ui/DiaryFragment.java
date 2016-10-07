package com.houxy.days.modules.diary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.common.ACache;
import com.houxy.days.common.utils.DensityUtil;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.modules.diary.adapter.DiaryAdapter;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.diary.bean.DiaryList;
import com.houxy.days.modules.special.adapter.EventAdapter;
import com.houxy.days.modules.special.bean.SpecialEvent;
import com.houxy.days.widget.LoadMoreRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Houxy on 2016/9/22.
 */

public class DiaryFragment extends BaseFragment {



    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.empty_rl)
    RelativeLayout emptyRl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, view);
        initView();
        loadDiaries();
        return view;
    }

    private void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(DensityUtil.dip2px(getContext(), 10)));
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDiaries();
                    }
                }, 1000);
            }
        });
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void loadDiaries() {
        if (null != ACache.getDefault().getAsObject(C.DIARY_CACHE)) {
            DiaryAdapter diaryAdapter = new DiaryAdapter();
            recyclerView.setAdapter(diaryAdapter);
            emptyRl.setVisibility(View.GONE);
            final List<Diary> diaries = (ArrayList<Diary>) ACache.getDefault().getAsObject(C.DIARY_CACHE);
            diaryAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = DiaryDetailActivity.getIntentStartActivity(getContext(), diaries.get(position), position);
                    startActivity(intent);
                }
            });

            if (!diaryAdapter.getDiaryList().isEmpty()) {
                diaryAdapter.getDiaryList().clear();
            }
            diaryAdapter.setDiaryList(diaries);
            diaryAdapter.notifyDataSetChanged();
        } else {
            recyclerView.setAdapter(new EventAdapter());
            emptyRl.setVisibility(View.VISIBLE);
        }
        swRefresh.setRefreshing(false);
    }
}
