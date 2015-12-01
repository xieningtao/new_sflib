package com.basesmartframe.gesture;

import com.basesmartframe.gesture.HeadViewGesture.MovingMode;
import com.basesmartframe.log.L;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class HeadViewInteraction implements
		HeadViewGesture.HeadViewGestureListener {
	private final float K = 0.6f;
	protected View head_show_fl;
	protected View head_hide_fl;
	protected HeadViewGesture mGestureHelp;
	private float hideViewYLowerBound = 0;
	private float showViewYLowerBound = 0;
	private float hideViewXLowerBound = 0;
	private float showViewXLowerBound = 0;

	private float hideViewYUpBound = 0;
	private float showViewYUpBound = 0;
	private float hideViewXUpBound = 0;
	private float showViewXUpBound = 0;

	private float hideViewAlphaLowerBound = 0;
	private float showViewAlphaLowerBound = 0;

	private float hideViewAlphaUpBound = 1;
	private float showViewAlphaUpBound = 1;

	private String TAG = getClass().getSimpleName();

	public HeadViewInteraction(Context context, View view, View hide_fl,
			View show_fl) {
		mGestureHelp = new HeadViewGesture(context, this);
		head_hide_fl = hide_fl;
		head_show_fl = show_fl;
		mGestureHelp.registerGuesture(view);
	}

	private void setAlpha(float alpha) {
		head_show_fl.setAlpha(alpha);
	}

	private void toggleLeftRightShowHeadView(boolean show, float curAlpha,
			float factor) {

		float curX = -(showViewXLowerBound - factor * showViewXLowerBound);
		if (show) {
			doXTranslateAnimation(head_show_fl, curX, showViewXUpBound);
			L.debug(TAG, "do show show_view animation");
		} else {
			doXTranslateAnimation(head_show_fl, curX, -showViewXLowerBound);
			L.debug(TAG, "do dismiss show_view animation");
		}
	}

	private void toggleUpDownShowHeadView(boolean show, float curAlpha,
			float factor) {

		float curY = -(showViewYLowerBound - factor * showViewYLowerBound);
		if (show) {
			doYTranslateAnimation(head_show_fl, curY, showViewYUpBound);
			L.debug(TAG, "do show show_view animation");
		} else {
			doYTranslateAnimation(head_show_fl, curY, -showViewYLowerBound);
			L.debug(TAG, "do dismiss show_view animation");
		}
	}

	private void toggleShowHeandViewAlpha(boolean show, float curAlpha) {
		if (show) {
			doAlphaAnimation(head_show_fl, curAlpha, showViewAlphaUpBound);
			L.debug(TAG, "do show show_view animation");
		} else {
			doAlphaAnimation(head_show_fl, curAlpha, showViewAlphaLowerBound);
			L.debug(TAG, "do dimiss show_view animation");
		}
	}

	private void toggleLeftRightHideHeadView(boolean show, float curX) {
		if (show) {
			doXTranslateAnimation(head_hide_fl, curX, hideViewXUpBound);
			L.debug(TAG, "do show hide view animation");
		} else {
			doXTranslateAnimation(head_hide_fl, curX, -hideViewXLowerBound);
			L.debug(TAG, "do dismiss hide view animation");
		}
	}

	private void toggleUpDownHideHeadView(boolean show, float curY) {
		if (show) {
			doYTranslateAnimation(head_hide_fl, curY, hideViewYUpBound);
			L.debug(TAG, "do show hide view animation");
		} else {
			doYTranslateAnimation(head_hide_fl, curY, -hideViewYLowerBound);
			L.debug(TAG, "do dismiss hide view animation");
		}
	}

	private void doAlphaAnimation(View view, float cur, float des) {
		Animator oldAnimator = (Animator) view.getTag();
		if (null != oldAnimator) {
			oldAnimator.cancel();
		}
		Animator animator = ObjectAnimator.ofFloat(view, "Alpha", cur, des);
		animator.setDuration(200);
		animator.start();
		view.setTag(animator);
	}

	private void doYTranslateAnimation(View view, float cur, float des) {
		Animator oldAnimator = (Animator) view.getTag();
		if (null != oldAnimator) {
			oldAnimator.cancel();
		}
		Animator animator = ObjectAnimator.ofFloat(view, "Y", cur, des);
		animator.setDuration(200);
		animator.start();
		view.setTag(animator);
	}

	private void doXTranslateAnimation(View view, float cur, float des) {
		Animator oldAnimator = (Animator) view.getTag();
		if (null != oldAnimator) {
			oldAnimator.cancel();
		}
		Animator animator = ObjectAnimator.ofFloat(view, "X", cur, des);
		animator.setDuration(200);
		animator.start();
		view.setTag(animator);
	}

	private boolean doUpDownFling(float velocityX, float velocityY) {
		L.debug(TAG, "doFling action");
		float curY = head_hide_fl.getY();
		boolean show_HideHeadView = velocityY > 0 ? true : false;

		flingUpDowHeadView(show_HideHeadView, curY);
		return true;
	}

	private boolean doLeftRightFling(float velocityX, float velocityY) {
		L.debug(TAG, "doLeftRightFling action");
		float curX = head_hide_fl.getX();
		boolean show_HideHeadView = velocityX > 0 ? true : false;

		flingLeftRightHeadView(show_HideHeadView, curX);
		return true;
	}

	private void flingLeftRightHeadView(boolean show_HideHeadView, float curX) {
		float curAlpha = head_show_fl.getAlpha();
		float factor = Math.abs(curX) / hideViewXLowerBound;
		toggleLeftRightHideHeadView(show_HideHeadView, curX);
		toggleLeftRightShowHeadView(!show_HideHeadView, curAlpha, factor);
	}

	private void flingUpDowHeadView(boolean show_HideHeadView, float curY) {
		float curAlpha = head_show_fl.getAlpha();
		float factor = Math.abs(curY) / hideViewYLowerBound;
		toggleUpDownHideHeadView(show_HideHeadView, curY);
		toggleUpDownShowHeadView(!show_HideHeadView, curAlpha, factor);
	}

	private boolean doUpDownUp(MotionEvent event) {
		L.debug(TAG, "doUpDownUp");
		boolean isOver = isUpDownOverBound();
		if (isOver) {
			L.debug(TAG, "do UpDownUp action hide head view is over bound");
			return false;
		} else {
			float curY = head_hide_fl.getY();
			boolean show_HideHeadView = curY > -hideViewYLowerBound / 2 ? true
					: false;
			flingUpDowHeadView(show_HideHeadView, curY);
			return true;
		}
	}

	private boolean doLeftRightUp(MotionEvent event) {
		L.debug(TAG, "doLeftRightUp");
		boolean isOver = isLeftRighOverBound();
		if (isOver) {
			L.debug(TAG, "do leftRightUp action hide head view is over bound");
			return false;
		} else {
			float curX = head_hide_fl.getX();
			boolean show_HideHeadView = curX > -hideViewXLowerBound / 2 ? true
					: false;
			flingLeftRightHeadView(show_HideHeadView, curX);
			return true;
		}
	}

	private void scrollHead(float distanceY, float dy) {
		float curY = head_hide_fl.getY();
		float nextY = curY + dy * K;
		if (nextY > hideViewYUpBound)
			nextY = hideViewYUpBound;
		else if (nextY < -hideViewYLowerBound)
			nextY = -hideViewYLowerBound;
		L.debug(TAG, "scroll dy: " + nextY);
		head_hide_fl.setY(nextY);
		float factor = Math.abs(nextY) / hideViewYLowerBound;
		// setAlpha(factor);
		float showY = showViewYLowerBound - factor * showViewYLowerBound;
		head_show_fl.setY(-showY);
	}

	private void scrollLeftRightHead(float distanceY, float dx) {
		float curX = head_hide_fl.getX();
		float nextX = curX + dx * K;
		if (nextX > hideViewXUpBound)
			nextX = hideViewXUpBound;
		else if (nextX < -hideViewXLowerBound)
			nextX = -hideViewXLowerBound;
		L.debug(TAG, "scroll dx: " + nextX);
		head_hide_fl.setX(nextX);
		float factor = Math.abs(nextX) / hideViewXLowerBound;
		// setAlpha(factor);
		float showX = showViewXLowerBound - factor * showViewXLowerBound;
		head_show_fl.setX(-showX);
	}

	private boolean isUpDownOverBound() {
		if (mGestureHelp.getmMoveDirection() == HeadViewGesture.MOVEDIRECTION.DOWN) {
			if (isHideHeadViewYUpBound()
					&& head_hide_fl.getVisibility() == View.VISIBLE) {
				L.debug(TAG, "hide view is at bottom,can't scroll down");
				return true;
			}
		} else if (mGestureHelp.getmMoveDirection() == HeadViewGesture.MOVEDIRECTION.UP) {
			if (isHideHeadViewYLowerBound()) {
				L.debug(TAG, "hide view is at top,can't scroll up");
				return true;
			}
		}
		return false;
	}

	private boolean isLeftRighOverBound() {
		if (mGestureHelp.getmMoveDirection() == HeadViewGesture.MOVEDIRECTION.LEFT) {
			if (isHideHeadViewXLowerBound()
					&& head_hide_fl.getVisibility() == View.VISIBLE) {
				L.debug(TAG, "hide view is at left,can't scroll left");
				return true;
			}
		} else if (mGestureHelp.getmMoveDirection() == HeadViewGesture.MOVEDIRECTION.RIGHT) {
			if (isHideHeadViewXUpBound()) {
				L.debug(TAG, "hide view is at right,can't scroll right");
				return true;
			}
		}
		return false;
	}

	private boolean isHideHeadViewYUpBound() {
		int curY = (int) head_hide_fl.getY();
		return curY == hideViewYUpBound;
	}

	private boolean isHideHeadViewYLowerBound() {
		int curY = (int) head_hide_fl.getY();
		return curY == -hideViewYLowerBound;
	}

	private boolean isHideHeadViewXUpBound() {
		int curX = (int) head_hide_fl.getX();
		return curX == hideViewXUpBound;
	}

	private boolean isHideHeadViewXLowerBound() {
		int curX = (int) head_hide_fl.getX();
		return curX == -hideViewXLowerBound;
	}

	private boolean doUpDownScroll(float distanceX, float distanceY, float dy) {
		if (Math.abs(distanceX) > Math.abs(distanceY)) {
			L.debug(TAG, "scroll direction is illegal");
			return false;
		}
		L.debug(TAG, "do upDown scrolling");
		if (head_hide_fl.getVisibility() != View.VISIBLE) {
			head_hide_fl.setVisibility(View.VISIBLE);
			hideViewYLowerBound = head_hide_fl.getHeight();
			showViewYLowerBound = head_show_fl.getHeight();
			head_hide_fl.setY(-hideViewYLowerBound);
		}
		boolean isOver = isUpDownOverBound();
		if (isOver)
			return true;
		scrollHead(distanceY, dy);
		return true;
	}

	private boolean doLeftRightScroll(float distanceX, float distanceY, float dx) {
		if (Math.abs(distanceX) < Math.abs(distanceY)) {
			L.debug(TAG, "scroll direction is illegal");
			return false;
		}
		L.debug(TAG, "do leftRight scrolling");
		if (head_hide_fl.getVisibility() != View.VISIBLE) {
			head_hide_fl.setVisibility(View.VISIBLE);
			hideViewXLowerBound = head_hide_fl.getHeight() / 2;
			showViewXLowerBound = head_show_fl.getHeight();
			// head_hide_fl.set(-hideViewYLowerBound);
		}
		boolean isOver = isLeftRighOverBound();
		if (isOver)
			return true;
		scrollLeftRightHead(distanceY, dx);
		return true;
	}

	@Override
	public boolean onScroll(float distanceX, float distanceY, float dy,
			float dx, MovingMode mode) {
		if (MovingMode.UPDOWN == mode) {
			return doUpDownScroll(distanceX, distanceY, dy);
		} else if (MovingMode.LEFTRIGHT == mode) {
			return doLeftRightScroll(distanceX, distanceY, dx);
		} else {
			L.debug(TAG, "scroll moving mode is idle");
			return false;
		}
	}

	@Override
	public boolean onFling(float velocityX, float velocityY, MovingMode mode) {
		if (MovingMode.UPDOWN == mode) {
			return doUpDownFling(velocityX, velocityY);
		} else if (MovingMode.LEFTRIGHT == mode) {
			return doLeftRightFling(velocityX, velocityY);
		} else {
			L.debug(TAG, "fling moving mode is idle");
			return false;
		}
	}

	@Override
	public boolean onUp(MotionEvent event, MovingMode mode) {
		if (MovingMode.UPDOWN == mode) {
			return doUpDownUp(event);
		} else if (MovingMode.LEFTRIGHT == mode) {
			return doLeftRightUp(event);
		} else {
			L.debug(TAG, "up moving mode is idle");
			return false;
		}
	}

	private boolean doUpDownSingleUp() {
		int curY = (int) head_hide_fl.getY();
		if (curY == hideViewYUpBound
				&& head_hide_fl.getVisibility() == View.VISIBLE) {
			toggleUpDownHideHeadView(false, curY);
			toggleUpDownShowHeadView(true, 0.0f, 1.0f);
		}
		return true;
	}

	private boolean doLeftRightSingleUp() {
		int curX = (int) head_hide_fl.getX();
		if (curX == hideViewXUpBound
				&& head_hide_fl.getVisibility() == View.VISIBLE) {
			toggleLeftRightHideHeadView(false, curX);
			toggleLeftRightShowHeadView(true, 0.0f, 1.0f);
		}
		return true;
	}

	@Override
	public boolean onSingleUp(MotionEvent event, MovingMode mode) {
		L.debug(TAG, "onSingleUp");
		if (MovingMode.UPDOWN == mode) {
			return doUpDownSingleUp();
		} else if (MovingMode.LEFTRIGHT == mode) {
			return doLeftRightSingleUp();
		} else {
			L.debug(TAG, "singleUp moving mode is idle");
			return false;
		}

	}

	@Override
	public boolean onDoubleTap(MotionEvent e, MovingMode mode) {
		return false;
	}
}
