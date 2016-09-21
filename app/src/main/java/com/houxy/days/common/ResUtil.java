package com.houxy.days.common;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.houxy.days.DaysApplication;

/**
 * Created by Houxy on 2016/9/21.
 */

public class ResUtil {

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(DaysApplication.getContext(), id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(DaysApplication.getContext(), id);
    }
    public static String getString(@StringRes int id){
        return DaysApplication.getContext().getString(id);
    }
}
