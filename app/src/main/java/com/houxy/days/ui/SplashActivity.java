package com.houxy.days.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;

import cn.bmob.v3.Bmob;

/**
 * Created by Houxy on 2016/9/2.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Bmob.initialize(this, C.APP_KEY);
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
