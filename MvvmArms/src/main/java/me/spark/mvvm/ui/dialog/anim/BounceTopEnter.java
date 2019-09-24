package me.spark.mvvm.ui.dialog.anim;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import me.spark.mvvm.ui.dialog.base.BaseAnimatorSet;


public class BounceTopEnter extends BaseAnimatorSet {
	public BounceTopEnter() {
		duration = 500;
	}

	@Override
	public void setAnimation(View view) {
		DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),//
				ObjectAnimator.ofFloat(view, "translationY", -250 * dm.density, 30, -10, 0));
	}
}
