package com.houxy.days.modules.diary.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.houxy.days.base.ToolbarActivity;
import com.houxy.days.common.ACache;
import com.houxy.days.common.utils.BitmapUtils;
import com.houxy.days.common.utils.DensityUtil;
import com.houxy.days.common.utils.DialogUtil;
import com.houxy.days.common.utils.InsertPicUtil;
import com.houxy.days.common.utils.TimeUtil;
import com.houxy.days.common.utils.ToastUtils;
import com.houxy.days.common.utils.UploadPictureUtil;
import com.houxy.days.common.Utils;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.main.bean.User;
import com.houxy.days.modules.main.ui.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import rx.Observer;

/**
 * Created by Houxy on 2016/9/2.
 */
public class DiaryEditActivity extends ToolbarActivity {

    @Bind(R.id.diary_edit_content_et)
    EditText editText;
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

    private ProgressDialog progressDialog;
    private long timeLong;
    private Diary diary;

    public static Intent getIntentStartActivity(Context context, Diary diary, int position) {
        Intent intent = new Intent(context, DiaryEditActivity.class);
        intent.putExtra("diary", diary);
        intent.putExtra("position", position);
        return intent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_diary_edit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        setToolBarTitle("写日记");

        diary = (Diary)getIntent().getSerializableExtra("diary");
        if(null != diary){
            diaryEditTimeTv.setText(TimeUtil.getTime(Long.valueOf(diary.getPostTime())));
            InsertPicUtil.setRichTextLocal(diary.getContent(), editText);
            editText.setSelection(diary.getContent().length());
        }else {
            timeLong = System.currentTimeMillis();
            diaryEditTimeTv.setText(TimeUtil.getTime(timeLong));
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.diary_edit_add_pic_ib:
                        DialogUtil.showSelectPicDialog(DiaryEditActivity.this);
                        break;
                    case R.id.diary_edit_hideSoftInput_ib:
                        diaryEditHideSoftInputIb.setVisibility(View.GONE);
                        Utils.hideSoftInput(editText);
                        break;
                    default:
                        break;
                }
            }
        };
        diaryEditAddPicIb.setOnClickListener(clickListener);
        diaryEditHideSoftInputIb.setOnClickListener(clickListener);

        diaryEditScrollEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Utils.hideSoftInput(editText);
                    diaryEditHideSoftInputIb.setVisibility(View.GONE);
                }
                return false;
            }
        });
        diaryEditRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = diaryEditRl.getRootView().getHeight() - diaryEditRl.getHeight();
                if (heightDiff > DensityUtil.dip2px(DiaryEditActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                    diaryEditHideSoftInputIb.setVisibility(View.VISIBLE);
                } else {
                    diaryEditHideSoftInputIb.setVisibility(View.GONE);
                }
            }
        });
    }

    private void saveDiary() {

        if(TextUtils.isEmpty(editText.getText().toString())){
            ToastUtils.show("内容不能为空哦~");
            return;
        }

        if( null == diary){
            diary = new Diary();
            diary.setPostTime(String.valueOf(timeLong));
            diary.setAuthor(User.getCurrentUser(User.class));
        }
        diary.setContent(editText.getText().toString());
        ArrayList<Diary> diaries;
        if( null != ACache.getDefault().getAsObject(C.DIARY_CACHE)){
            diaries = (ArrayList<Diary>) ACache.getDefault().getAsObject(C.DIARY_CACHE);
        }else {
            diaries = new ArrayList<>();
        }
        int position = getIntent().getIntExtra("position", -1);

        if( position != -1){
            diaries.remove(position);
            diaries.add(position, diary);
        }else {
            diaries.add(0, diary);
        }
        ACache.getDefault().put(C.DIARY_CACHE, diaries);
        ToastUtils.show("添加成功");
        Intent intent = new Intent();
        intent.putExtra("diary", diary);
        setResult(RESULT_OK, intent);
        finish();
//        User user = BmobUser.getCurrentUser(User.class);
//        diary.setAuthor(user);
//        diary.saveObservable().subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                ToastUtils.show("添加成功");
//                startActivity(new Intent(DiaryEditActivity.this, DiaryActivity.class));
//                finish();
//            }
//        });
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
//                    UploadPicture(UploadPictureUtil.cameraUri);
                    setPicture(UploadPictureUtil.cameraUri);
                    break;
                case C.PICK_FROM_FILE:
//                    UploadPicture(data.getData());
                    setPicture(data.getData());
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
                InsertPicUtil.upDateEditText(uri,imageUrl, editText);
                progressDialog.dismiss();
            }

        };

        UploadPictureUtil.upLoadPicture(this, uri, observer);

    }

    public void setPicture(Uri uri){
        int editTextWidth = editText.getWidth() - editText.getPaddingRight() - editText.getPaddingLeft();
        String spanName = C.IMAGE_CACHE + "img_" + TimeUtil.getNowYMDHMSTime();
        spanName = "<img src=\"" + spanName + "\" />";
        Bitmap pic = BitmapUtils.compressImage(uri, DiaryEditActivity.this, editTextWidth);
        ACache.getDefault().put(spanName, pic);

        SpannableString picSs = new SpannableString(spanName);
        ImageSpan span = new ImageSpan(DiaryEditActivity.this, pic);
        picSs.setSpan(span, 0, spanName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int index = editText.getSelectionStart();
        Editable editable = editText.getEditableText();

        if (index < 0 || index >= editable.length()) {
            editable.append("\n");
            editable.append(picSs);
            editable.append("\n");
        } else {
            editable.insert(index, "\n");
            editable.insert(index, picSs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_send){
            saveDiary();
        }
        return super.onOptionsItemSelected(item);
    }
}
