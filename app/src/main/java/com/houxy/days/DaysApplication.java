package com.houxy.days;

import android.app.Application;

import com.houxy.days.common.SharedPreferencesUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by Houxy on 2016/9/2.
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

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //初始化
        Logger.init("houlucky");
        SharedPreferencesUtil.init(this);
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }
}
