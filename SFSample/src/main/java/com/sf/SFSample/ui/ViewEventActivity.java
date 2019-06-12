package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.sf.SFSample.R;
import com.sflib.CustomView.viewevent.EventHandlerManager;
import com.sflib.CustomView.viewevent.EventInfoUtil;
import com.sflib.CustomView.viewevent.handler.ViewEventHandler;
import com.sflib.CustomView.viewevent.handler.ViewGroupEventHandler;
import com.sflib.CustomView.viewevent.view.MyLinearLayout;
import com.sflib.CustomView.viewevent.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

public class ViewEventActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private EventHandlerManager dispatchTouchHandler = new EventHandlerManager();
    private EventHandlerManager onTouchHandler = new EventHandlerManager();

    private final String dispatchTouchLogMark = "----------Activity dispatchTouchEvent-----------";
    private final String onTouchLogMark = "----------Activity onTouchEvent-----------";
    private final String superDispatchTouchLogMark = "----------Activity super dispatchTouchEvent-----------";
    private final String superOnTouchLogMark = "----------Activity super onTouchEvent-----------";

    /**
     * Called when the activity is first created.
     */

    private MyLinearLayout mMyLinearLayout;
    private MyTextView mMyTextView;

    private ViewEventHandler mMyTextViewHandler;
    private ViewGroupEventHandler mMyLinearLayoutHandler;

    private List<Integer> mTvDispatchHanlderEvents = new ArrayList<>();
    private List<Integer> mTvTouchHanlderEvents = new ArrayList<>();
    private List<Integer> mTvTouchListenerHanlderEvents = new ArrayList<>();

    private List<Integer> mLlDispatchHanlderEvents = new ArrayList<>();
    private List<Integer> mLlTouchHanlderEvents = new ArrayList<>();
    private List<Integer> mLlIntercepterHanlderEvents = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewevent_main);
        init();
        initToggleView();
    }

    private void init() {
        mMyLinearLayout = (MyLinearLayout) findViewById(R.id.ll);
        mMyTextView = (MyTextView) findViewById(R.id.tv);

        mMyTextViewHandler = new ViewEventHandler(mMyTextView);
        mMyLinearLayoutHandler = new ViewGroupEventHandler(mMyLinearLayout);
        //refactor it later
        mMyTextView.setEventListener(mMyTextViewHandler);
        mMyLinearLayout.setEventListener(mMyLinearLayoutHandler);

    }

    private void initToggleView() {
        ToggleButton ll_dispatch_down_tv = (ToggleButton) findViewById(R.id.ll_dispatch_down_tb);
        ToggleButton ll_dispatch_move_tv = (ToggleButton) findViewById(R.id.ll_dispatch_move_tb);
        ToggleButton ll_touch_down_tv = (ToggleButton) findViewById(R.id.ll_touch_down_tb);
        ToggleButton ll_touch_move_tv = (ToggleButton) findViewById(R.id.ll_touch_move_tb);
        ToggleButton ll_intercept_down_tv = (ToggleButton) findViewById(R.id.ll_intercept_down_tb);
        ToggleButton ll_intercept_move_tv = (ToggleButton) findViewById(R.id.ll_intercept_move_tb);

        ll_dispatch_down_tv.setOnCheckedChangeListener(this);
        ll_dispatch_move_tv.setOnCheckedChangeListener(this);
        ll_touch_down_tv.setOnCheckedChangeListener(this);
        ll_touch_move_tv.setOnCheckedChangeListener(this);
        ll_intercept_down_tv.setOnCheckedChangeListener(this);
        ll_intercept_move_tv.setOnCheckedChangeListener(this);


        ToggleButton tv_dispatch_down_tv = (ToggleButton) findViewById(R.id.tv_dispatch_down_tb);
        ToggleButton tv_dispatch_move_tv = (ToggleButton) findViewById(R.id.tv_dispatch_move_tb);
        ToggleButton tv_touch_down_tv = (ToggleButton) findViewById(R.id.tv_touch_down_tb);
        ToggleButton tv_touch_move_tv = (ToggleButton) findViewById(R.id.tv_touch_move_tb);
        ToggleButton tv_touch_listener_down_tv = (ToggleButton) findViewById(R.id.tv_touchlistener_down_tb);
        ToggleButton tv_touch_listener_move_tv = (ToggleButton) findViewById(R.id.tv_touchlistener_move_tb);

        tv_dispatch_down_tv.setOnCheckedChangeListener(this);
        tv_dispatch_move_tv.setOnCheckedChangeListener(this);
        tv_touch_down_tv.setOnCheckedChangeListener(this);
        tv_touch_move_tv.setOnCheckedChangeListener(this);
        tv_touch_listener_down_tv.setOnCheckedChangeListener(this);
        tv_touch_listener_move_tv.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(EventInfoUtil.TAG, dispatchTouchLogMark);
        boolean isConsumed = dispatchTouchHandler.handleEvent(ev);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        if (isConsumed) return isConsumed;
        isConsumed = super.dispatchTouchEvent(ev);
        Log.i(EventInfoUtil.TAG, superDispatchTouchLogMark);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        return isConsumed;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(EventInfoUtil.TAG, onTouchLogMark);
        boolean isConsumed = onTouchHandler.handleEvent(ev);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        if (isConsumed) return isConsumed;
        isConsumed = super.onTouchEvent(ev);
        Log.i(EventInfoUtil.TAG, superOnTouchLogMark);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        return isConsumed;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        switch (viewId) {
            case R.id.ll_dispatch_down_tb:
                addOrRemoveLlDispatchEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.ll_dispatch_move_tb:
                addOrRemoveLlDispatchEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;
            case R.id.ll_touch_down_tb:
                addOrRemoveLlTouchEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.ll_touch_move_tb:
                addOrRemoveLlTouchEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;
            case R.id.ll_intercept_down_tb:
                addOrRemoveLlInterceptEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.ll_intercept_move_tb:
                addOrRemoveLlInterceptEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;


            case R.id.tv_dispatch_down_tb:
                addOrRemoveTvDispatchEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.tv_dispatch_move_tb:
                addOrRemoveTvDispatchEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;
            case R.id.tv_touch_down_tb:
                addOrRemoveTvTouchEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.tv_touch_move_tb:
                addOrRemoveTvTouchEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;
            case R.id.tv_touchlistener_down_tb:
                addOrRemoveTvTouchListenerEvent(isChecked, MotionEvent.ACTION_DOWN);
                break;
            case R.id.tv_touchlistener_move_tb:
                addOrRemoveTvTouchListenerEvent(isChecked, MotionEvent.ACTION_MOVE);
                break;
        }

        mMyTextViewHandler.setOnTouchHandlerEvents(mTvTouchHanlderEvents);
        mMyTextViewHandler.setDispatchTouchHandlerEvents(mTvDispatchHanlderEvents);
        mMyTextViewHandler.setOnTouchListenerHandlerEvent(mTvTouchListenerHanlderEvents);

        mMyLinearLayoutHandler.setDispatchTouchHandlerEvents(mLlDispatchHanlderEvents);
        mMyLinearLayoutHandler.setOnTouchHandlerEvents(mLlTouchHanlderEvents);
        mMyLinearLayoutHandler.setOnInterceptHandlerEvents(mLlIntercepterHanlderEvents);
    }

    private void addOrRemoveLlDispatchEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mLlDispatchHanlderEvents.add(actionMove);
        } else {
            mLlDispatchHanlderEvents.remove(actionMove);
        }
    }

    private void addOrRemoveLlTouchEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mLlTouchHanlderEvents.add(actionMove);
        } else {
            mLlTouchHanlderEvents.remove(actionMove);
        }
    }

    private void addOrRemoveLlInterceptEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mLlIntercepterHanlderEvents.add(actionMove);
        } else {
            mLlIntercepterHanlderEvents.remove(actionMove);
        }
    }

    private void addOrRemoveTvDispatchEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mTvDispatchHanlderEvents.add(actionMove);
        } else {
            mTvDispatchHanlderEvents.remove(actionMove);
        }
    }

    private void addOrRemoveTvTouchEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mTvTouchHanlderEvents.add(actionMove);
        } else {
            mTvTouchHanlderEvents.remove(actionMove);
        }
    }

    private void addOrRemoveTvTouchListenerEvent(boolean isChecked, Integer actionMove) {
        if (isChecked) {
            mTvTouchListenerHanlderEvents.add(actionMove);
        } else {
            mTvTouchListenerHanlderEvents.remove(actionMove);
        }
    }
}
