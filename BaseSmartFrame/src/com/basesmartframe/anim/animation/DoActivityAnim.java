package com.basesmartframe.anim.animation;

import com.basesmartframe.R;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by xieningtao on 15-4-17.
 */
public enum DoActivityAnim {

	BOTTOM_IN_OUT(R.anim.pop_from_bottom_in,R.anim.pop_to_bottom_out);
	
	private final int in;
	private final int out;

	private DoActivityAnim(int in, int out) {
		this.in = in;
		this.out = out;
	}

	public void doStartActivityAnimation(Activity activity, Intent intent) {
		if (activity == null || intent == null)
			return;
		activity.startActivity(intent);
		activity.overridePendingTransition(in, out);
	}

	public void doStartActivityResultAnimation(Activity activity,
			Intent intent, int request) {
		if (activity == null || intent == null)
			return;
		activity.startActivityForResult(intent, request);
		activity.overridePendingTransition(in, out);
	}

	public void doFinishAnimation(Activity activity) {
		if (activity == null)
			return;
		activity.finish();
		activity.overridePendingTransition(in, out);
	}
}
