package com.houxy.days.common.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.houxy.days.C;
import com.houxy.days.DaysApplication;

/**
 * Created by Houxy on 2016/9/8.
 */
public class SPUtil {

    public SPUtil() {
        throw new AssertionError("no instance");
    }

    private static class Holder {
        private static SharedPreferences sp = DaysApplication.getContext().getSharedPreferences(C.SP_DATA, Activity.MODE_PRIVATE);
    }

    private static SharedPreferences getSP() {
        return Holder.sp;
    }

    public static void setUsername(String username) {
        getSP().edit().putString(C.USERNAME, username).apply();
    }

    public static String getUsername() {
        return getSP().getString(C.USERNAME, "");
    }

    public static void setPassword(String psw) {
        getSP().edit().putString(C.PASSWORD, psw).apply();
    }

    public static String getPassword() {
        return getSP().getString(C.PASSWORD, "");
    }

    public static void setToken(String token) {
        getSP().edit().putString(C.TOKEN_HEADER, token).apply();
    }

    public static String getToken() {
        return getSP().getString(C.TOKEN_HEADER, "");
    }

    public static void setMeiZhiCache(boolean hasMeiZhi) {
        getSP().edit().putBoolean(C.HAS_MEIZHI_CACHE, hasMeiZhi).apply();
    }

    public static boolean getMeiZhiCache() {
        return getSP().getBoolean(C.HAS_MEIZHI_CACHE, false);
    }

    public static void setItemAnimation(boolean hasMeiZhi) {
        getSP().edit().putBoolean(C.ITEM_ANIMATION, hasMeiZhi).apply();
    }

    public static boolean getItemAnimation() {
        return getSP().getBoolean(C.ITEM_ANIMATION, false);
    }

    public static void setClearCache(boolean hasMeiZhi) {
        getSP().edit().putBoolean(C.ITEM_ANIMATION, hasMeiZhi).apply();
    }

    public static boolean getClearCache() {
        return getSP().getBoolean(C.ITEM_ANIMATION, false);
    }
}
