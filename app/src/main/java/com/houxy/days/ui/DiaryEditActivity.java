package com.houxy.days.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.TimeUtil;
import com.houxy.days.common.UploadPictureUtil;
import com.houxy.days.common.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/2.
 */
public class DiaryEditActivity extends BaseActivity {

    @Bind(R.id.diary_edit_back_ib)
    ImageButton diaryEditBackIb;
    @Bind(R.id.diary_edit_toolbar)
    Toolbar diaryEditToolbar;
    @Bind(R.id.diary_edit_send_ib)
    ImageButton diaryEditSendIb;
    @Bind(R.id.diary_edit_content_et)
    EditText diaryEditContentEt;
    @Bind(R.id.diary_edit_add_pic_ib)
    ImageButton diaryEditAddPicIb;
    @Bind(R.id.diary_edit_hideSoftInput_ib)
    ImageButton diaryEditHideSoftInputIb;
    @Bind(R.id.diary_edit_scrollEdit)
    ScrollView diaryEditScrollEdit;
    @Bind(R.id.diary_edit_rl)
    RelativeLayout diaryEditRl;
    @Bind(R.id.diary_edit_time_tv)
    TextView diaryEditTimeTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        ButterKnife.bind(this);
//        setStatusBar();
        setSupportActionBar(diaryEditToolbar);
        initView();
    }

    private void initView() {

        diaryEditTimeTv.setText(TimeUtil.getNowYMDHMSTime());

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.diary_edit_add_pic_ib:
                        UploadPictureUtil.showSelectPicDialog(DiaryEditActivity.this);
                        break;
                    case R.id.diary_edit_hideSoftInput_ib:
                        diaryEditHideSoftInputIb.setVisibility(View.GONE);
                        Utils.hideSoftInput(diaryEditContentEt);
                        break;
                    case R.id.diary_edit_send_ib:
                        saveDiary();
                        break;
                    case R.id.diary_edit_back_ib:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        diaryEditBackIb.setOnClickListener(clickListener);
        diaryEditAddPicIb.setOnClickListener(clickListener);
        diaryEditHideSoftInputIb.setOnClickListener(clickListener);
        diaryEditSendIb.setOnClickListener(clickListener);

        diaryEditScrollEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Utils.hideSoftInput(diaryEditContentEt);
                    diaryEditHideSoftInputIb.setVisibility(View.GONE);
                }
                return false;
            }
        });
        diaryEditRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = diaryEditRl.getRootView().getHeight() - diaryEditRl.getHeight();
                if (heightDiff > 100) { // if more than 200 dp, it's probably a keyboard...
                    diaryEditHideSoftInputIb.setVisibility(View.VISIBLE);
                } else {
                    diaryEditHideSoftInputIb.setVisibility(View.GONE);
                }
            }
        });
    }

    private void saveDiary() {
        String diaryContent = diaryEditContentEt.getText().toString();
        String time = diaryEditTimeTv.getText().toString();
        aCache.put(C.DEFAULT_DIARY_CACHE + time, diaryContent);

    }

}
