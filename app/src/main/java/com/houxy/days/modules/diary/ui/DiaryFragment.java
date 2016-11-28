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

import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.common.utils.DensityUtil;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.modules.diary.adapter.DiaryAdapter;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.diary.contract.DiaryContract;
import com.houxy.days.modules.diary.di.DaggerDiaryComponent;
import com.houxy.days.modules.diary.di.DiaryModule;
import com.houxy.days.modules.diary.presenter.DiaryPresenter;
import com.houxy.days.modules.main.ui.MainActivity;
import com.houxy.days.modules.special.adapter.EventAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/22.
 */

public class DiaryFragment extends BaseFragment implements DiaryContract.View{



    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.empty_rl)
    RelativeLayout emptyRl;
    @Inject DiaryPresenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, view);
        initView();
        DaggerDiaryComponent.builder()
                .activityComponent( ((MainActivity)getActivity()).getMainComponent() )
                .diaryModule(new DiaryModule(this))
                .build().inject(this);
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
//                        loadDiaries();
                        mPresenter.loadDiaries();
                    }
                }, 1000);
            }
        });
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.loadDiaries();
    }


    @Override
    public void showEmptyView(boolean bShow) {
        emptyRl.setVisibility(bShow ? View.VISIBLE : View.GONE);
        if(bShow){
            recyclerView.setAdapter(new EventAdapter());
        }
    }

    @Override
    public void setRefreshing(boolean bShow) {
        swRefresh.setRefreshing(bShow);
    }

    @Override
    public void showDiaries(final ArrayList<Diary> diaries) {
        DiaryAdapter diaryAdapter = new DiaryAdapter();
        recyclerView.setAdapter(diaryAdapter);
        emptyRl.setVisibility(View.GONE);
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
    }
}
