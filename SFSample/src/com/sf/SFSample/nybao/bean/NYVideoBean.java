package com.sf.SFSample.nybao.bean;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYVideoBean {

    public static class VideoTopic {
        private String alias;

        private String ename;

        private String tid;

        private String tname;

        public void setAlias(String alias){
            this.alias = alias;
        }
        public String getAlias(){
            return this.alias;
        }
        public void setEname(String ename){
            this.ename = ename;
        }
        public String getEname(){
            return this.ename;
        }
        public void setTid(String tid){
            this.tid = tid;
        }
        public String getTid(){
            return this.tid;
        }
        public void setTname(String tname){
            this.tname = tname;
        }
        public String getTname(){
            return this.tname;
        }

    }

    private String cover;

    private String description;

    private int length;

    private String m3u8_url;

    private String mp4_url;

    private int playCount;

    private int playersize;

    private String prompt;

    private String ptime;

    private String replyBoard;

    private int replyCount;

    private String replyid;

    private String sectiontitle;

    private String title;

    private String topicDesc;

    private String topicImg;

    private String topicName;

    private String topicSid;

    private String vid;

    private VideoTopic videoTopic;

    private String videosource;

    public void setCover(String cover){
        this.cover = cover;
    }
    public String getCover(){
        return this.cover;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setLength(int length){
        this.length = length;
    }
    public int getLength(){
        return this.length;
    }
    public void setM3u8_url(String m3u8_url){
        this.m3u8_url = m3u8_url;
    }
    public String getM3u8_url(){
        return this.m3u8_url;
    }
    public void setMp4_url(String mp4_url){
        this.mp4_url = mp4_url;
    }
    public String getMp4_url(){
        return this.mp4_url;
    }
    public void setPlayCount(int playCount){
        this.playCount = playCount;
    }
    public int getPlayCount(){
        return this.playCount;
    }
    public void setPlayersize(int playersize){
        this.playersize = playersize;
    }
    public int getPlayersize(){
        return this.playersize;
    }
    public void setPrompt(String prompt){
        this.prompt = prompt;
    }
    public String getPrompt(){
        return this.prompt;
    }
    public void setPtime(String ptime){
        this.ptime = ptime;
    }
    public String getPtime(){
        return this.ptime;
    }
    public void setReplyBoard(String replyBoard){
        this.replyBoard = replyBoard;
    }
    public String getReplyBoard(){
        return this.replyBoard;
    }
    public void setReplyCount(int replyCount){
        this.replyCount = replyCount;
    }
    public int getReplyCount(){
        return this.replyCount;
    }
    public void setReplyid(String replyid){
        this.replyid = replyid;
    }
    public String getReplyid(){
        return this.replyid;
    }
    public void setSectiontitle(String sectiontitle){
        this.sectiontitle = sectiontitle;
    }
    public String getSectiontitle(){
        return this.sectiontitle;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTopicDesc(String topicDesc){
        this.topicDesc = topicDesc;
    }
    public String getTopicDesc(){
        return this.topicDesc;
    }
    public void setTopicImg(String topicImg){
        this.topicImg = topicImg;
    }
    public String getTopicImg(){
        return this.topicImg;
    }
    public void setTopicName(String topicName){
        this.topicName = topicName;
    }
    public String getTopicName(){
        return this.topicName;
    }
    public void setTopicSid(String topicSid){
        this.topicSid = topicSid;
    }
    public String getTopicSid(){
        return this.topicSid;
    }
    public void setVid(String vid){
        this.vid = vid;
    }
    public String getVid(){
        return this.vid;
    }
    public void setVideoTopic(VideoTopic videoTopic){
        this.videoTopic = videoTopic;
    }
    public VideoTopic getVideoTopic(){
        return this.videoTopic;
    }
    public void setVideosource(String videosource){
        this.videosource = videosource;
    }
    public String getVideosource(){
        return this.videosource;
    }
}
