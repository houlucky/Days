package com.houxy.days.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.houxy.days.R;
import com.houxy.days.base.BaseViewHolder;
import com.houxy.days.base.i.OnItemClickListener;
import com.houxy.days.bean.MeiZhi;
import com.houxy.days.common.utils.DensityUtil;

import butterknife.Bind;

/**
 * Created by Houxy on 2016/9/24.
 */

public class MeiZhiViewHolder extends BaseViewHolder {

    @Bind(R.id.iv_girl)
    ImageView ivGirl;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    public MeiZhiViewHolder(Context context, ViewGroup root, OnItemClickListener onItemClickListener) {
        super(context, root, R.layout.item_recyclerview_meizhi, onItemClickListener);
    }

    @Override
    public void bindData(Object o) {
        final MeiZhi meiZhi = (MeiZhi) o;
        setRatioImageView();
        Glide.with(getContext())
                .load(meiZhi.getUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivGirl);
        tvTitle.setText(meiZhi.getDesc());

//        ivGirl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = PhotoActivity.getIntentStartActivity(getContext(), meiZhi.getUrl());
////                getContext().startActivity(intent);
////
////                PhotoFragment photoFragment = new PhotoFragment();
////
////                photoFragment.show((Activity)itemView.getContext())
//            }
//        });
    }

    //设置imageview的大小不一
    private void setRatioImageView() {
        float scale = (float) (Math.random() + 1);
        while (scale > 1.6 || scale < 1.1) {
            scale = (float) (Math.random() + 1);
        }
        ViewGroup.LayoutParams params = ivGirl.getLayoutParams();
        params.height = (int) (DensityUtil.getWindowWidth(getContext()) * scale * 0.448);
        ivGirl.setLayoutParams(params);
    }
}
