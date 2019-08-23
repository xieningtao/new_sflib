package com.basesmartframe.gesture;

import com.sf.loglib.L;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by xieningtao on 15-6-12.
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class HeadViewGesture extends GestureDetector.SimpleOnGestureListener {
	public enum MOVEDIRECTION {
		IDLE, UP, DOWN, LEFT, RIGHT
	}

	public enum MovingMode {
		IDLE, UPDOWN, LEFTRIGHT
	}

	public interface HeadViewGestureListener {
		boolean onScroll(float distanceX, float distanceY, float dy, float dx,
				MovingMode mode);

		boolean onFling(float velocityX, float velocityY, MovingMode mode);

		boolean onUp(MotionEvent event, MovingMode mode);

		boolean onSingleUp(MotionEvent event, MovingMode mode);

		boolean onDoubleTap(MotionEvent e, MovingMode mode);
	}

	private final String TAG = HeadViewGesture.class.getName();

	private final HeadViewGestureListener mHeadViewListener;

	private MOVEDIRECTION mMoveDirection = MOVEDIRECTION.IDLE;

	private float mDownY;
	private float mDownX;

	private float mMoveY;
	private float mMoveX;

	private MovingMode movingMode = MovingMode.IDLE;

	private boolean isScrolling = false;

	private VelocityTracker mTracker;
	private ViewConfiguration mConfiguration;

	@SuppressLint("Recycle")
	public HeadViewGesture(Context context,
			HeadViewGestureListener mGestureListener) {
		this.mHeadViewListener = mGestureListener;
		mTracker = VelocityTracker.obtain();
		mConfiguration = ViewConfiguration.get(context);
	}

	private void reset(MotionEvent motionEvent) {
		mTracker.clear();
		mTracker.addMovement(motionEvent);
		mMoveX = mDownX = motionEvent.getRawX();
		mMoveY = mDownY = motionEvent.getRawY();
		movingMode = MovingMode.IDLE;
		isScrolling = false;
		mMoveDirection = MOVEDIRECTION.IDLE;
	}

	private boolean isMovingModeOk() {
		return movingMode == MovingMode.LEFTRIGHT;
	}

	private void doScrollingFast(MotionEvent motionEvent) {
		if (!isMovingModeOk())
			return;
		float distanceY = motionEvent.getRawY() - mDownY;
		float distanceX = motionEvent.getRawX() - mDownX;
		float dy = motionEvent.getRawY() - mMoveY;
		float dx = motionEvent.getRawX() - mMoveX;
		changeMoveDirection(dy, dx);
		L.debug(TAG, "distanceY=" + distanceY + " distanceX=" + distanceX
				+ " moving dy: " + dy + " moving dx: " + dx);
		if (null != mHeadViewListener) {
			mHeadViewListener
					.onScroll(distanceX, distanceY, dy, dx, movingMode);
		}
		mMoveY = motionEvent.getRawY();
		mMoveX = motionEvent.getRawX();
	}

	private void updateMovingMode(MotionEvent motionEvent) {
		float distanceY = motionEvent.getRawY() - mDownY;
		float distanceX = motionEvent.getRawX() - mDownX;
		if (movingMode == MovingMode.IDLE || !isScrolling) {
			final int touchSlop = mConfiguration.getScaledTouchSlop();
			final int abs_y = (int) Math.abs(distanceY);
			final int abs_x = (int) Math.abs(distanceX);
			if (abs_y > abs_x && abs_y > touchSlop) {
				movingMode = MovingMode.UPDOWN;
				isScrolling = true;
			} else if (abs_y < abs_x && abs_x > touchSlop) {
				movingMode = MovingMode.LEFTRIGHT;
				isScrolling = true;
			}
		}
		L.debug(TAG, "moving mode=" + movingMode + " isScrolling="
				+ isScrolling);
		doScrollingFast(motionEvent);
	}

	public MovingMode getCurMovingMode() {
		return movingMode;
	}

	public void registerGuesture(View view) {
		if (null == view) {
			L.error(this, "regiesterGuesture view is null");
			return;
		}
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int action = motionEvent.getAction();
				// printAction(motionEvent);
				if (action == MotionEvent.ACTION_MOVE && isScrolling) {
					doScrollingFast(motionEvent);
					return true;
				}

				switch (action) {
				case MotionEvent.ACTION_DOWN:
					reset(motionEvent);
					break;
				case MotionEvent.ACTION_MOVE:
					mTracker.addMovement(motionEvent);

					updateMovingMode(motionEvent);

					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					boolean result = actionUp(motionEvent);
					if (result)
						return true;
					break;
				}
				return true;
			}
		});
	}

	private boolean actionUp(MotionEvent motionEvent) {
		if (!isMovingModeOk())
			return false;
		if (isScrolling) {
			mTracker.computeCurrentVelocity(1000);

			float maximumVelocity = mConfiguration
					.getScaledMaximumFlingVelocity();
			mTracker.computeCurrentVelocity(1000, maximumVelocity);
			float yVelocity = mTracker.getYVelocity();
			float xVelocity = mTracker.getXVelocity();
			float scaleFlingVelocity = mConfiguration
					.getScaledMinimumFlingVelocity();
			if (Math.abs(yVelocity) < scaleFlingVelocity) {
				L.debug(TAG, "scroll up velocity=" + yVelocity
						+ " miniFlingVelocity=" + scaleFlingVelocity);
				if (null != mHeadViewListener) {
					return mHeadViewListener.onUp(motionEvent, movingMode);
				}
			} else {
				L.debug(TAG, "fling velocityY=" + yVelocity + " velocityX="
						+ xVelocity);
				if (null != mHeadViewListener) {
					return mHeadViewListener.onFling(xVelocity, yVelocity,
							movingMode);
				}
			}
		} else {
			L.debug(TAG, "do single up action");
			if (null != mHeadViewListener) {
				return mHeadViewListener.onSingleUp(motionEvent, movingMode);
			}

		}
		return false;
	}

	// @Override
	// public boolean onDown(MotionEvent e) {
	// return true;
	// }

	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// L.debug(this, "onFling velocityX:" + velocityX + " velocityY: "
	// + velocityY);
	// if (null != mHeadViewListener) {
	// return mHeadViewListener.onFling(velocityX, velocityY, movingMode);
	// }
	// return super.onFling(e1, e2, velocityX, velocityY);
	// }

	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// isScrolling = true;
	// return true;
	// }

	private void changeMoveDirection(float distanceY, float distanceX) {
		if (movingMode == MovingMode.UPDOWN) {
			if (distanceY > 0) {
				mMoveDirection = MOVEDIRECTION.DOWN;
			} else {
				mMoveDirection = MOVEDIRECTION.UP;
			}
		} else if (movingMode == MovingMode.LEFTRIGHT) {
			if (distanceX > 0) {
				mMoveDirection = MOVEDIRECTION.RIGHT;
			} else {
				mMoveDirection = MOVEDIRECTION.LEFT;
			}
		} else {
			L.debug(TAG, "changeMoveDirection moving mode is idle");
		}
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		L.debug(this, "onSingleTapConfirmed e: " + e.toString());
		if (null != mHeadViewListener) {
			return mHeadViewListener.onSingleUp(e, movingMode);
		}
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		if (null != mHeadViewListener) {
			return mHeadViewListener.onDoubleTap(e, movingMode);
		}

		return super.onDoubleTap(e);
	}

	public MOVEDIRECTION getmMoveDirection() {
		return mMoveDirection;
	}

	public double distance2UnitInterval(float distance, int limitation) {
		if (limitation <= 0)
			return 1.0;
		if (Math.abs(distance) >= limitation && distance > 0) {
			return 1.0;
		} else if (Math.abs(distance) >= limitation && distance < 0) {
			return -1.0;
		} else {
			return distance * 1.0 / limitation;
		}
	}

	public void printAction(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			L.debug(this, "action_down");
			break;
		case MotionEvent.ACTION_MOVE:
			L.debug(this, "action_move");
			break;
		case MotionEvent.ACTION_UP:
			L.debug(this, "action_up");
			break;
		case MotionEvent.ACTION_CANCEL:
			L.debug(this, "action_cancel");
			break;
		}
	}

	public void printEventXY(MotionEvent event, String prefix) {
		L.error(this, prefix + " X: " + event.getX() + " Y: " + event.getY()
				+ " rawX: " + event.getRawX() + " rawY: " + event.getRawY());
	}

}
