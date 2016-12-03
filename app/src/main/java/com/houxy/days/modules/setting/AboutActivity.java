package com.houxy.days.modules.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Houxy on 2016/11/30.
 * <p>
 * Info :  AboutActivity.java
 */
public class AboutActivity extends BaseActivity {


    @Bind(R.id.bannner) ImageView mBannner;
    @Bind(R.id.tv_version) TextView mTvVersion;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar) AppBarLayout mAppBar;
    @Bind(R.id.bt_code) Button mBtCode;
    @Bind(R.id.bt_blog) Button mBtBlog;
    @Bind(R.id.bt_pay) Button mBtPay;
    @Bind(R.id.bt_share) Button mBtShare;
    @Bind(R.id.bt_update) Button mBtUpdate;
    @Bind(R.id.bt_bug) Button mBtBug;
    @Bind(R.id.coord) CoordinatorLayout mCoord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        checkNotNull(getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
