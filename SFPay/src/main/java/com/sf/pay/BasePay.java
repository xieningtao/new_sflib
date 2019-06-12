package com.sf.pay;

import android.app.Activity;
import android.content.Context;

/**
 * Created by NetEase on 2016/6/15 0015.
 */
public abstract class BasePay {
   protected final String TAG=getClass().getName();

   protected SFPayCallBack mPayCallBack;

   public void setPayCallBack(SFPayCallBack mPayCallBack) {
      this.mPayCallBack = mPayCallBack;
   }

   public abstract void pay(Activity activity);
}
