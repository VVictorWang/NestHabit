package com.victor.nesthabit.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by victor on 7/3/17.
 */

public class ViewAnimationUtils {

    /**
     * 视图移动
     *
     * @param view           要移动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param fromYDelta     Y轴开始坐标
     * @param toYDelta       Y轴结束坐标
     * @param cycles         重复
     * @param durationMillis 持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void translate(final View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, float cycles, long durationMillis, final boolean isBanClick) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(durationMillis);
        if (cycles > 0.0) {
            translateAnimation.setInterpolator(new CycleInterpolator(cycles));
        }
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isBanClick) {
                    view.setClickable(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isBanClick) {
                    view.setClickable(true);
                }
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * 视图移动
     *
     * @param view           要移动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param fromYDelta     Y轴开始坐标
     * @param toYDelta       Y轴结束坐标
     * @param cycles         重复
     * @param durationMillis 持续时间
     */
    public static void translate(final View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, float cycles, long durationMillis) {
        translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, cycles, durationMillis, false);
    }

}
