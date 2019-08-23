package com.sf.utils.baseutil;


import com.sf.loglib.L;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class VideoGestureControl {

	    public interface GestureControlEvent{
	        boolean onSingleEvent(MotionEvent e);
	        boolean onDoubleEevent(MotionEvent e);
	        boolean onScroll(int distance);
	    }

	    public static class VideoGesture extends GestureDetector.SimpleOnGestureListener{
	        private GestureControlEvent event;
	        private boolean isDoubletap=false;
	        private boolean isScroll=false;
	        private Handler handler=new Handler();
	        private Runnable curRunnable;
	        private ViewConfiguration configuration;

	        private int state=0;//0代表down 1代表doubleTap

	        public VideoGesture(GestureControlEvent event){
	            this.event=event;
	            configuration=new ViewConfiguration();
	        }

	        @Override
	        public boolean onDown(final MotionEvent e) {
	            if(state==0){
	                isDoubletap=false;
	                isScroll=false;
	                if(curRunnable!=null){
	                    handler.removeCallbacks(curRunnable);
	                }
	                curRunnable=new Runnable() {
	                    @Override
	                    public void run() {
	                        if (!isScroll&&!isDoubletap&&event!=null){
	                            event.onSingleEvent(e);
	                            L.error("VideoShowActivity", "onDown");
	                        }
	                        curRunnable=null;
	                    }
	                };
	                handler.postDelayed(curRunnable, ViewConfiguration.getTapTimeout());
	            }else if(state==1){
	                state=0;
	            }
	            return true;
	        }
	        @Override
	        public boolean onDoubleTap(MotionEvent e) {
	            isDoubletap=true;
	            state=1;
	            if(curRunnable!=null){
	                handler.removeCallbacks(curRunnable);
	                curRunnable=null;
	            }
	            if(event!=null){
	                event.onDoubleEevent(e);
	                L.info("VideoShowActivity", "double tap");
	            }
	            return true;
	        }

	        @Override
	        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	            if(curRunnable!=null){
	                handler.removeCallbacks(curRunnable);
	                curRunnable=null;
	            }
	            isScroll=true;
	            int distance=distance(e1,e2);
	            L.info("VideoShowActivity","onScroll distanceX: "+distance);
	            if(event!=null)event.onScroll(distance);
	            return true;
	        }

	        @Override
	        public void onShowPress(MotionEvent e) {
	            super.onShowPress(e);
	            L.info("VideoShowActivity","onshowPress");
	        }

	        private int distance(MotionEvent e1, MotionEvent e2){
	            return (int)(e2.getRawX()-e1.getRawX());
	        }
	    }
}

