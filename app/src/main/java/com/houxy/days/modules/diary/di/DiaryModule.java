package com.houxy.days.modules.diary.di;

import com.houxy.days.modules.diary.DiaryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  DiaryModule.java
 */

@Module
public class DiaryModule {

    private DiaryContract.View mView;

    public DiaryModule(DiaryContract.View view){
        mView = view;
    }

    @Provides
    DiaryContract.View provideDiaryContractView(){
        return mView;
    }
}
