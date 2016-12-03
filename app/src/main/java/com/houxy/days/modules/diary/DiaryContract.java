package com.houxy.days.modules.diary;

import com.houxy.days.base.BasePresenter;
import com.houxy.days.base.BaseView;
import com.houxy.days.bean.Diary;

import java.util.ArrayList;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  DiaryContract.java
 */
public interface DiaryContract {

    interface View extends BaseView{

        void showEmptyView(boolean bShow);

        void setRefreshing(boolean bShow);

        void showDiaries(ArrayList<Diary> diaries);
    }

    interface Presenter extends BasePresenter{
        void loadDiaries();
    }
}
