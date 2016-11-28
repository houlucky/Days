package com.houxy.days.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Houxy on 2016/11/27.
 *
 * Info :  AppModule.java
 */

@Module
public final class AppModule {

    private final Context mContext;

    public AppModule(Context context){
        mContext = context;
    }

    @Provides @Singleton
    Context provideContext(){ return mContext; }

}
