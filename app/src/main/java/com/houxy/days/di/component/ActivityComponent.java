package com.houxy.days.di.component;

import android.app.Activity;

import com.houxy.days.di.module.ActivityModule;
import com.houxy.days.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  ActivityComponent.java
 */
@PerActivity
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
