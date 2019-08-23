package com.basesmartframe.gesture;

import android.content.Context;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.sf.loglib.L;

public class SlidingLeftViewLayout extends FrameLayout {
    private final String TAG = "SlidingLeftViewLayout";

    public interface SlidingEvent {
        void onFinishSliding(boolean isOpen, SlidingLeftViewLayout viewLayout);

        void onStartSliding();
    }

    private Scroller mScroller;

    private VelocityTracker mVelocityTracker;

    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private boolean mIsBeingDragged = false;
    private boolean mIsUnableToDrag = false;
    private boolean mFakeDragging = false;

    private int mActivePointerId;
    private int _mActivePointerId;
    private final int INVALID_POINTER = -1;

    private float mLastMotionX = 0.0f;
    private float mLastMotionY = 0.0f;

    private float mInitialMotionY = 0.0f;
    private float mInitialMotionX = 0.0f;

    private float _mLastMotionX = 0.0f;
    private float _mLastMotionY = 0.0f;

    private float _mInitialMotionY = 0.0f;
    private float _mInitialMotionX = 0.0f;

    private int mCloseEnough = 0;

    private int mScrollState = SCROLL_STATE_IDLE;

    public static final int SCROLL_STATE_IDLE = 0;

    // Indicates that the pager is currently being dragged by the user.

    public static final int SCROLL_STATE_DRAGGING = 1;

    // Indicates that the pager is in the process of settling to a final
    // position.

    public static final int SCROLL_STATE_SETTLING = 2;

    private int mSlidingWidth = 0;

    private View mExtraView;

    public SlidingLeftViewLayout(Context context) {
        super(context);
        init();
    }

