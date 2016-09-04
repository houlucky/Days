package com.houxy.days.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.houxy.days.R;
import com.houxy.days.adapter.DiaryAdapter;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.RecyclerViewUtil;
import com.houxy.days.widget.LoadMoreRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/2.
 */
public class DiaryActivity extends BaseActivity {

    @Bind(R.id.load_more_recycler_view)
    LoadMoreRecyclerView loadMoreRecyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        DiaryAdapter adapter = new DiaryAdapter();
        loadMoreRecyclerView.setAdapter(adapter);
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(R.dimen.margin_10));
        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        swRefresh.setRefreshing(true);
    }
}
