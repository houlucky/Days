package com.houxy.days.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.bean.User;
import com.houxy.days.modules.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Houxy on 2016/9/2.
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.username_et)
    TextInputEditText usernameEt;
    @Bind(R.id.password_et)
    TextInputEditText passwordEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        intiView();
    }

    private void intiView() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.register_btn:
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.login_btn:
                        login(usernameEt.getText().toString(), passwordEt.getText().toString());
                        break;
                    default:
                        break;
                }
            }
        };

        registerBtn.setOnClickListener(onClickListener);
        loginBtn.setOnClickListener(onClickListener);
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
                Log.d("TAG", "login_com");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show(throwable.toString());
            }

            @Override
            public void onNext(User user) {
                Log.d("TAG", "login_next");
                SPUtil.setUsername(userName);
                SPUtil.setPassword(password);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
