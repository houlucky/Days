package com.houxy.days.modules.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.adapter.EmptyAdapter;
import com.houxy.days.adapter.MeiZhiAdapter;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.bean.MeiZhi;
import com.houxy.days.bean.Result;
import com.houxy.days.common.ACache;
import com.houxy.days.common.RetrofitClient;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Action0;

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
    }

    private void loadMeiZhi(final int page) {

        Observer<Result<List<MeiZhi>>> meiZhiObserver = new Observer<Result<List<MeiZhi>>>() {
            @Override
            public void onCompleted() {
                emptyRl.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable throwable) {
                RetrofitClient.disposeFailureInfo(throwable);
                if(!SPUtil.getMeiZhiCache()){
                    if(null == emptyAdapter){
                        emptyAdapter = new EmptyAdapter();
                    }
                    loadMoreRecyclerView.setAdapter(emptyAdapter);
                    emptyRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNext(Result<List<MeiZhi>> meiZhiResult) {
                if (null != meiZhiResult) {
                    currentPage = page;
                    if (currentPage == 1) {
//                        RecyclerView empty after SwipeRefreshLayout pull onRefresh event
                        if(null == loadMoreRecyclerView.getAdapter()){
                            meiZhiAdapter = new MeiZhiAdapter(getFragmentManager());
                            loadMoreRecyclerView.setAdapter(meiZhiAdapter);
                        }
                        meiZhiAdapter.getMeiZhiList().clear();
                        ArrayList<MeiZhi> meiZhis = new ArrayList<>();
                        meiZhis.addAll(meiZhiResult.getResults());
                        ACache.getDefault().put(C.MEIZHI_CACHE, meiZhis);
                    }
                    meiZhiAdapter.setMeiZhiList(meiZhiResult.getResults());
                    loadMoreRecyclerView.notifyDataChange(meiZhiResult.isError());
                }
            }
        };

        if( page == 1){
            getMeiZhiFromCache(meiZhiObserver);
        }

        getMeiZhiFromNetWork(page, meiZhiObserver);
    }

    private void getMeiZhiFromNetWork(int page, Observer<Result<List<MeiZhi>>> observer){

        RetrofitClient.getInstance()
                .getMeiZhi(page)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(swRefresh.isRefreshing()){
                            swRefresh.setRefreshing(false);
                        }
                    }
                }).subscribe(observer);
    }

    private void getMeiZhiFromCache(Observer<Result<List<MeiZhi>>> observer){
        ArrayList<MeiZhi> meiZhis;
        meiZhis = (ArrayList<MeiZhi>) ACache.getDefault().getAsObject(C.MEIZHI_CACHE);
        if( null != meiZhis){
            Result<List<MeiZhi>> result = new Result<>();
            result.setError(true);
            result.setResults(meiZhis);
            SPUtil.setMeiZhiCache(true);
            Observable.just(result).distinct().subscribe(observer);
        }else {
            SPUtil.setMeiZhiCache(false);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
