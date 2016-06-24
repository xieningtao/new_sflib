package com.basesmartframe.basevideo.util;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.sf.loglib.L;


/**
 * Created by xieningtao on 15-3-29.
 */
public class GuestureControl {
    public  interface GestureControlEvent {
        boolean onSingleEvent(MotionEvent e);

        boolean onDoubleEevent(MotionEvent e);

        boolean onScroll(int distance, MotionEvent e1, MotionEvent e2);
    }

    public static class VideoGesture extends GestureDetector.SimpleOnGestureListener {
        private GestureControlEvent event;

        public VideoGesture(GestureControlEvent event) {
            this.event = event;
        }

        public VideoGesture() {
            this(null);
        }

        @Override
        public boolean onDown(final MotionEvent e) {

            return true;
        }


        @Override
        public boolean onDoubleTap(MotionEvent e) {

            L.info("VideoShowActivity", "double tap");
            if (event != null) {
                event.onDoubleEevent(e);
            }
            return true;
        }

        @Override
        public boolean onScroll(final MotionEvent e1, final MotionEvent e2, float distanceX, float distanceY) {
            final int distancex = distanceX(e1, e2);
            final int distancey = distanceY(e1, e2);
            L.info("VideoShowActivity", "onScroll distanceX: " + distancex + " distanceY: " + distanceY);
            if (Math.abs(distanceY) > Math.abs(distanceX)) return true;
            if (event != null) event.onScroll(distancex, e1, e2);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            L.info("VideoShowActivity", "onSingleTapConfirmed");
            if (null != event) {
                return event.onSingleEvent(e);
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            L.info("VideoShowActivity", "onshowPress");
        }

        private int distanceX(MotionEvent e1, MotionEvent e2) {
            return (int) (e2.getRawX() - e1.getRawX());
        }

        private int distanceY(MotionEvent e1, MotionEvent e2) {
            return (int) (e2.getRawY() - e1.getRawY());
        }

        public void setEvent(GestureControlEvent event) {
            this.event = event;
        }

    }
}
