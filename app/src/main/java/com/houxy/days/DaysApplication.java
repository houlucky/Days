package com.houxy.days;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.houxy.days.common.RetrofitClient;
import com.houxy.days.di.component.AppComponent;
import com.houxy.days.di.component.DaggerAppComponent;
import com.houxy.days.di.module.AppModule;
import com.orhanobut.logger.Logger;

//import com.houxy.days.di.component.DaggerAppComponent;

/**
 * Created by Houxy on 2016/9/2.
 *
 * Info :
 */
public class DaysApplication extends Application{

    private static DaysApplication INSTANCE;
    public static String cacheDir = "";

    public static DaysApplication getContext() {
        return INSTANCE;
    }

    private void setInstance(DaysApplication app) {
        setDaysApplication(app);
    }

    private static void setDaysApplication(DaysApplication a) {
        DaysApplication.INSTANCE = a;
    }

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //初始化
        Logger.init("houlucky");
        RetrofitClient.init();
        Stetho.initializeWithDefaults(this);
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
