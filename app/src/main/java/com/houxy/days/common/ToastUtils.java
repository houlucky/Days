package com.houxy.days.common;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.houxy.days.DaysApplication;

/**
 * Created by Houxy on 2016/9/5.
 */
public class ToastUtils {

    public static void show(String s){
        Toast.makeText(DaysApplication.getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int res) {
        Toast.makeText(DaysApplication.getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String s){
        Toast.makeText(DaysApplication.getContext(), s, Toast.LENGTH_LONG).show();
    }

    public static void showLong(@StringRes int res) {
        Toast.makeText(DaysApplication.getContext(), res, Toast.LENGTH_SHORT).show();
    }

}
