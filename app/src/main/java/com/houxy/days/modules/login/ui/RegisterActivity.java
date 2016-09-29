package com.houxy.days.modules.login.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.base.ToolbarActivity;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.modules.main.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobRelation;
import rx.Subscriber;

/**
 * Created by Houxy on 2016/9/5.
 */
public class RegisterActivity extends ToolbarActivity {

    @Bind(R.id.username_et)
    TextInputEditText usernameEt;
    @Bind(R.id.password_et)
    TextInputEditText passwordEt;
    @Bind(R.id.confirm_password_et)
    TextInputEditText confirmPasswordEt;
    @Bind(R.id.register_btn)
    Button registerBtn;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        setToolBarTitle("注册");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(usernameEt.getText().toString(), passwordEt.getText().toString(),
                        confirmPasswordEt.getText().toString());
            }
        });

    }


    public void register(String userName, String password, String pwdAgain) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            ToastUtils.show("请确认密码");
            return;
        }
        if (!password.equals(pwdAgain)) {
            ToastUtils.show("两次输入的密码不一致");
            return;
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setSex(true);
        user.setNickName("");
        user.setMotto("小白");
        user.setAvatar("");
        user.setRelation(new BmobRelation());
        user.signUpObservable(user.getClass()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Log.d("TAG", "finish");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show(throwable.toString());
            }

            @Override
            public void onNext(User user) {
                Log.d("TAG", "onNext");
                ToastUtils.show("注册成功");
                finish();
            }
        });
    }
}
