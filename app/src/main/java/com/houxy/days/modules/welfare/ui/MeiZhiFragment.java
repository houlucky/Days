package com.houxy.days.modules.welfare.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.common.ACache;
import com.houxy.days.common.RetrofitClient;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.modules.special.adapter.EmptyAdapter;
import com.houxy.days.modules.special.bean.ResultList;
import com.houxy.days.modules.welfare.adapter.MeiZhiAdapter;
import com.houxy.days.modules.welfare.bean.MeiZhi;
import com.houxy.days.widget.LoadMoreRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Func2;

/**
 * Created by Houxy on 2016/9/22.
 */

public class MeiZhiFragment extends BaseFragment {

    @Bind(R.id.load_more_recycler_view)
    LoadMoreRecyclerView loadMoreRecyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    MeiZhiAdapter meiZhiAdapter;
    EmptyAdapter emptyAdapter;
    @Bind(R.id.empty_rl)
    RelativeLayout emptyRl;
    private int rows = -1; //一共有多少条数据
    private int currentPage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        ButterKnife.bind(this, view);
        initView();
        loadMeiZhi(1);
        return view;
    }

    private void initView() {

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        loadMoreRecyclerView.setLayoutManager(manager);
        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.margin_10)));
        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMeiZhi(currentPage + 1);
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMeiZhi(1);
                    }
                }, 1000);
            }
        });
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swRefresh.setRefreshing(true);

    }

    private void loadMeiZhi(final int page) {

        Observer<ResultList<MeiZhi>> meiZhiObserver = new Observer<ResultList<MeiZhi>>() {
            @Override
            public void onCompleted() {
                emptyRl.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show(throwable.toString());
                if(null == emptyAdapter){
                    emptyAdapter = new EmptyAdapter();
                }
                loadMoreRecyclerView.setAdapter(emptyAdapter);
                emptyRl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(ResultList<MeiZhi> meiZhiList) {
                if (null != meiZhiList) {
                    currentPage = page;
                    if (currentPage == 1) {
                        if( null == meiZhiAdapter){
                            meiZhiAdapter = new MeiZhiAdapter();
                        }
                        loadMoreRecyclerView.setAdapter(meiZhiAdapter);
                        meiZhiAdapter.getMeiZhiList().clear();

                    }
                    meiZhiAdapter.setMeiZhiList(meiZhiList.list);
                    rows = meiZhiList.rows;
                    loadMoreRecyclerView.notifyDataChange(currentPage, rows);
                }
            }
        };

        getMeiZhiFromNetWork(page, meiZhiObserver);
    }

    private void getMeiZhiFromNetWork(int page, Observer<ResultList<MeiZhi>> observer){

        Observable<List<MeiZhi>> meiZhiObservable = RetrofitClient.getInstance().getMeiZhi(page);
        Observable<List<String>> welfareCountCountObservable = RetrofitClient.getInstance().getWelfareCount();
        Observable<ResultList<MeiZhi>> resultListObservable = Observable.zip(meiZhiObservable, welfareCountCountObservable,
                new Func2<List<MeiZhi>, List<String>, ResultList<MeiZhi>>() {
                    @Override
                    public ResultList<MeiZhi> call(List<MeiZhi> meiZhis, List<String> stringList) {
                        ResultList<MeiZhi> resultList = new ResultList<MeiZhi>();
                        resultList.setList(meiZhis);
                        resultList.setRows(stringList.size());
                        return resultList;
                    }
                });

        resultListObservable.doOnTerminate(new Action0() {
            @Override
            public void call() {
                swRefresh.setRefreshing(false);
            }
        }).subscribe(observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
