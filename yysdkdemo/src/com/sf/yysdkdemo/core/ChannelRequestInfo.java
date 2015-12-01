package com.sf.yysdkdemo.core;


/**
 * Created by xieningtao on 15-6-11.
 */
public class ChannelRequestInfo {
    public final long sid;
    public final long subSid;
    public final int gameId;
    public final String kickContext;
    public final int videoRate;
    public final long mobileLiveId;

    private ChannelRequestInfo(long sid, long subSid, int gameId, String kickContext, int videoRate,
                               long mobileLiveId) {
        this.sid = sid;
        this.subSid = subSid;
        this.gameId = gameId;
        this.kickContext = kickContext;
        this.videoRate = videoRate;
        this.mobileLiveId = mobileLiveId;
    }

    @Override
    public String toString() {
        return "ChannelRequestInfo{" +
                "sid=" + sid +
                ", subSid=" + subSid +
                ", gameId=" + gameId +
                ", kickContext='" + kickContext + '\'' +
                ", videoRate=" + videoRate +
                ", mobileLiveId=" + mobileLiveId +
                '}';
    }

    public static class ChannelRequestInfoBuild {
        private long sid;
        private long subSid;
        private int gameId;
        private String kickContext;
        private int videoRate;
        private long mobileLiveId;

        public ChannelRequestInfoBuild setSid(long sid) {
            this.sid = sid;
            return this;
        }

        public ChannelRequestInfoBuild setSubSid(long subSid) {
            this.subSid = subSid;
            return this;
        }

        public ChannelRequestInfoBuild setGameId(int gameId) {
            this.gameId = gameId;
            return this;
        }

        public ChannelRequestInfoBuild setKickContext(String kickContext) {
            this.kickContext = kickContext;
            return this;
        }

        public ChannelRequestInfoBuild setVideoRate(int videoRate) {
            this.videoRate = videoRate;
            return this;
        }

        public ChannelRequestInfoBuild setMobileLiveId(long mobileLiveId) {
            this.mobileLiveId = mobileLiveId;
            return this;
        }


        public ChannelRequestInfo build() {
            return new ChannelRequestInfo(sid, subSid, gameId, kickContext, videoRate, mobileLiveId);
        }
    }
}
