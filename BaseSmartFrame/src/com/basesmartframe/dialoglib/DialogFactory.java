package com.basesmartframe.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.basesmartframe.R;


/**
 * Created by xieningtao on 15-4-1.
 */
public class DialogFactory {

    public final String TAG = "DialogUtil";

    /**
     * 无标题的浮动对话框
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog getNoTitleDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.MessageDiloag);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 无标题的无浮动的对话框
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog getNoFloatingDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.MessageDiloag);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 无标题的无浮动的dim对话框
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog getNoFloatingDimDialog(Context context, View view) {
        Dialog dialog = new Dialog(context,
                R.style.MessageDiloag);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 无标题的重载的对话框
     *
     * @param context
     * @param layoutId
     * @return
     * @see #getNoTitleDialog(Context, View)
     */
    public static Dialog getNoTitleDialog(Context context, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        return getNoTitleDialog(context, view);
    }

    /**
     * @param context
     * @param view
     * @return
     */
    public static Dialog getMatchParentDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.Theme_Dialog_Kiwi_ls_videoShareDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    /**
     * @param context
     * @param view
     * @param style
     * @return
     */
    public static Dialog getFullDialog(Context context, View view, int style) {
        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }
}
