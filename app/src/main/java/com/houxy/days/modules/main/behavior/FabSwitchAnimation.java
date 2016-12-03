package com.houxy.days.modules.main.behavior;

import android.support.design.widget.FloatingActionButton;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.houxy.days.R;
import com.houxy.days.common.utils.ResUtil;

/**
 * Created by Houxy on 2016/9/25.
 */

public  class FabSwitchAnimation {

    private static int[] colorIntArray = {R.color.colorAccent, android.R.color.holo_green_light,
            android.R.color.holo_red_light};
//    private static int[] iconIntArray = {R.mipmap.ic_dialog_edit,R.mipmap.ic_dialog_anniversary, R.mipmap.ic_menu_welfare_white};

    private static int[] iconIntArray = {R.mipmap.fab_edit,R.mipmap.fab_event, R.mipmap.ic_menu_welfare_white};
    public static void start(final FloatingActionButton fab, final int position) {

        //配合FloatingActionButtonScrollBehavior使用的时候可能因为上个fragment将fab隐藏
        //导致切换到下个fragment的时候不可见
        fab.show();
        fab.clearAnimation();
        // Scale down animation
        ScaleAnimation shrink =  new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon

                fab.setBackgroundTintList(ResUtil.getColorStateList(colorIntArray[position]));
                fab.setImageDrawable(ResUtil.getDrawable(iconIntArray[position]));

                // Scale up animation
                ScaleAnimation expand =  new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
    }
}
