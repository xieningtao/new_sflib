package com.basesmartframe.basevideo.core;

/**
 * Created by xieningtao on 15-11-15.
 */
public interface State {

    void play(String url);

    void toError();

    void reset();

    void pause();

    void resume();

    void seek(int millisec);

    void toLoading();
}
