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
import com.houxy.days.common.utils.NetUtil;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.common.utils.ToastUtils;
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
        if(!TextUtils.isEmpty(SPUtil.getUsername()) && !TextUtils.isEmpty(SPUtil.getPassword()) ){
//            if(NetUtil.isNetworkConnected(SplashActivity.this)){
//                login( SPUtil.getUsername(), SPUtil.getPassword());
//            }else {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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

            }

            @Override
            public void onError(Throwable throwable) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
