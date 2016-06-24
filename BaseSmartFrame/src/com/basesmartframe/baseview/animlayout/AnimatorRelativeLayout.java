package com.basesmartframe.baseview.animlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.sf.loglib.L;

public class AnimatorRelativeLayout extends RelativeLayout {

	private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

	public AnimatorRelativeLayout(Context context, AttributeSet set) {
		super(context, set);
	}

	public void setFractionX(final float fraction) {
		int width = getWidth();

		if (width == 0) {
			if (preDrawListener == null) {
				preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						getViewTreeObserver().removeOnPreDrawListener(
								preDrawListener);
						setFractionX(fraction);
						return true;
					}
				};
				getViewTreeObserver().addOnPreDrawListener(preDrawListener);
			}
			return;
		}
		setX(width * fraction);

		L.info(this, "fractionX: " + fraction + " widht:" + width);
	}

	public void setFractionY(final float fraction) {
		int height = getHeight();
		setY(height * fraction);
		L.info(this, "fractiony: " + fraction + " height:" + height);
	}

	public float getFractionX() {
		double width = getWidth() * 1.0;
		double rWidht = getX();
		double result = rWidht / width;
		return (float) result;
	}

	public float getFractionY() {
		double height = getHeight() * 1.0;
		double rHeight = getY();
		double result = rHeight / height;
		return (float) result;
	}
}
