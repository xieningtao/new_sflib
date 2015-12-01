package com.sf.yysdkdemo.videoplayer.glutil;

import android.opengl.GLES20;

import com.duowan.mobile.mediaproxy.glutils.utils.CatchError;


public class VBO {
    private int mID = -1;

    public VBO(int target, int size, java.nio.Buffer data, int usage) {
        int id[] = new int[1];

        GLES20.glGenBuffers(1, id, 0);

        mID = id[0];

        GLES20.glBindBuffer(target, mID);
        GLES20.glBufferData(target, size, data, usage);
        GLES20.glBindBuffer(target, 0);
        CatchError.catchError("VBO");
    }

    public int getId() {
        return mID;
    }

    public void subBuffer(int target, int size, java.nio.Buffer data) {
        GLES20.glBindBuffer(target, mID);
        GLES20.glBufferSubData(target, 0, size, data);
        CatchError.catchError("subBuffer");
        GLES20.glBindBuffer(target, 0);
    }

    public void delete() {
        if(-1 != mID) {
            int id[] = new int[1];
            id[0] = mID;
            GLES20.glDeleteBuffers(1, id, 0);
            CatchError.catchError("deleteVBOBuffer");
            mID = -1;
        }
    }
}
