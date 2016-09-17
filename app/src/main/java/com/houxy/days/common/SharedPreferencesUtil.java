package com.houxy.days.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.houxy.days.C;

/**
 * Created by Houxy on 2016/9/8.
 */
public class SharedPreferencesUtil {

    private final static String USER_INFO = "user_info";
    private final static String OTHER_INFO = "other_info";
    private final static String SETTING_INFO = "setting_info";
    private final static String MESSAGE_INFO = "message_info";

    private static Context context;

    public static void init(Context context) {
        SharedPreferencesUtil.context = context;
    }


    public static void setUsername(String username) {

        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.USERNAME, username);
        editor.apply();
    }

    public static String getUsername() {

        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.USERNAME, "");
    }

    public static void setPassword(String psw) {

        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.PASSWORD, psw);
        editor.apply();
    }

    public static String getPassword() {

        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.PASSWORD, "");
    }
}
