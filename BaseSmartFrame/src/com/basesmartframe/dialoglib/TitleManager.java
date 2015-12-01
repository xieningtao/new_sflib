package com.basesmartframe.dialoglib;

import com.basesmartframe.log.L;

import android.content.Context;
import android.view.View;


/**
 * Created by xieningtao on 15-4-17.
 */
public class TitleManager {
    public static final String TAG="TitleManager";

    public static TitleFactory loginTitle(Context context,View rootView,TitleFactory.TitleEvent event){
        if(null==context||null==rootView){
            L.error(TAG,"context or rootView is null");
            return null;
        }else{
            TitleFactory factory=new TitleFactory.TitleBuilder(context,rootView)
                    .setLeft("取消")
                    .setTitle("登录")
                    .setLeftDrawble(null)
                    .setTitleListener(event).build();
            return factory;
        }
    }


}
