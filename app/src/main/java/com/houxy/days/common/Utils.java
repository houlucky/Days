package com.houxy.days.common;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.houxy.days.DaysApplication;

/**
 * Created by Houxy on 2016/9/3.
 */
public class Utils {

    public static void showSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager)DaysApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(EditText editText){
        InputMethodManager imm = (InputMethodManager) DaysApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //1.得到InputMethodManager对象
        //2.调用hideSoftInputFromWindow方法隐藏软键盘
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
    }
}
