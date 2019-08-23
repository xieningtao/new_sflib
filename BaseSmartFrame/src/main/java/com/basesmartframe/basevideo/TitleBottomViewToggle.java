package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.core.view.GestureDetectorCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;

import com.basesmartframe.R;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.basesmartframe.basevideo.util.GuestureControl;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.sf.loglib.L;

/**
 * Created by xieningtao on 15-11-16.
 */
class TitleBottomViewToggle implements GuestureControl.GestureControlEvent {

    public interface ScrollSeekEvent {
        void onFinished(int seekPosition);

        void onStart();
    }

    private final String TAG = getClass().getName();
    private final VideoViewHolder mHolder;
    private final Context mContext;
    private double preX = -1;
    private boolean isback = false;

    private int groalSeekPosition = 0;

    private ScrollSeekEvent mSeekEvent;

    public TitleBottomViewToggle(Context context, VideoViewHolder viewHolder) {
        mHolder = viewHolder;
        this.mContext = context;
        init();
    }

    public void setSeekEvent(ScrollSeekEvent event) {
        this.mSeekEvent = event;
    }

    private void init() {
        final GestureDetectorCompat detector = new GestureDetectorCompat(mContext, new GuestureControl.VideoGesture(this));
        mHolder.mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                L.info(TAG, "videoshow touch action");
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mHolder.fast_control_tv.setVisibility(View.GONE);
                    if (groalSeekPosition != -1 && groalSeekPosition >= 0) {
                        if (mSeekEvent != null) {
                            mSeekEvent.onFinished(groalSeekPosition);
                        }
                        groalSeekPosition = -1;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP && View.GONE != mHolder.fast_control_tv.getVisibility()) {
                    mHolder.fast_control_tv.setVisibility(View.GONE);
                }
                return detector.onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onSingleEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleEevent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(int distance, MotionEvent e1, MotionEvent e2) {
        int curPosition = mHolder.mVideoView.getCurrentPosition();
        if (Math.abs(distance) == 0) distance = 1;
        int sign = distance / Math.abs(distance);
        int screenWidth = SystemUIWHHelp.getScreenRealWidth((Activity) mContext);
        float rate = Math.abs(distance) * 1.0f / screenWidth * 120;
        int seekPosition = (int) (curPosition + rate * 1000 * sign);
        int total = mHolder.mVideoView.getDuration();
        if (seekPosition < 0) {
            seekPosition = 0;
        } else if (seekPosition > total) {
            seekPosition = total;
        }
        boolean directionLeft = true;
        if (preX == -1) {
            preX = e1.getRawX();
        }
        directionLeft = !(e2.getRawX() - preX > 0);
        preX = e2.getRawX();
        if (e1.getAction() == MotionEvent.ACTION_DOWN) {
            L.info(TAG, "scroll action down");
            showFast(directionLeft, seekPosition, total);
        }

        if (e2.getAction() == MotionEvent.ACTION_UP) {
            L.info(TAG, "scroll action up");
            hideFast();
        }
        groalSeekPosition = seekPosition;
        return false;
    }

    private void showFast(boolean back, int seek, int total) {
        if (mHolder.fast_control_tv.getVisibility() != View.VISIBLE) {
            mHolder.fast_control_tv.setVisibility(View.VISIBLE);
        }
        String time = TimeUtil.getMSFormatTime(seek) + "/" + TimeUtil.getMSFormatTime(total);
        SpannableString spannableString = new SpannableString(time);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffff6600")), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mHolder.fast_control_tv.setText(spannableString);
        if (isback == back) return;
        isback = back;
        if (back) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.videoshow_fast_backward);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mHolder.fast_control_tv.setCompoundDrawables(null, drawable, null, null);
            L.info(TAG, "show back");
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.videoshow_fast_foward);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mHolder.fast_control_tv.setCompoundDrawables(null, drawable, null, null);
            L.info(TAG, "show forward");
        }

    }

    private void hideFast() {
        mHolder.fast_control_tv.setVisibility(View.GONE);
    }
}
