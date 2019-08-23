package com.basesmartframe.basevideo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.sf.utils.baseutil.SystemUIWHHelp;
import com.sf.loglib.L;


/**
 * Created by xieningtao on 15-5-6.
 */
public class ToggelSystemUIHelp {

    public static final String TAG = "ToggelSystemUIWHHelpHelp";

    private static boolean isNavigationBarRight = true;

    public static void hideStatusBar(Activity activity){
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = activity.getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void showStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = activity.getWindow().getDecorView();
            // Show Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    public static void setLayoutScreenView(Context context){
        toggleScreenView(context,false);
    }
    public static void toggleScreenView(Context context, boolean show) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!show) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                    activity.getWindow().setAttributes(attrs);

                } else {
                    View decorView = activity.getWindow().getDecorView();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        decorView.setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    } else {

//                        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                        decorView.setSystemUiVisibility(uiOptions);
                        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                        activity.getWindow().setAttributes(attrs);
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    activity.getWindow().setAttributes(attrs);
                } else {
                    View decorView = activity.getWindow().getDecorView();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        String os = Build.BRAND;
                        if (isNavigationBarRight) {// it has problem
//                            decorView.setSystemUiVisibility(
//                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                            decorView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        } else {
                            decorView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        }

                    } else {

//                        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_LAYOUT_FLAGS;
//                        decorView.setSystemUiVisibility(uiOptions);
                        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                        activity.getWindow().setAttributes(attrs);
                    }
                }
            }

        } else {
            L.error(TAG, "context is not activity");
        }
    }

    public static void resetScreen(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                activity.getWindow().setAttributes(attrs);
            } else {
                View decorView = activity.getWindow().getDecorView();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                } else {
//                    int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
//                    decorView.setSystemUiVisibility(uiOptions);
                    WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                    activity.getWindow().setAttributes(attrs);
                }
            }
        } else {
            L.error(TAG, "context is not activity");
        }
    }

    public static void configNavigationBarOritation(Activity activity) {

        Rect visibleFrame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleFrame);

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        // check if the DecorView takes the whole screen vertically or horizontally
        boolean isRightOfContent = dm.heightPixels == visibleFrame.bottom;
        isNavigationBarRight = isRightOfContent;

    }

    public static boolean isFull(Context context) {
        Activity activity = (Activity) context;
        final int width = SystemUIWHHelp.getScreenRealWidth(activity);
        final int height = SystemUIWHHelp.getScreenRealHeight(activity);
        return width > height;
    }

}
