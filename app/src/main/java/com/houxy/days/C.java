package com.houxy.days;

import android.os.Environment;

/**
 * Created by Houxy on 2016/9/2.
 */
public class C {

    public static final String APP_KEY = "e7da7931cbfdaae2cb3f14dad369b53c";

    public static final int PICK_FROM_FILE = 0;
    public static final int PICK_FROM_CAMERA = 1;

    public static final String SAVE_PATH = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
            Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡;

    public static final String DEFAULT_DIARY_CACHE = "cache/diary/";
    public static final String DEFAULT_DAYS_CACHE = "cache/days/";
    public static final String DEFAULT_IMAGE_CACHE = "/days/image/";

    public static final String USERNAME = "username"; //用户账户
    public static final String TRUENAME = "truename"; //真实姓名
    public static final String PASSWORD = "password";
}
