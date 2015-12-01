package com.basesmartframe.basevideo.util;

/**
 * Created by xieningtao on 15-5-11.
 */
public class ActionTimeGapHelp {
    public final static int ACTION_200=200;
    public final static int ACTION_500=500;
    public final static int ACTION_1000=1000;

    private long mLastActionTime=0;

    private static ActionTimeGapHelp instance=new ActionTimeGapHelp();

    private ActionTimeGapHelp(){

    }

    public static ActionTimeGapHelp getInstance(){
        return instance;
    }

    public boolean isInActionGap(int duration){
        if(duration<=0)return true;
        long curActionTime=System.currentTimeMillis();
        long gap=  curActionTime-mLastActionTime;
        if(gap<duration){
            return true;
        }else {
            mLastActionTime=curActionTime;
            return false;
        }
    }
}
