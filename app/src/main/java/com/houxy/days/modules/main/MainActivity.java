package com.houxy.days.modules.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.StatusBarUtil;
import com.houxy.days.common.utils.DialogUtil;
import com.houxy.days.common.utils.ResUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.di.module.ActivityModule;
import com.houxy.days.modules.diary.ui.DiaryActivity;
import com.houxy.days.modules.diary.ui.DiaryEditActivity;
import com.houxy.days.modules.diary.ui.DiaryFragment;
import com.houxy.days.modules.login.LoginActivity;
import com.houxy.days.adapter.TabPagerAdapter;
import com.houxy.days.bean.User;
import com.houxy.days.modules.main.behavior.FabSwitchAnimation;
import com.houxy.days.modules.setting.AboutActivity;
import com.houxy.days.modules.setting.SettingActivity;
import com.houxy.days.modules.special.EventEditActivity;
import com.houxy.days.modules.special.EventFragment;
import com.houxy.days.modules.welfare.MeiZhiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.houxy.days.R.id.fab;
import static com.houxy.days.R.id.tabLayout;
import static com.houxy.days.R.id.viewPager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.nav_view) NavigationView mNavView;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(viewPager) ViewPager mViewPager;
    @Bind(fab) FloatingActionButton mFab;
    @Bind(tabLayout) TabLayout mTabLayout;
    private MainComponent mMainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        mMainComponent = DaggerMainComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .mainModule(new MainModule()).build();
        mMainComponent.inject(this);
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }


    private void initView() {

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (null != mDrawerLayout) {
            mDrawerLayout.addDrawerListener(toggle);
        }
        toggle.syncState();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, DiaryEditActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, EventEditActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        ToastUtils.show("美美哒~");
                        break;
                    default:
                        break;
                }
            }
        };
        mFab.setOnClickListener(onClickListener);

        initNavigationView();
        initFragments();

    }


    private void initFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DiaryFragment());
        fragments.add(new EventFragment());
        fragments.add(new MeiZhiFragment());
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                FabSwitchAnimation.start(mFab, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (null != navigationView) {
            navigationView.setNavigationItemSelectedListener(this);
            Menu menu = navigationView.getMenu();
            final View headerLayout = navigationView.getHeaderView(0);
            TextView mottoTv = (TextView) headerLayout.findViewById(R.id.motto_tv);
            TextView usernameTv = (TextView) headerLayout.findViewById(R.id.username_tv);
            ImageView avatarIv = (ImageView) headerLayout.findViewById(R.id.avatar_iv);

            View.OnClickListener clickListener1 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.avatar_iv:
                            ToastUtils.show("头像");
                            break;
                        case R.id.motto_tv:
                            break;
                        case R.id.username_tv:
                            break;
                        default:
                            break;
                    }
                }
            };

            mottoTv.setOnClickListener(clickListener1);
            usernameTv.setOnClickListener(clickListener1);
            avatarIv.setOnClickListener(clickListener1);
            User user = BmobUser.getCurrentUser(User.class);
            if (null != user) {
                Glide.with(this).load(user.getAvatar())
                        .placeholder(R.mipmap.default_profile)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(avatarIv);
                Glide.with(this).load(user.getAvatar())
                        .error(R.mipmap.default_profile)
                        .centerCrop()
                        .bitmapTransform(new BlurTransformation(this))
                        .priority(Priority.LOW)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                headerLayout.setBackground(resource);
                            }
                        });
                usernameTv.setText(user.getUsername());
                mottoTv.setText(user.getMotto());

                //设置 登录 退出 item 是否可见
                menu.findItem(R.id.nav_signIn).setVisible(false);
                menu.findItem(R.id.nav_signOut).setVisible(true);
            } else {
                menu.findItem(R.id.nav_signIn).setVisible(true);
                menu.findItem(R.id.nav_signOut).setVisible(false);
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (fabActionMenu.isExpanded()) {
//                Rect outRect = new Rect();
//                fabActionMenu.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
//                    fabActionMenu.collapse();
//            }
//        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (null != drawer && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_diary:
                startActivity(new Intent(this, DiaryActivity.class));
                break;
            case R.id.nav_anniversary:
                break;
            case R.id.nav_signIn:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_setting:
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.nav_share:
                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_signOut:
                DialogUtil.showDialog(MainActivity.this, "确定退出此账号?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO.  退出登录后 应该清除缓存
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (null != drawer)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setStatusBar() {

        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout,
                ResUtil.getColor(R.color.colorPrimary));
    }

}
