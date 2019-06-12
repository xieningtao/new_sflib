package com.sf.utils.baseutil;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.loglib.L;
import com.sf.utils.R;

/**
 * Created by NetEase on 2016/8/16 0016.
 */
public class SFToast {

    private static SFToastBuild toastBuild;

    public static void configDefaultToast(Context context) {
        toastBuild = new SFToastBuild(context, R.layout.custom_toast, R.id.toast_tv);
    }

    public static void configCustomToast(Context context, int rootViewId, int textViewId) {
        toastBuild = new SFToastBuild(context, rootViewId, textViewId);
    }

    public static void configCustomToast(Context context, View rootView, int textViewId) {
        toastBuild = new SFToastBuild(context, rootView, textViewId);
    }

    public static boolean showToast(String text) {
        if (toastBuild == null) {
            return false;
        }
        return toastBuild.showToast(text);
    }

    public static boolean showToast(int textId) {
        if (toastBuild == null) {
            return false;
        }
        return toastBuild.showToast(textId);
    }

    public static class SFToastBuild {
        private final String TAG = SFToast.class.getName();
        private View mRootView;
        private TextView mTextView;
        private Context mContext;

        public SFToastBuild(Context context, int rootViewId, int textViewId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            mRootView = inflater.inflate(rootViewId, null);
            mTextView = (TextView) mRootView.findViewById(textViewId);
            this.mContext = context;
        }

        public SFToastBuild(Context context, View rootView, int textViewId) {
            this.mContext = context;
            this.mRootView = rootView;
            mTextView = (TextView) mRootView.findViewById(textViewId);
        }

        public boolean showToast(String text) {
            if (mContext == null)
                return false;
            try {
                Toast toast = new Toast(mContext);
                mTextView.setText(text);
                toast.setView(mRootView);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                return true;
            } catch (Exception e) {
                L.error(TAG, TAG + ".showToast,exception: " + e);
            }
            return false;
        }

        public boolean showToast(int textId) {
            if (mContext == null) {
                return false;
            }
            String text = mContext.getResources().getString(textId);
            return showToast(text);
        }
    }


}
