package com.houxy.days.modules.diary.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.houxy.days.base.i.UpLoadDoneListener;
import com.houxy.days.common.ACache;
import com.houxy.days.common.DialogUtil;
import com.houxy.days.common.InsertPicUtil;
import com.houxy.days.common.TimeUtil;
import com.houxy.days.common.ToastUtils;
import com.houxy.days.common.UploadPictureUtil;
import com.houxy.days.common.Utils;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.main.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import rx.Observer;

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

    ProgressDialog progressDialog;
    long timeLong;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        ButterKnife.bind(this);
        setSupportActionBar(diaryEditToolbar);
        initView();
    }

    private void initView() {

        timeLong = System.currentTimeMillis();
        diaryEditTimeTv.setText(TimeUtil.getTime(timeLong));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.diary_edit_add_pic_ib:
                        DialogUtil.showSelectPicDialog(DiaryEditActivity.this);
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
        Diary diary = new Diary();
        diary.setContent(diaryContent);
        diary.setPostTime(String.valueOf(timeLong));
        User user = BmobUser.getCurrentUser(User.class);
        diary.setAuthor(user);
        diary.saveObservable().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                ToastUtils.show("添加成功");
                startActivity(new Intent(DiaryEditActivity.this, DiaryActivity.class));
                finish();
            }
        });
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("上传图片中..");
        progressDialog.setIcon(android.R.drawable.btn_star);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case C.PICK_FROM_CAMERA:
                    showProgressDialog();
                    UploadPicture(UploadPictureUtil.cameraUri);
                    break;
                case C.PICK_FROM_FILE:
                    showProgressDialog();
                    UploadPicture(data.getData());
                    break;
                default:
                    break;
            }
        }
    }

    public void UploadPicture(final Uri uri){

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("TAG", "finish");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show("上传文件失败：" + throwable.getMessage());
                progressDialog.dismiss();
            }

            @Override
            public void onNext(String imageUrl) {
                InsertPicUtil.upDateEditText(uri,imageUrl, diaryEditContentEt);
                progressDialog.dismiss();
            }

        };

        UploadPictureUtil.upLoadPicture(this, uri, observer);

    }
}
