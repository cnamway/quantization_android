package com.spark.qtz.ui.popup.animator;

import android.support.v4.view.animation.FastOutSlowInInterpolator;

import com.lxj.xpopup.animator.PopupAnimator;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/11
 * 描    述：透明动画
 * 修订历史：
 * ================================================
 */
public class PopupAlphaAnimator extends PopupAnimator {
    int duration = 300;

    @Override
    public void initAnimator() {
        targetView.setAlpha(0);
    }

    @Override
    public void animateShow() {
        targetView.animate().alpha(1).setInterpolator(new FastOutSlowInInterpolator()).setDuration(duration).start();
    }

    @Override
    public void animateDismiss() {
        targetView.animate().alpha(0).setInterpolator(new FastOutSlowInInterpolator()).setDuration(duration).start();
    }
}