package com.houxy.days.modules.main;

import com.houxy.days.di.component.ActivityComponent;
import com.houxy.days.di.component.AppComponent;
import com.houxy.days.di.module.ActivityModule;
import com.houxy.days.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  MainComponent.java
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, ActivityModule.class})
public interface MainComponent extends ActivityComponent{

    void inject(MainActivity mainActivity);

}
