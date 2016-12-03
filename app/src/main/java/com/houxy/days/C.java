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

    public static final String DIARY_CACHE = "days/diary/";
    public static final String EVENT_CACHE = "days/event/";
    public static final String IMAGE_CACHE = "days/image/";
    public static final String MEIZHI_CACHE = "days/meizhi/";

    public static final String USERNAME = "username"; //用户账户
    public static final String TRUENAME = "truename"; //真实姓名
    public static final String PASSWORD = "password";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String HAS_MEIZHI_CACHE = "";
    public static final String CLEAR_CACHE = "clear_cache";
    public static final String ITEM_ANIMATION = "item_animation";

//    public static final int CurrentItem_Diary = 0;
//    public static final int CurrentItem_Event = 1;
//    public static final int CurrentItem_MeiZhi = 2;

    public static final String CACHE_PATH = "DaysCache";
    public static final String SP_DATA = "sp_data";
}
