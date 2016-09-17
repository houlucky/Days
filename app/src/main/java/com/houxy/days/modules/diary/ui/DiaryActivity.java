package com.houxy.days.modules.diary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.RecyclerViewUtil;
import com.houxy.days.common.ToastUtils;
import com.houxy.days.modules.diary.adapter.DiaryAdapter;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.diary.bean.DiaryList;
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
 * Created by Houxy on 2016/9/2.
 */
public class DiaryActivity extends BaseActivity {

    @Bind(R.id.load_more_recycler_view)
    LoadMoreRecyclerView loadMoreRecyclerView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.back_ib)
    ImageButton backIb;
    @Bind(R.id.progressBar)
    RelativeLayout progressBar;

    private DiaryAdapter adapter;
    private int rows = -1; //一共有多少条数据
    private int currentPage = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        initView();
        loadDiaries(1);
    }

    private void initView() {
        adapter = new DiaryAdapter();
        loadMoreRecyclerView.setAdapter(adapter);
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(R.dimen.margin_10));
        loadMoreRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.margin_10)));
        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDiaries(currentPage + 1);
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDiaries(1);
            }
        });

        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadDiaries(final int page) {

        Observer<DiaryList<Diary>> observer = new Observer<DiaryList<Diary>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

                progressBar.setVisibility(View.GONE);
                ToastUtils.show(throwable.toString());
            }

            @Override
            public void onNext(DiaryList<Diary> diaryList) {
                if (null != diaryList) {
                    currentPage = page;
                    if (currentPage == 1) {
                        adapter.getDiaryList().clear();
                    }
                    adapter.setDiaryList(diaryList.list);
                    rows = diaryList.rows;
                    loadMoreRecyclerView.notifyDataChange(currentPage, rows);
                }

                progressBar.setVisibility(View.GONE);
            }
        };

        getDiariesFromNetWork(page, observer);

    }

    private void getDiariesFromNetWork(int page, Observer<DiaryList<Diary>> observer) {

        BmobQuery<Diary> diaryBmobQuery = new BmobQuery<>();
        diaryBmobQuery.addWhereEqualTo("author", BmobUser.getCurrentUser());
        //  在查询日记信息的同时也把author的信息查询出来
        diaryBmobQuery.include("author");
        //时间降序
        diaryBmobQuery.order("-createdAt");
        if (page >= 1) {
            diaryBmobQuery.setSkip((page - 1) * 10);
        }
        diaryBmobQuery.setLimit(10);
        Observable<List<Diary>> diaryObservable = diaryBmobQuery.findObjectsObservable(Diary.class);
        Observable<Integer> diaryCountObservable = diaryBmobQuery.countObservable(Diary.class);
        Observable.zip(diaryObservable, diaryCountObservable, new Func2<List<Diary>, Integer, DiaryList<Diary>>() {
            @Override
            public DiaryList<Diary> call(List<Diary> diaries, Integer integer) {
                DiaryList<Diary> diaryList = new DiaryList<Diary>();
                diaryList.setDiaryList(diaries);
                diaryList.setRows(integer);
                return diaryList;
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
}
