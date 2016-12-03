package com.houxy.days.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.common.Utils;
import com.houxy.days.common.utils.TimeUtil;
import com.houxy.days.bean.Diary;

import butterknife.Bind;

/**
 * Created by Houxy on 2016/9/12.
 *
 * Info :  日记装饰器
 */
public class DiaryHolder extends BaseViewHolder {


    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.diary_content)
    TextView diaryContent;

    public DiaryHolder(Context context, ViewGroup root, OnItemClickListener onItemClickListener) {
        super(context, root, R.layout.item_recyclerview_diary, onItemClickListener);
    }

    @Override
    public void bindData(Object o) {
        Diary diary = (Diary) o;

        timeTv.setText(TimeUtil.converTime(Long.valueOf(diary.getPostTime())));
        diaryContent.setText(Utils.replaceN(Utils.replaceImgHtml(diary.getContent())));
    }
}
