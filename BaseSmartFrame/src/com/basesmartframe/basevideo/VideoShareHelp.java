package com.basesmartframe.basevideo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.basesmartframe.R;
import com.basesmartframe.dialoglib.DialogFactory;
import com.basesmartframe.share.DefaultShareAdapter;
import com.basesmartframe.share.ShareContent;
import com.basesmartframe.share.XSocialShareView;
import com.basesmartframe.share.sharecore.OnXBaseShareViewItemClickListener;
import com.basesmartframe.share.shareitem.XBaseShareItem;

/**
 * Created by xieningtao on 15-11-16.
 */
class VideoShareHelp {

    private final String TAG = getClass().getName();
    private Dialog mDialog;

    private final Context mContext;
    private final ViewGroup mShareContainer;
    private ShareContent mShareContent;

    public VideoShareHelp(Context context, View rootView) {
        this.mContext = context;
        mShareContainer = (ViewGroup) rootView.findViewById(R.id.view_container);

    }

    public void showPortraitDialog() {
        if (null == mDialog) {
            View share_view = LayoutInflater.from(mContext).inflate(R.layout.videoshow_share, null);
            share_view.findViewById(R.id.share_bk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mDialog && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                }
            });
            share_view.findViewById(R.id.share_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mDialog && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                }
            });
            XSocialShareView shareGridView = (XSocialShareView) share_view.findViewById(R.id.videoshow_share);
            initShareGridView(shareGridView);
            shareGridView.setOnXBaseShareViewItemClickListener(new OnXBaseShareViewItemClickListener() {
                @Override
                public void onXBaseShareViewItemClickListener(XBaseShareItem shareItem, AdapterView<?> parent, View view, int position, long id) {
                    if (null != mDialog && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                }
            });

            mDialog = DialogFactory.getMatchParentDialog(mContext, share_view);
        }
        if (mDialog.isShowing()) return;
        mDialog.show();
    }

    private void initShareGridView(XSocialShareView shareGridView) {
        shareGridView.setShareContent(mShareContent);
        shareGridView.setShareAdapter(new DefaultShareAdapter());
    }

    public void showLandscapeDialog() {
        if (mShareContainer.getChildCount() > 0) {
            mShareContainer.removeAllViews();
        }
        View share_view = LayoutInflater.from(mContext).inflate(R.layout.videoshow_ls_share, mShareContainer);

        share_view.findViewById(R.id.share_bk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimissShareView();
            }
        });

        XSocialShareView shareGridView = (XSocialShareView) share_view.findViewById(R.id.videoshow_share);
        initShareGridView(shareGridView);
        shareGridView.setOnXBaseShareViewItemClickListener(new OnXBaseShareViewItemClickListener() {
            @Override
            public void onXBaseShareViewItemClickListener(XBaseShareItem shareItem, AdapterView<?> parent, View view, int position, long id) {
                dimissShareView();
            }
        });

        if (View.VISIBLE != mShareContainer.getVisibility()) {
            mShareContainer.setVisibility(View.VISIBLE);
        }
        mShareContainer.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_from_right);
        mShareContainer.startAnimation(animation);
    }

    public boolean isShareViewShow() {
        return null != mShareContainer && View.VISIBLE == mShareContainer.getVisibility() && mShareContainer.getChildCount() > 0;
    }

    public boolean isShareViewAnimating() {
        return null != mShareContainer && mShareContainer.findViewById(R.id.share_bk).isEnabled() == false;
    }


    public void dimissShareView() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_to_right);
        mShareContainer.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mShareContainer.findViewById(R.id.share_bk).setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mShareContainer.setVisibility(View.GONE);
                mShareContainer.findViewById(R.id.share_bk).setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
