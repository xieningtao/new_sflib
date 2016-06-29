package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.basesmartframe.R;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.basesmartframe.basevideo.util.ToggelSystemUIHelp;
import com.sf.loglib.L;

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
//        ViewGroup.LayoutParams params = mHolder.mVideoView.getLayoutParams();
//        if (null != params) {
            Activity activity = (Activity) mContext;
            final int width = SystemUIWHHelp.getScreenRealWidth(activity);
            final int height = SystemUIWHHelp.getScreenRealHeight(activity);
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    mHolder.mVideoView.getHolder().setFixedSize(width, height);
//                }
//            }, 100);
//            params.width = width;
//            params.height = height;
//            mHolder.mRootView.setLayoutParams(params);
//            L.info(this, "full oritation width: " + width + " height: " + height);
//        } else {
//            L.error(this, "getLayoutParams is null");
//        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        mHolder.mVideoView.setLayoutParams(params);

    }

    private void updateVideoToHalfMode() {
        L.info(this, "to half action");
//        ViewGroup.LayoutParams params = mHolder.mRootView.getLayoutParams();
//        if (null != params) {
        Activity activity = (Activity) mContext;
        final int width = SystemUIWHHelp.getScreenRealWidth(activity);
        final int height = width * 9 / 16;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mHolder.mVideoView.getHolder().setFixedSize(width, height);
//                }
//            }, 100);
//            params.width = width;
//            params.height = height;
//            mHolder.mRootView.setLayoutParams(params);
//            L.info(this, "half orientation width: " + width + " height: " + height);
//
//        } else {
//            L.error(this, "mOriginParams is null");
//        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        mHolder.mVideoView.setLayoutParams(params);
    }


    private void updateViewToFullMode() {
        ToggelSystemUIHelp.configNavigationBarOritation((Activity) mContext);
        ToggelSystemUIHelp.toggleScreenView(mContext, true);
//            videoshow_pt_back.setVisibility(View.GONE);
//            videoshow_pt_share.setVisibility(View.GONE);
//            videoshow_ls_share.setVisibility(View.VISIBLE);
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

//                RelativeLayout.LayoutParams share_ls_params = (RelativeLayout.LayoutParams) videoshow_ls_share.getLayoutParams();
//                share_ls_params.setMargins(share_ls_params.leftMargin, share_ls_params.topMargin, rightMargin, share_ls_params.bottomMargin);
//                videoshow_ls_share.setLayoutParams(share_ls_params);
        }
        mHolder.control_ll.setVisibility(View.VISIBLE);
    }

    private void updateViewToHalfMode() {
//            videoshow_pt_back.setVisibility(View.VISIBLE);
//            videoshow_pt_share.setVisibility(View.VISIBLE);
//            videoshow_ls_share.setVisibility(View.GONE);
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
//                RelativeLayout.LayoutParams share_ls_params = (RelativeLayout.LayoutParams) videoshow_ls_share.getLayoutParams();
//                share_ls_params.setMargins(share_ls_params.leftMargin, share_ls_params.topMargin, rightMargin, share_ls_params.bottomMargin);
//                videoshow_ls_share.setLayoutParams(share_ls_params);

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
