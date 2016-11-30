package com.houxy.days.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.houxy.days.DaysApplication;
import com.houxy.days.di.component.AppComponent;


/**
 * Created by Houxy on 2016/9/2.
 */
public class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBar() {
    }

    public AppComponent getAppComponent(){
        return ((DaysApplication)getApplication()).getAppComponent();
    }

}
