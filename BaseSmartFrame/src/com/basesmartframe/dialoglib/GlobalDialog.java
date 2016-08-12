package com.basesmartframe.dialoglib;

import android.app.Dialog;
import android.view.View;

import com.basesmartframe.baseapp.BaseApp;

/**
 * Created by NetEase on 2016/7/26 0026.
 */
public class GlobalDialog {

    public static Dialog showGlobalDialog(View view){
        return DialogFactory.getNoFloatingDimDialog(BaseApp.gContext,view);
    }
}
