package com.basesmartframe.basevideo.core;

/**
 * Created by xieningtao on 15-11-15.
 */
public class IllegalVideoState extends RuntimeException {

    public IllegalVideoState(String description){
        super(description);
    }
}
