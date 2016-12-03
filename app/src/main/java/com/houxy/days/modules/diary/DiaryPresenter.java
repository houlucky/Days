package com.houxy.days.modules.diary;

import com.houxy.days.C;
import com.houxy.days.common.ACache;
import com.houxy.days.bean.Diary;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  DiaryPresenter.java
 */
public class DiaryPresenter implements DiaryContract.Presenter{


    private DiaryContract.View mView;

    @Inject
    public DiaryPresenter(DiaryContract.View view){
        this.mView = view;
    }

    @Override
    public void start() {
        mView.setRefreshing(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadDiaries() {
        if (null != ACache.getDefault().getAsObject(C.DIARY_CACHE)) {
            ArrayList<Diary> diaries = (ArrayList<Diary>) ACache.getDefault().getAsObject(C.DIARY_CACHE);
            mView.showEmptyView(false);
            mView.showDiaries(diaries);
        }else {
            mView.showEmptyView(true);
        }
        mView.setRefreshing(false);
    }
}
