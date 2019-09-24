package me.spark.mvvm.ui.dialog.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import me.spark.mvvm.ui.dialog.base.BaseAnimatorSet;


public class Swing extends BaseAnimatorSet {
	public Swing() {
		duration = 500;
	}

	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "alpha", 1, 1, 1, 1, 1, 1, 1, 1),//
				ObjectAnimator.ofFloat(view, "rotation", 0, 10, -10, 6, -6, 3, -3, 0));
	}
}
