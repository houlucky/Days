package com.houxy.days.modules.diary.di;

import com.houxy.days.di.component.ActivityComponent;
import com.houxy.days.di.scope.PerFragment;
import com.houxy.days.modules.diary.ui.DiaryFragment;

import dagger.Component;

/**
 * Created by Houxy on 2016/11/28.
 * <p>
 * Info :  DiaryComponent.java
 */
@PerFragment
@Component(dependencies = ActivityComponent.class,modules = DiaryModule.class)
public interface DiaryComponent {

    void inject(DiaryFragment fragment);
}
