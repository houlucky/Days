package com.houxy.days.modules.special;

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
import com.houxy.days.common.ACache;
import com.houxy.days.common.utils.DensityUtil;
import com.houxy.days.common.utils.RecyclerViewUtil;
import com.houxy.days.adapter.EventAdapter;
import com.houxy.days.bean.SpecialEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/22.
 */

public class EventFragment extends BaseFragment {

    //    @Bind(R.id.load_more_recycler_view)
//    LoadMoreRecyclerView loadMoreRecyclerView;
//    @Bind(R.id.progressBar)
//    RelativeLayout progressBar;
//    private int rows = -1; //一共有多少条数据
//    private int currentPage = 0;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    @Bind(R.id.empty_rl)
    RelativeLayout emptyRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        initView();
        loadEvent();
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
                        loadEvent();
                    }
                }, 1000);
            }
        });
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//        loadMoreRecyclerView.setAdapter(eventAdapter);
//        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(getResources()
//                .getDimensionPixelSize(R.dimen.margin_10)));
//        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                loadEvent(currentPage + 1);
//            }
//        });
//        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swRefresh.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadEvent(1);
//                    }
//                }, 1000);
//            }
//        });
//        swRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        progressBar.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("unchecked")
    private void loadEvent() {

        if (null != ACache.getDefault().getAsObject(C.EVENT_CACHE)) {
            eventAdapter = new EventAdapter();
            recyclerView.setAdapter(eventAdapter);
            emptyRl.setVisibility(View.GONE);
            List<SpecialEvent> specialEvents = (ArrayList<SpecialEvent>) ACache.getDefault().getAsObject(C.EVENT_CACHE);
            if (!eventAdapter.getEventList().isEmpty()) {
                eventAdapter.getEventList().clear();
            }
            eventAdapter.setEventList(specialEvents);
            eventAdapter.notifyDataSetChanged();
        } else {
            recyclerView.setAdapter(new EventAdapter());
            emptyRl.setVisibility(View.VISIBLE);

        }
        swRefresh.setRefreshing(false);
    }

//    private void loadEvent(final int page) {
//
//        Observer<ResultList<SpecialEvent>> observer = new Observer<ResultList<SpecialEvent>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//                progressBar.setVisibility(View.GONE);
//                ToastUtils.show(throwable.toString());
//            }
//
//            @Override
//            public void onNext(ResultList<SpecialEvent> eventList) {
//                if (null != eventList) {
//                    currentPage = page;
//                    if (currentPage == 1) {
//                        eventAdapter.getEventList().clear();
//                    }
//                    eventAdapter.setEventList(eventList.list);
//                    rows = eventList.rows;
//                    loadMoreRecyclerView.notifyDataChange(currentPage, rows);
//                }
//
//                progressBar.setVisibility(View.GONE);
//            }
//        };
//
//        getEventFromNetWork(page, observer);
//
//    }
//
//    private void getEventFromNetWork(int page, Observer<ResultList<SpecialEvent>> observer) {
//
//        BmobQuery<SpecialEvent> diaryBmobQuery = new BmobQuery<>();
//        diaryBmobQuery.addWhereEqualTo("eventOwner", BmobUser.getCurrentUser());
//        //  在查询日记信息的同时也把author的信息查询出来
//        diaryBmobQuery.include("eventOwner");
//        //时间降序
//        diaryBmobQuery.order("-createdAt");
//        if (page >= 1) {
//            diaryBmobQuery.setSkip((page - 1) * 10);
//        }
//        diaryBmobQuery.setLimit(10);
//        Observable<List<SpecialEvent>> diaryObservable = diaryBmobQuery.findObjectsObservable(SpecialEvent.class);
//        Observable<Integer> diaryCountObservable = diaryBmobQuery.countObservable(SpecialEvent.class);
//        Observable.zip(diaryObservable, diaryCountObservable, new Func2<List<SpecialEvent>, Integer, ResultList<SpecialEvent>>() {
//            @Override
//            public ResultList<SpecialEvent> call(List<SpecialEvent> specialEvents, Integer integer) {
//                ResultList<SpecialEvent> eventList = new ResultList<SpecialEvent>();
//                eventList.setList(specialEvents);
//                eventList.setRows(integer);
//                return eventList;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(new Action0() {
//                    @Override
//                    public void call() {
//                        swRefresh.setRefreshing(false);
//                    }
//                }).subscribe(observer);
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
