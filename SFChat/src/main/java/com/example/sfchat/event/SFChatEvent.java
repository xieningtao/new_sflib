package com.example.sfchat.event;

/**
 * Created by NetEase on 2016/8/30 0030.
 */
public class SFChatEvent {
    public static final int VOICE_BUTTON_PRESS=5;
    public static final int VOICE_BUTTON_UP=6;

    public static final int ADD_MSG=7;

    public final int eventType;

    public SFChatEvent(int eventType) {
        this.eventType = eventType;
    }
}
