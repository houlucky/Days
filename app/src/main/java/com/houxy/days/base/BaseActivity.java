package com.houxy.days.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.houxy.days.DaysApplication;
import com.houxy.days.R;
import com.houxy.days.common.StatusBarUtil;
import com.houxy.days.common.utils.ResUtil;
import com.houxy.days.di.component.AppComponent;


/**
 * Created by Houxy on 2016/9/2.
 *
 * Info :  BaseActivity.java
 */
public class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, ResUtil.getColor(R.color.colorPrimary));
    }

    public AppComponent getAppComponent(){
        return ((DaysApplication)getApplication()).getAppComponent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
