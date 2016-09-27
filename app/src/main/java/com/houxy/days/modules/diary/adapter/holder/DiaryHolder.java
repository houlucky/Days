package com.houxy.days.modules.diary.adapter.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.common.utils.InsertPicUtil;
import com.houxy.days.common.utils.TimeUtil;
import com.houxy.days.modules.diary.bean.Diary;

import butterknife.Bind;

/**
 * Created by Houxy on 2016/9/12.
 */
public class DiaryHolder extends BaseViewHolder {


    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.diary_content)
    EditText diaryContent;

    public DiaryHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_recyclerview_diary);
    }

    @Override
    public void bindData(Object o) {
        Diary diary = (Diary) o;

        timeTv.setText(TimeUtil.converTime(Long.valueOf(diary.getPostTime())));
        InsertPicUtil.setRichTextLocal(diary.getContent(), diaryContent);
    }
}
