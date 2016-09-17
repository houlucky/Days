package com.houxy.days.modules.special.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.common.DialogUtil;
import com.houxy.days.common.TimeUtil;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Houxy on 2016/9/4.
 */
public class SpecialDayEditActivity extends BaseActivity {

    @Bind(R.id.date_tv)
    TextView dateTv;
    @Bind(R.id.diary_edit_back_ib)
    ImageButton BackIb;
    @Bind(R.id.edit_event)
    EditText editEvent;
    @Bind(R.id.date_switch)
    SwitchCompat dateSwitch;
    @Bind(R.id.category_rl)
    RelativeLayout categoryRl;
    @Bind(R.id.sticky_switch)
    SwitchCompat stickySwitch;
    @Bind(R.id.sticky_rl)
    RelativeLayout stickyRl;
    @Bind(R.id.repeat_show_tv)
    TextView repeatShowTv;
    @Bind(R.id.repeat_rl)
    RelativeLayout repeatRl;
    @Bind(R.id.finish_time_switch)
    SwitchCompat finishTimeSwitch;
    @Bind(R.id.finish_time_rl)
    RelativeLayout finishTimeRl;
    @Bind(R.id.save_event_btn)
    Button saveEventBtn;
    @Bind(R.id.category_show_tv)
    TextView categoryShowTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialday_edit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        dateTv.setText(TimeUtil.getCurrentDate(this));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.date_tv:
                        DialogUtil.showDatePickerDialog(SpecialDayEditActivity.this, v, Calendar.getInstance());
                        break;
                    case R.id.repeat_rl:
                        final String[] choices = {"不重复", "每周重复", "每月重复", "每年重复"};
                        DialogUtil.showSingleChoiceDialog(SpecialDayEditActivity.this, "请选择重复类型", choices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                repeatShowTv.setText(choices[which]);
                                dialog.dismiss();
                            }
                        });
                        break;
                    case R.id.category_rl:
                        final String[] choices_1 = {"生活", "纪念日", "工作"};
                        DialogUtil.showSingleChoiceDialog(SpecialDayEditActivity.this, "请选择重复类型", choices_1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                categoryShowTv.setText(choices_1[which]);
                                dialog.dismiss();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };


        categoryRl.setOnClickListener(onClickListener);
        repeatRl.setOnClickListener(onClickListener);
        dateTv.setOnClickListener(onClickListener);
    }
}
