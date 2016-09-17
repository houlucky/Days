package com.houxy.days.base;

import com.houxy.days.modules.main.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by Houxy on 2016/9/8.
 */
public class UserModel {

//    private static UserModel userModel = new UserModel();
//
//    public static UserModel getInstance(){
//        return userModel;
//    }
//
//    private UserModel(){}

    public static User getCurrentUser(){
        return BmobUser.getCurrentUser(User.class);
    }
}
