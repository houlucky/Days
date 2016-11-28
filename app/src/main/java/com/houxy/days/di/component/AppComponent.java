package com.houxy.days.di.component;

import android.content.Context;

import com.houxy.days.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  AppComponent.java
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    //要想依赖此组件的component获取到context，必须显示的暴露
    Context context();
}
