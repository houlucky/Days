package com.houxy.days.modules.diary.adapter.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.houxy.days.R;
import com.houxy.days.common.TimeUtil;
import com.houxy.days.modules.diary.adapter.holder.BaseViewHolder;
import com.houxy.days.modules.diary.bean.Diary;
import com.houxy.days.modules.main.bean.User;
import com.zzhoujay.richtext.RichText;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Houxy on 2016/9/12.
 */
public class DiaryHolder extends BaseViewHolder {


    @Bind(R.id.authorAvatar)
    ImageView authorAvatar;
    @Bind(R.id.authorName)
    TextView authorName;
    @Bind(R.id.authorTitle)
    TextView authorTitle;
    @Bind(R.id.postTime)
    TextView postTime;
    @Bind(R.id.diary_content)
    TextView diaryContent;

    public DiaryHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_diary);
    }

    @Override
    public void bindData(Object o) {
        Diary diary = (Diary)o;
        User user = diary.getAuthor();

        Glide.with(getContext())
                .load(user.getAvatar())
                .placeholder(R.mipmap.default_profile)
                .error(R.mipmap.default_profile)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(authorAvatar);

        postTime.setText(TimeUtil.converTime(Long.valueOf(diary.getPostTime())));
//        diaryContent.setText(diary.getContent());
        RichText.from(diary.getContent()).into(diaryContent);
        authorName.setText(user.getUsername());
        authorTitle.setText(user.getMotto());
    }
}
