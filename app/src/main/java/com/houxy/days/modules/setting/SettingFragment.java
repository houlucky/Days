package com.houxy.days.modules.setting;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.houxy.days.C;
import com.houxy.days.DaysApplication;
import com.houxy.days.R;
import com.houxy.days.common.utils.FileUtil;
import com.houxy.days.common.utils.SPUtil;
import com.houxy.days.common.utils.SimpleSubscriber;
import com.houxy.days.common.utils.rx.RxHelper;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Houxy on 2016/11/30.
 * <p>
 * Info :  SettingFragment.java
 */
public class SettingFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener{


    private Preference mClearCache;
    private CheckBoxPreference mItemAnimation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mClearCache = findPreference(C.CLEAR_CACHE);
        mClearCache.setSummary(FileUtil.getAutoFileOrFilesSize(DaysApplication.cacheDir + "/days/image/"));
        mClearCache.setOnPreferenceClickListener(this);
        mItemAnimation = (CheckBoxPreference)findPreference(C.ITEM_ANIMATION);
        mItemAnimation.setChecked(SPUtil.getItemAnimation());
        mItemAnimation.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference == mItemAnimation){
            SPUtil.setItemAnimation(true);
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mClearCache){
            Observable.just(FileUtil.delete(new File(DaysApplication.cacheDir + "/days/image/")))
                    .filter(new Func1<Boolean, Boolean>() {
                        @Override
                        public Boolean call(Boolean aBoolean) {
                            return aBoolean;
                        }
                    }).compose(RxHelper.<Boolean>rxSchedulerHelper()).subscribe(new SimpleSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean a) {
                        mClearCache.setSummary(FileUtil.getAutoFileOrFilesSize(DaysApplication.cacheDir + "/days/image/"));
                        Snackbar.make(checkNotNull(getView()), "缓存已清除", Snackbar.LENGTH_SHORT).show();
                    }
            });
        }
        return false;
    }
}
