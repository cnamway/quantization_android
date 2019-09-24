package me.spark.mvvm.ui.dialog.anim;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import me.spark.mvvm.ui.dialog.base.BaseAnimatorSet;


public class SlideBottomExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "translationY", 0, 250 * dm.density), //
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}
