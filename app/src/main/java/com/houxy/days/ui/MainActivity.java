package com.houxy.days.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.fab_action_edit_diary)
    FloatingActionButton fabActionEditDiary;
    @Bind(R.id.fab_action_edit_special_day)
    FloatingActionButton fabActionEditSpecialDay;
    @Bind(R.id.fab_action_menu)
    FloatingActionsMenu fabActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.fab_action_edit_diary) {
                    Intent intent = new Intent(MainActivity.this, DiaryEditActivity.class);
                    startActivity(intent);
                    fabActionMenu.collapse();
                } else if (v.getId() == R.id.fab_action_edit_special_day) {
                    Intent intent = new Intent(MainActivity.this, SpecialDayEditActivity.class);
//                    startActivity(intent);
                    fabActionMenu.collapse();
                }
            }
        };

        fabActionEditDiary.setOnClickListener(clickListener);
        fabActionEditSpecialDay.setOnClickListener(clickListener);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (null != drawer) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (null != navigationView) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(fabActionMenu.isExpanded()){
                Rect outRect = new Rect();
                fabActionMenu.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    fabActionMenu.collapse();
            }
        }
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
                break;
            case R.id.nav_anniversary:
                break;
            case R.id.nav_signIn:
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
