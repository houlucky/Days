package com.houxy.days.modules.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.SharedPreferencesUtil;
import com.houxy.days.common.ToastUtils;
import com.houxy.days.modules.main.bean.User;
import com.houxy.days.modules.main.ui.MainActivity;

import cn.bmob.v3.Bmob;
import rx.Subscriber;

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
                if(!TextUtils.isEmpty(SharedPreferencesUtil.getUsername()) && !TextUtils.isEmpty(SharedPreferencesUtil.getPassword()) ){
                    login( SharedPreferencesUtil.getUsername(), SharedPreferencesUtil.getPassword());
                }else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 10);
    }


    private void login(final String userName, final String password) {

        if(TextUtils.isEmpty(userName)){
            ToastUtils.show("请输入用户名");
            return;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtils.show("请输入密码");
            return;
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.loginObservable(user.getClass()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Log.d("TAG", "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show(throwable.toString());
            }

            @Override
            public void onNext(User user) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
