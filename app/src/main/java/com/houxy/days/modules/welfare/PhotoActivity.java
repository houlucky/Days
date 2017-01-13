package com.houxy.days.modules.welfare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.houxy.days.R;
import com.houxy.days.base.ToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Houxy on 2017/1/12.
 * <p>
 * Info :  PhotoActivity.java
 */
public class PhotoActivity extends ToolbarActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.ivGirl) PhotoView mIvGirl;
    protected boolean mIsHidden = false;
    private String url;


    public static Intent getIntentStartActivity(Context context, String url){
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("imgUrl", url);
        return intent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setStatusBar();
        parseIntent();
        initView();
        setPhotoViewEvent();
    }

    private void initView() {

        Glide.with(this).load(url).into(mIvGirl);

    }
    
    private void hideOrShowToolBar() {
        mToolBar.animate()
                .translationY(mIsHidden ? 0 : -mToolBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(10))
                .start();
        mIsHidden = !mIsHidden;
    }

    private void setPhotoViewEvent() {
        mIvGirl.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolBar();
            }
        });

        mIvGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(PhotoActivity.this)
                        .setMessage(R.string.save_picture)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        }).show();

                return true;
            }
        });
    }

    private void parseIntent(){
        if( null != getIntent().getStringExtra("imgUrl")){
            url = getIntent().getStringExtra("imgUrl");
        }
    }
}
