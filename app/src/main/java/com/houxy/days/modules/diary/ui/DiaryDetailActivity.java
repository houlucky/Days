package com.houxy.days.modules.diary.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.ToolbarActivity;
import com.houxy.days.common.utils.BitmapUtils;
import com.houxy.days.common.utils.InsertPicUtil;
import com.houxy.days.common.utils.TimeUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.common.utils.UploadPictureUtil;
import com.houxy.days.modules.diary.bean.Diary;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/29.
 */

public class DiaryDetailActivity extends ToolbarActivity {

    @Bind(R.id.diary_content)
    EditText diaryContent;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.diaryRl)
    RelativeLayout diaryRl;
    private Diary diary;
    private int position;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_diary_detail;
    }

    public static Intent getIntentStartActivity(Context context, Diary diary, int position) {
        Intent intent = new Intent(context, DiaryDetailActivity.class);
        intent.putExtra("diary", diary);
        intent.putExtra("position", position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setToolBarTitle("日记详情");
        diary = (Diary) getIntent().getSerializableExtra("diary");
        position = getIntent().getIntExtra("position", -1);
        if (null != diary) {
            InsertPicUtil.setRichTextLocal(diary.getContent(), diaryContent);
            timeTv.setText(TimeUtil.getTime(Long.valueOf(diary.getPostTime())));
        }

        diaryContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Snackbar.make(diaryRl, "生成图片分享日志", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = UploadPictureUtil.saveBitmapToSDCard(BitmapUtils.richTextAsBitmap(diary.getContent()));
                        ToastUtils.show("保存图片成功" + s);
                        Uri uri = Uri.fromFile(new File(s));
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(intent);
                    }
                }).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_modify) {
            Intent intent = DiaryEditActivity.getIntentStartActivity(DiaryDetailActivity.this, diary, position);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                diary = (Diary) data.getSerializableExtra("diary");
                if (null != diary) {
                    InsertPicUtil.setRichTextLocal(diary.getContent(), diaryContent);
                    timeTv.setText(TimeUtil.getTime(Long.valueOf(diary.getPostTime())));
                }
            }
        }
    }
}
