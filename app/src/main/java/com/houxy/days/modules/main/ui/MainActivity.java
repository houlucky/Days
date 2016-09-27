package com.houxy.days.modules.main.ui;

import android.content.Context;
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
import android.view.LayoutInflater;
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
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.modules.diary.ui.DiaryActivity;
import com.houxy.days.modules.diary.ui.DiaryEditActivity;
import com.houxy.days.modules.diary.ui.DiaryFragment;
import com.houxy.days.modules.login.ui.LoginActivity;
import com.houxy.days.modules.main.adapter.TabPagerAdapter;
import com.houxy.days.modules.main.bean.User;
import com.houxy.days.modules.main.listener.FabSwitchAnimation;
import com.houxy.days.modules.special.ui.EventEditActivity;
import com.houxy.days.modules.special.ui.EventFragment;
import com.houxy.days.modules.welfare.ui.MeiZhiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    @Bind(R.id.fab_action_edit_diary)
//    FloatingActionButton fabActionEditDiary;
//    @Bind(R.id.fab_action_edit_special_day)
//    FloatingActionButton fabActionEditSpecialDay;
//    @Bind(R.id.fab_action_menu)
//    FloatingActionsMenu fabActionMenu;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private User user;

    public static Intent getIntentStartActivity(Context context, int currentItem) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("CurrentItem", currentItem);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        user = BmobUser.getCurrentUser(User.class);
        initView();
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        View.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.fab_action_edit_diary) {
//                    Intent intent = new Intent(MainActivity.this, DiaryEditActivity.class);
//                    startActivity(intent);
//                    fabActionMenu.collapse();
//                } else if (v.getId() == R.id.fab_action_edit_special_day) {
//                    Intent intent = new Intent(MainActivity.this, EventEditActivity.class);
//                    startActivity(intent);
//                    fabActionMenu.collapse();
//                }
//            }
//        };
//
//        fabActionEditDiary.setOnClickListener(clickListener);
//        fabActionEditSpecialDay.setOnClickListener(clickListener);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (null != drawer) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        initNavigationView();
        initFragments();

    }


    private void initFragments() {


        View.OnClickListener[] onClickListeners = {};


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DiaryFragment());
        fragments.add(new EventFragment());
        fragments.add(new MeiZhiFragment());
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                FabSwitchAnimation.start(fab, tab.getPosition());
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (tab.getPosition()){
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
                            default:break;
                        }
                    }
                };
                fab.setOnClickListener(onClickListener);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        //这里有点坑
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);

        //如果是由其他活动结束进入的Main 需要判断他要进入的是哪个fragment
        if (getIntent().getIntExtra("CurrentItem", -1) != -1) {
            viewPager.setCurrentItem(getIntent().getIntExtra("CurrentItem", -1));
        }
    }

    private View getTabView(int pos) {
        int[] resId = {R.drawable.item_tab_diary_selector, R.drawable.item_tab_event_selector, R.drawable.item_tab_welfare_selector};

        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(resId[pos]);
        return view;
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (null != navigationView) {
            navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_signOut:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (null != drawer)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
