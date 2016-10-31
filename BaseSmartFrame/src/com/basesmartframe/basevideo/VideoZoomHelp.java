package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.basesmartframe.R;
import com.basesmartframe.basevideo.util.ToggelSystemUIHelp;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SystemUIWHHelp;

/**
 * Created by xieningtao on 15-11-16.
 */
class VideoZoomHelp {
    private final VideoViewHolder mHolder;
    private final Context mContext;

    public VideoZoomHelp(Context context, VideoViewHolder holder) {
        mHolder = holder;
        this.mContext = context;
    }

    private void updateVideoToFullMode() {
        L.info(this, "to full action");
        RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams) mHolder.mVideoView.getLayoutParams();
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height=SystemUIWHHelp.getScreenRealHeight((Activity) mContext);
        mHolder.mVideoView.setLayoutParams(layoutParams);
    }

    private void updateVideoToHalfMode() {
        L.info(this, "to half action");
        Activity activity = (Activity) mContext;
        final int width = SystemUIWHHelp.getScreenRealWidth(activity);
        final int height = width * 9 / 16;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mHolder.mVideoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mHolder.mVideoView.setLayoutParams(layoutParams);
    }


    private void updateViewToFullMode() {
        ToggelSystemUIHelp.configNavigationBarOritation((Activity) mContext);
        ToggelSystemUIHelp.toggleScreenView(mContext, true);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mHolder.title_ll.getLayoutParams();
        params.setMargins(0, SystemUIWHHelp.getStatusBarHeight(mContext), 0, 0);
        mHolder.zoom_iv.setImageResource(R.drawable.videoshow_zoomin_normal);
        mHolder.title_ll.setVisibility(View.VISIBLE);

        if (SystemUIWHHelp.hasNavigationBar(mContext)) {
            RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) mHolder.title_ll.getLayoutParams();
            int rightMargin = title_params.rightMargin + SystemUIWHHelp.getNavigationBarLandscapeWidth(mContext);
            title_params.setMargins(title_params.leftMargin, title_params.topMargin, rightMargin, title_params.bottomMargin);
            mHolder.title_ll.setLayoutParams(title_params);

            RelativeLayout.LayoutParams cotrol_params = (RelativeLayout.LayoutParams) mHolder.control_ll.getLayoutParams();
            cotrol_params.setMargins(cotrol_params.leftMargin, cotrol_params.topMargin, rightMargin, cotrol_params.bottomMargin);
            mHolder.control_ll.setLayoutParams(cotrol_params);

        }
        mHolder.control_ll.setVisibility(View.VISIBLE);
    }

    private void updateViewToHalfMode() {
        mHolder.zoom_iv.setImageResource(R.drawable.videoshow_zoomout_normal);
        ToggelSystemUIHelp.resetScreen(mContext);
        mHolder.title_ll.setVisibility(View.GONE);

        if (SystemUIWHHelp.hasNavigationBar(mContext)) {
            RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) mHolder.title_ll.getLayoutParams();
            int rightMargin = 0;
            title_params.setMargins(title_params.leftMargin, title_params.topMargin, rightMargin, title_params.bottomMargin);
            mHolder.title_ll.setLayoutParams(title_params);

            RelativeLayout.LayoutParams cotrol_params = (RelativeLayout.LayoutParams) mHolder.control_ll.getLayoutParams();
            cotrol_params.setMargins(cotrol_params.leftMargin, cotrol_params.topMargin, rightMargin, cotrol_params.bottomMargin);
            mHolder.control_ll.setLayoutParams(cotrol_params);
        }
        mHolder.control_ll.setVisibility(View.VISIBLE);

    }

    public void changeMode(boolean full) {
        if (full) {
            updateVideoToFullMode();
//            updateViewToFullMode();
        } else {
            updateVideoToHalfMode();
//            updateViewToHalfMode();
        }
    }
}
