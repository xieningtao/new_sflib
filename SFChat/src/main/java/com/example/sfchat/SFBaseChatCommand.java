package com.example.sfchat;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public interface SFBaseChatCommand {

    int CAMERA_REQUEST=1;
    int ALBUM_REQUEST=2;

    void executeCommand();
}
