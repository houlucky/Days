package com.houxy.days.modules.special.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houxy.days.R;
import com.houxy.days.base.BaseFragment;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.modules.special.adapter.EventAdapter;
import com.houxy.days.modules.special.bean.ResultList;
import com.houxy.days.modules.special.bean.SpecialEvent;
import com.houxy.days.widget.LoadMoreRecyclerView;
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

public class EventFragment extends BaseFragment {

    @Bind(R.id.load_more_recycler_view)
    LoadMoreRecyclerView loadMoreRecyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.progressBar)
    RelativeLayout progressBar;
    EventAdapter eventAdapter;
    private int rows = -1; //一共有多少条数据
    private int currentPage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        initView();
        loadEvent(1);
        return view;
    }

    private void initView() {

        eventAdapter = new EventAdapter();
        loadMoreRecyclerView.setAdapter(eventAdapter);
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.margin_10)));
        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadEvent(currentPage + 1);
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadEvent(1);
            }
        });


        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadEvent(final int page) {

        Observer<ResultList<SpecialEvent>> observer = new Observer<ResultList<SpecialEvent>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

                progressBar.setVisibility(View.GONE);
                ToastUtils.show(throwable.toString());
            }

            @Override
            public void onNext(ResultList<SpecialEvent> eventList) {
                if (null != eventList) {
                    currentPage = page;
                    if (currentPage == 1) {
                        eventAdapter.getEventList().clear();
                    }
                    eventAdapter.setEventList(eventList.list);
                    rows = eventList.rows;
                    loadMoreRecyclerView.notifyDataChange(currentPage, rows);
                }

                progressBar.setVisibility(View.GONE);
            }
        };

        getEventFromNetWork(page, observer);

    }

    private void getEventFromNetWork(int page, Observer<ResultList<SpecialEvent>> observer) {

        BmobQuery<SpecialEvent> diaryBmobQuery = new BmobQuery<>();
        diaryBmobQuery.addWhereEqualTo("eventOwner", BmobUser.getCurrentUser());
        //  在查询日记信息的同时也把author的信息查询出来
        diaryBmobQuery.include("eventOwner");
        //时间降序
        diaryBmobQuery.order("-createdAt");
        if (page >= 1) {
            diaryBmobQuery.setSkip((page - 1) * 10);
        }
        diaryBmobQuery.setLimit(10);
        Observable<List<SpecialEvent>> diaryObservable = diaryBmobQuery.findObjectsObservable(SpecialEvent.class);
        Observable<Integer> diaryCountObservable = diaryBmobQuery.countObservable(SpecialEvent.class);
        Observable.zip(diaryObservable, diaryCountObservable, new Func2<List<SpecialEvent>, Integer, ResultList<SpecialEvent>>() {
            @Override
            public ResultList<SpecialEvent> call(List<SpecialEvent> specialEvents, Integer integer) {
                ResultList<SpecialEvent> eventList = new ResultList<SpecialEvent>();
                eventList.setList(specialEvents);
                eventList.setRows(integer);
                return eventList;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
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
