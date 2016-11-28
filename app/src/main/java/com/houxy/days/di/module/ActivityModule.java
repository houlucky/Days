package com.houxy.days.di.module;

import android.app.Activity;

import com.houxy.days.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  ActivityModule.java
 */
@Module
public class ActivityModule {

    private Activity mActivity;
    public ActivityModule(Activity activity){
        this.mActivity = activity;
    }

    @Provides @PerActivity
    public Activity provideActivity(){
        return mActivity;
    }
}
