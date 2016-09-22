package com.houxy.days.modules.special.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.houxy.days.C;
import com.houxy.days.R;
import com.houxy.days.base.BaseActivity;
import com.houxy.days.base.ToolbarActivity;
import com.houxy.days.common.DialogUtil;
import com.houxy.days.common.TimeUtil;
import com.houxy.days.common.ToastUtils;
import com.houxy.days.modules.main.bean.User;
import com.houxy.days.modules.main.ui.MainActivity;
import com.houxy.days.modules.special.bean.SpecialEvent;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Houxy on 2016/9/4.
 */
public class SpecialDayEditActivity extends ToolbarActivity {

    @Bind(R.id.date_tv)
    TextView dateTv;
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
    @Bind(R.id.date_rl)
    RelativeLayout dateRl;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_specialday_edit;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        setToolBarTitle("新增事件");
        dateTv.setText(TimeUtil.getCurrentDate(this));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
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
                    case R.id.date_rl:
                        Calendar calendar = TimeUtil.getAssignCalendar(dateTv.getText().toString());
                        DialogUtil.showDatePickerDialog(SpecialDayEditActivity.this, calendar,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        dateTv.setText(TimeUtil.getAssignDate(view.getContext(), year, monthOfYear, dayOfMonth));
                                    }
                                });
                        break;
                    case R.id.sticky_rl:
                        break;
                    case R.id.finish_time_rl:
                        break;
                    case R.id.save_event_btn:
                        saveEvent();
                        break;
                    default:
                        break;
                }
            }
        };


        categoryRl.setOnClickListener(onClickListener);
        repeatRl.setOnClickListener(onClickListener);
        dateRl.setOnClickListener(onClickListener);
        stickyRl.setOnClickListener(onClickListener);
        finishTimeRl.setOnClickListener(onClickListener);
        saveEventBtn.setOnClickListener(onClickListener);
    }

    private void saveEvent() {

        if (TextUtils.isEmpty(editEvent.getText())) {
            ToastUtils.show("事件名称不能为空哦~");
            return;
        }

        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setEventName(editEvent.getText().toString());
        specialEvent.setEventCategory(categoryShowTv.getText().toString());
        specialEvent.setDate(dateTv.getText().toString());
        specialEvent.setRepeatType(repeatShowTv.getText().toString());
        specialEvent.setEventOwner(User.getCurrentUser(User.class));
        specialEvent.saveObservable().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                ToastUtils.show("添加成功");
                Intent intent = MainActivity.getIntentStartActivity(SpecialDayEditActivity.this, C.CurrentItem_Event);
                startActivity(intent);
                finish();
            }
        });


    }

}
