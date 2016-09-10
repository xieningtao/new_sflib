package com.sf.circlelib;

import com.sf.circlelib.abscircle.ISFComment;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFComment implements ISFComment {
    private String comment;

    private String fromName;

    private String toName;

    private int fromId;

    private int toId;

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String getFromName() {
        return fromName;
    }

    @Override
    public String getToName() {
        return toName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
}