    public SlidingLeftViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration
                .get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mSlidingWidth = getResources().getDisplayMetrics().widthPixels;
    }

    public void setExtraView(View extraView) {
        mExtraView = extraView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.debug(TAG, "measure width: " + getMeasuredWidth());
        L.debug(TAG, "child measure width: " + getChildAt(0).getMeasuredWidth());
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();

        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                0, lp.height);

        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mSlidingWidth,
                MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child,
                                           int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child
                .getLayoutParams();

        final int childHeightMeasureSpec = getChildMeasureSpec(
                parentHeightMeasureSpec, 0 + lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                lp.leftMargin + lp.rightMargin, MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private void setScrollState(int newState) {
        if (mScrollState == newState) {
            return;
        }

        mScrollState = newState;
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance
            // first.
            for (int i = count - 1; i >= 0; i--) {
                // TODO: Add versioned support here for transformed views.
                // This will not work for transformed views in Honeycomb+
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft()
                        && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop()
                        && y + scrollY < child.getBottom()
                        && canScroll(child, true, dx,
                        x + scrollX - child.getLeft(), y + scrollY
                                - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && ViewCompat.canScrollHorizontally(v, -dx);
    }

    private void canInterceptTouchTriggerDrag(MotionEvent ev) {
        final int pointId = ev.getPointerId(0);
        final int pointerIndex = ev.findPointerIndex(pointId);
        final float x = ev.getX(pointerIndex);
        final float dx = x - _mLastMotionX;
        final float xDiff = Math.abs(dx);
        final float y = ev.getY(pointerIndex);
        final float yDiff = Math.abs(y - _mLastMotionY);
        L.debug(TAG, "Moved x to (" + x + "," + y + ") diff= (" + xDiff + ","
                + yDiff + ")");
        if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {// x direction drag
            L.debug(TAG, "Starting drag!");
            mIsBeingDragged = true;
            requestDisallowInterceptTouchEvent(true);
            setScrollState(SCROLL_STATE_DRAGGING);
            _mLastMotionX = dx > 0 ? _mInitialMotionX + mTouchSlop
                    : _mInitialMotionX - mTouchSlop;
            _mLastMotionY = y;
        } else if (yDiff > mTouchSlop) {// setting unnable drag
            L.debug(TAG, "Starting unable to drag!");
            mIsUnableToDrag = true;
        }
    }

    private void willDraggerHappen(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                canInterceptTouchTriggerDrag(ev);
                break;

            case MotionEvent.ACTION_DOWN:
                _mLastMotionX = _mInitialMotionX = ev.getX();
                _mLastMotionY = _mInitialMotionY = ev.getY();
                _mActivePointerId = ev.getPointerId(0);
                mIsUnableToDrag = false;
                mIsBeingDragged = false;

                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);

                mScroller.abortAnimation();
                L.debug(TAG, "intercept Down at " + _mLastMotionX + ","
                        + _mLastMotionY + " mIsBeingDragged=" + mIsBeingDragged
                        + "mIsUnableToDrag=" + mIsUnableToDrag);
                break;

        }

    }

    private void reset() {
        // Release the drag.
        mIsBeingDragged = false;
        mIsUnableToDrag = false;
        _mActivePointerId = INVALID_POINTER;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                SlidingLeftViewHelper.getSlidingViewInstance().hasOpenView() &&
                SlidingLeftViewHelper.getSlidingViewInstance().getOpenView() != this) {
            SlidingLeftViewHelper.getSlidingViewInstance().closeOpenView();
            return false;
        } else {
            final int action = ev.getAction();
            // Always take care of the touch gesture being complete.
            if (action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_UP) {
                if (isOpen()) {
                    doClose();
                }
                reset();
                return false;
            }

            // Nothing more to do here if we have decided whether or not we
            // are dragging.
            if (action != MotionEvent.ACTION_DOWN) {// this action is moving
                // action
                if (mIsBeingDragged) {
                    L.debug(TAG, "Intercept returning true!");
                    return true;
                }
                if (mIsUnableToDrag) {
                    L.debug(TAG, "Intercept returning false!");
                    return false;
                }
            }

            willDraggerHappen(ev);

            return mIsBeingDragged;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN && isOpen()) {
//            doClose();
//            return true;
//        } else {
        if (ev.getAction() == MotionEvent.ACTION_DOWN
                && ev.getEdgeFlags() != 0) {
            // Don't handle edge touches immediately -- they may actually
            // belong
            // to one of our
            // descendants.
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        boolean needsInvalidate = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mScroller.abortAnimation();
                // Remember where the motion event started
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                L.debug(TAG, "onTouch Down");
                break;
            }
            case MotionEvent.ACTION_MOVE:
                if (INVALID_POINTER == mActivePointerId) {
                    L.debug(TAG, "mActivePointerId is invalid");
                    mActivePointerId = ev.getPointerId(0);
                    break;
                }

                if (!mIsBeingDragged) {
                    canTouchTriggerDrag(ev);
                }
                // Not else! Note that mIsBeingDragged can be set above.
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    needsInvalidate = performDrag(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    needsInvalidate = performAnimation(ev);
                } else if (isOpen()) {
                    doClose();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    needsInvalidate = performAnimation(ev);
                    mActivePointerId = INVALID_POINTER;
                    endDrag();
                    needsInvalidate = true;
                }
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
//        }

    }

    private void canTouchTriggerDrag(MotionEvent ev) {
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        final float x = ev.getX(pointerIndex);
        final float xDiff = Math.abs(x - mLastMotionX);
        final float y = ev.getY(pointerIndex);
        final float yDiff = Math.abs(y - mLastMotionY);
        L.debug(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + ","
                + yDiff);
        if (xDiff > mTouchSlop && xDiff > yDiff) {
            L.debug(TAG, "Starting drag!");
            mIsBeingDragged = true;
            requestDisallowInterceptTouchEvent(true);
            mLastMotionX = x - mInitialMotionX > 0 ? mInitialMotionX
                    + mTouchSlop : mInitialMotionX - mTouchSlop;
            mLastMotionY = y;
            setScrollState(SCROLL_STATE_DRAGGING);

            // Disallow Parent Intercept, just in case
            ViewParent parent = getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
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
            setScrollState(SCROLL_STATE_IDLE);
            return;
        }

        setScrollState(SCROLL_STATE_SETTLING);
        mScroller.startScroll(sx, sy, dx, dy, 200);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void endDrag() {
        mIsBeingDragged = false;
        mIsUnableToDrag = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
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
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        } else if (mScroller.isFinished() || !mScroller.computeScrollOffset()) {
            if (isOpen()) {
                SlidingLeftViewHelper.getSlidingViewInstance().setOpenView(this);
            } else {
                SlidingLeftViewHelper.getSlidingViewInstance().setOpenView(null);
            }
        }

    }

    private boolean performDrag(MotionEvent ev) {
        final int pointId = ev.getPointerId(0);
        final int activePointerIndex = ev.findPointerIndex(pointId);
        final float x = ev.getX(activePointerIndex);
        final float y = ev.getY(activePointerIndex);// for later
        final float deltaX = mLastMotionX - x;
        mLastMotionX = x;

        float oldScrollX = getScrollX();
        float scrollX = oldScrollX + deltaX;
        final int width = getMeasuredWidth();

        float leftBound = 0;
        float rightBound = width - 20;

        L.debug(TAG, "drag leftBound=" + leftBound + " rightBound="
                + rightBound);

        if (scrollX < leftBound) {
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            scrollX = rightBound;
        }
        // Don't lose the rounded component
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
        L.debug(TAG, "scrollTo scrollX=" + scrollX);

        return true;
    }

    private boolean isOpen() {
        return mExtraView!=null&&getScrollX() == mExtraView.getWidth();
    }

    public void doClose() {
        smoothScrollTo(0, getScrollY(), 0);
    }


    private boolean performAnimation(MotionEvent ev) {
        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        int initialVelocity = 0;
        if (INVALID_POINTER == mActivePointerId) {
            L.error(TAG, "mActivePointerId is invalid");
            initialVelocity = (int) velocityTracker.getXVelocity();
        } else {
            initialVelocity = (int) velocityTracker
                    .getXVelocity(mActivePointerId);
        }

        final int width = mExtraView.getMeasuredWidth();
        final int scrollX = getScrollX();
        // final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
        // final float x = ev.getX(activePointerIndex);
        // final float y = ev.getY(activePointerIndex);
        int half = width / 2;
        int scrollY = getScrollY();
        boolean isFling = false;
        isFling = mMinimumVelocity < Math.abs(initialVelocity);
        if (!isFling) {
            if (scrollX > half / 2) {
                smoothScrollTo(width, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        } else {
            if (initialVelocity < 0) {
                smoothScrollTo(width, scrollY, initialVelocity);
            } else {
                smoothScrollTo(0, scrollY, initialVelocity);
            }
        }
        mActivePointerId = INVALID_POINTER;
        endDrag();
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SlidingLeftViewHelper.getSlidingViewInstance().setOpenView(null);
    }
}
