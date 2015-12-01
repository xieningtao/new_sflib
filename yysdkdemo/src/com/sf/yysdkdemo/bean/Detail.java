package com.sf.yysdkdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieningtao on 15-11-6.
 */
public class Detail {
    public String code;
    public String count;
    public List<TVDetailListBean> data;

    public static class TVDetailListBean implements Serializable {
        private long lChannelId;
        private long lSubchannel;
        private String sNick;
        private String sSubchannelName;
        private String sAvatarUrl;
        private int iAttendeeCount;
        private int iGameId;
        private String sGameName;
        private String sVideoCaptureUrl;
        private int iRecType;
        private int live_type;
        private int showAvatar;

        public long getlChannelId() {
            return lChannelId;
        }

        public long getlSubchannel() {
            return lSubchannel;
        }

        public String getsNick() {
            return sNick;
        }

        public String getsSubchannelName() {
            return sSubchannelName;
        }

        public String getsAvatarUrl() {
            return sAvatarUrl;
        }

        public int getiAttendeeCount() {
            return iAttendeeCount;
        }

        public int getiGameId() {
            return iGameId;
        }

        public String getsGameName() {
            return sGameName;
        }

        public String getsVideoCaptureUrl() {
            return sVideoCaptureUrl;
        }

        public int getiRecType() {
            return iRecType;
        }

        public int getLive_type() {
            return live_type;
        }

        public int getShowAvatar() {
            return showAvatar;
        }

        @Override
        public String toString() {
            return "TVDetailListBean{" +
                    "lChannelId=" + lChannelId +
                    ", lSubchannel=" + lSubchannel +
                    ", sNick='" + sNick + '\'' +
                    ", sSubchannelName='" + sSubchannelName + '\'' +
                    ", sAvatarUrl='" + sAvatarUrl + '\'' +
                    ", iAttendeeCount=" + iAttendeeCount +
                    ", iGameId=" + iGameId +
                    ", sGameName='" + sGameName + '\'' +
                    ", sVideoCaptureUrl='" + sVideoCaptureUrl + '\'' +
                    ", iRecType=" + iRecType +
                    ", live_type=" + live_type +
                    ", showAvatar=" + showAvatar +
                    '}';
        }
    }
}
