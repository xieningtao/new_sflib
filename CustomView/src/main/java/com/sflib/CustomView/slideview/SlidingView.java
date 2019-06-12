package com.sflib.CustomView.slideview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by xieningtao on 15-11-8.
 */
public class SlidingView extends FrameLayout {
    private final String TAG = "SlidingView";
    private Scroller mScroller;

    private VelocityTracker mVelocityTracker;

    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private boolean mIsBeingDragged = false;
    private boolean mIsUnableToDrag = false;


    private float mLastDownX = 0.0f;
    private float mLastDownY = 0.0f;

    private float mLastMotionX = 0.0f;
    private float mLastMotionY = 0.0f;

    private float mRightBound = 0.0f;
    private float mLeftBound = 0.0f;

    private SlidingEvent mSlidingEvent;
    private SlidingEvent.SlidingMode mNextMode = SlidingEvent.SlidingMode.NONE;

    public SlidingView(Context context) {
        super(context);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration
                .get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

    }

    public void setSlidingEvent(SlidingEvent event) {
        this.mSlidingEvent = event;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    private void checkMotionEvents(MotionEvent ev) {
        final int pointId = ev.getPointerId(0);
        final int pointerIndex = ev.findPointerIndex(pointId);
        final float x = ev.getX(pointerIndex);
        final float dx = x - mLastMotionX;
        final float xDiff = Math.abs(dx);
        final float y = ev.getY(pointerIndex);
        final float yDiff = Math.abs(y - mLastMotionY);
        Log.i(TAG, "Moved x to (" + x + "," + y + ") diff= (" + xDiff + ","
                + yDiff + ")");
        if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {// x direction drag
            Log.i(TAG, "Starting drag!");
            mIsBeingDragged = true;
            requestDisallowInterceptTouchEvent(true);
            mLastMotionX = dx > 0 ? mLastDownX + mTouchSlop
                    : mLastDownX - mTouchSlop;
            mLastMotionY = y;

            if (isCurSlideClosed()) {
                if (dx > 0) {
                    mNextMode = SlidingEvent.SlidingMode.LEFT_OPEN;
                } else {
                    mNextMode = SlidingEvent.SlidingMode.RIGHT_OPEN;
                }
            } else {
                if (dx > 0) {
                    if (mNextMode == SlidingEvent.SlidingMode.RIGHT_OPEN) {
                        mNextMode = SlidingEvent.SlidingMode.RIGHT_CLOSE;
                    }
                } else {
                    if (mNextMode == SlidingEvent.SlidingMode.LEFT_OPEN) {
                        mNextMode = SlidingEvent.SlidingMode.LEFT_CLOSE;
                    }
                }
            }

        } else if (yDiff > mTouchSlop) {// setting unnable drag
            Log.i(TAG, "Starting unable to drag!");
            mIsUnableToDrag = true;
        }
        Log.i(TAG, "method->checkMotionEvents,mNextMode: " + mNextMode.name());
    }

    private boolean isCurSlideClosed() {
        return mNextMode == SlidingEvent.SlidingMode.NONE || mNextMode == SlidingEvent.SlidingMode.RIGHT_CLOSE || mNextMode == SlidingEvent.SlidingMode.LEFT_CLOSE;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged && !mIsUnableToDrag) {
            Log.i(TAG, "do fast motion event");
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    checkMotionEvents(ev);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                initXY(ev);
                mVelocityTracker = VelocityTracker.obtain();
                mIsBeingDragged = false;
                mIsUnableToDrag = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int half = getMeasuredWidth() / 2;
                int scrollX = getScrollX();
                if (scrollX == half) {
                    mNextMode = SlidingEvent.SlidingMode.RIGHT_OPEN;
                } else if (scrollX == 0) {
                    mNextMode = SlidingEvent.SlidingMode.NONE;
                } else if (scrollX == -half) {
                    mNextMode = SlidingEvent.SlidingMode.LEFT_OPEN;
                }
                Log.i(TAG, "method->onInterceptTouchEvent,down event,mNextMode: " + mNextMode);
                break;
        }
        return mIsBeingDragged && (!mIsUnableToDrag);
    }

