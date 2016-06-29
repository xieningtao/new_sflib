package com.sflib.reflection.core;

/**
 * Created by NetEase on 2016/6/23 0023.
 */
public class SFMsgId {
    int BASIC_ID=100;
     public interface CacheMessage {
       int CACHE_MESSAGE_ID=101;
    }

    public interface TVMessage{
        int CATEGORY_REQUEST_ID=102;
        int DETAIL_REQUEST_ID=103;
        int CATEGORY_RESPONSE_ID=106;
    }

    public interface BannerMessage{
        int BANNER_LIST_ID=104;
    }

    public interface NetworkMessage{
        int NETWORK_AVAILABLE=105;
    }
}
