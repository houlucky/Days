package com.houxy.days.modules.welfare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Houxy on 2017/1/12.
 * <p>
 * Info :  PhotoActivity.java
 */
public class PhotoActivity extends BaseActivity {

    //    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.ivGirl) PhotoView mIvGirl;
    protected boolean mIsHidden = false;
    private String url;


    public static Intent getIntentStartActivity(Context context, String url) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("imgUrl", url);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        parseIntent();
        initView();
    }

    private void initView() {

        Glide.with(this).load(url).into(mIvGirl);

        mIvGirl.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                finish();
            }
        });


    }


    private void parseIntent() {
        if (null != getIntent().getStringExtra("imgUrl")) {
            url = getIntent().getStringExtra("imgUrl");
        }
    }
}