    private void initXY(MotionEvent ev) {
        mLastMotionX = mLastDownX = ev.getX();
        mLastMotionY = mLastDownY = ev.getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                performUpAnimation(event);
                if (null != mSlidingEvent) {
                    mSlidingEvent.onFinishSliding(mNextMode);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                if (!mIsBeingDragged) {
                    checkMotionEvents(event);
                }
                if (mIsBeingDragged) {
                    if (mSlidingEvent != null) {
                        mSlidingEvent.onStartSliding(mNextMode);
                    }
                    performDrag(event);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                initXY(event);
                break;
        }
        return true;
    }

    private void performDrag(MotionEvent ev) {
        final int pointId = ev.getPointerId(0);
        final int activePointerIndex = ev.findPointerIndex(pointId);
        final float x = ev.getX(activePointerIndex);
        final float y = ev.getY(activePointerIndex);// for later
        final float deltaX = mLastMotionX - x;
        final float deltaY = mLastMotionY - y;
        mLastMotionX = x;
        mLastMotionY = y;
        float oldScrollX = getScrollX();
        float scrollX = oldScrollX + deltaX;
        float scrollY = oldScrollX + deltaY;

        final int width = getMeasuredWidth();


        float leftBound = -width / 2;
        float rightBound = width / 2;
        if (mNextMode == SlidingEvent.SlidingMode.LEFT_CLOSE) {
            leftBound = -width / 2;
            rightBound = 0;
        } else if (mNextMode == SlidingEvent.SlidingMode.LEFT_OPEN) {
            leftBound = -width / 2;
            rightBound = 0;
        } else if (mNextMode == SlidingEvent.SlidingMode.RIGHT_OPEN) {
            rightBound = width / 2;
            leftBound = 0;
        } else if (mNextMode == SlidingEvent.SlidingMode.RIGHT_CLOSE) {
            rightBound = width / 2;
            leftBound = 0;
        }

        mLeftBound = leftBound;
        mRightBound = rightBound;

//        Log.i(TAG, "drag leftBound=" + leftBound + " rightBound="
//                + rightBound);

        if (scrollX < leftBound) {
            scrollX = leftBound;
        }
        if (scrollX > rightBound) {
            scrollX = rightBound;
        }
        // Don't lose the rounded component
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
//        Log.i(TAG, "scrollTo scrollX=" + scrollX);
        invalidate();
        if (null != mSlidingEvent) {
            mSlidingEvent.onSliding(mNextMode);
        }
    }

    private boolean performUpAnimation(MotionEvent ev) {
        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        int initialVelocity = (int) velocityTracker
                .getXVelocity(0);

        final int width = getMeasuredWidth();
        final int scrollX = getScrollX();
        // final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
        // final float x = ev.getX(activePointerIndex);
        // final float y = ev.getY(activePointerIndex);
        int half = width / 2;
        int scrollY = getScrollY();
        boolean isFling = false;
        if (mMinimumVelocity < Math.abs(initialVelocity)) {
            isFling = true;
        } else {
            isFling = false;
        }
        Log.i(TAG, "method->performUpAnimation,isFling: " + isFling + " mNextMode: " + mNextMode);
        if (!isFling) {
            scrollByDistance(initialVelocity, width, scrollX, half, scrollY);
        } else {
            scrollByVelocity(initialVelocity, width, scrollY);
        }
        return true;
    }

    private void scrollByVelocity(int initialVelocity, int width, int scrollY) {
        if (mNextMode == SlidingEvent.SlidingMode.RIGHT_CLOSE) {
            if (initialVelocity < 0) {
                smoothScrollTo(width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else if (mNextMode == SlidingEvent.SlidingMode.RIGHT_OPEN) {
            if (initialVelocity < 0) {
                smoothScrollTo(width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else if (mNextMode == SlidingEvent.SlidingMode.LEFT_OPEN) {
            if (initialVelocity > 0) {
                smoothScrollTo(-width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else if (mNextMode == SlidingEvent.SlidingMode.LEFT_CLOSE) {
            if (initialVelocity > 0) {
                smoothScrollTo(-width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        }
    }

    private void scrollByDistance(int initialVelocity, int width, int scrollX, int half, int scrollY) {
        if (mNextMode == SlidingEvent.SlidingMode.RIGHT_CLOSE) {
            if (scrollX > half / 2) {
                smoothScrollTo(width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else if (mNextMode == SlidingEvent.SlidingMode.RIGHT_OPEN) {
            if (scrollX > half / 2) {
                smoothScrollTo(width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else if (mNextMode == SlidingEvent.SlidingMode.LEFT_CLOSE) {
            if (scrollX < -half / 2) {
                smoothScrollTo(-width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }

        } else if (mNextMode == SlidingEvent.SlidingMode.LEFT_OPEN) {
            if (scrollX < -half / 2) {
                smoothScrollTo(-width / 2, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        }
    }

    void smoothScrollTo(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            // Nothing to do.
            return;
        }
        int sx = getScrollX();
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (dx == 0 && dy == 0) {
            return;
        }
        mScroller.startScroll(sx, sy, dx, dy, 200);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (oldX != x || oldY != y) {
                scrollTo(x, y);

            }

            // Keep on drawing until the animation has finished.
            invalidate();
            if (null != mSlidingEvent) {
                mSlidingEvent.onSliding(mNextMode);
            }
            return;
        }

    }
}
