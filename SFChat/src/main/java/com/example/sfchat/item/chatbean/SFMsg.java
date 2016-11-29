package com.example.sfchat.item.chatbean;

/**
 * Created by mac on 16/11/27.
 */

public class SFMsg {

    private SFUserInfo userInfo;
    private int type;
    private String content;
    private boolean fromMe;

    public SFUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SFUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }
}
