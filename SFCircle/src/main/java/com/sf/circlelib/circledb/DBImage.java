//package com.sf.circlelib.circledb;
//
//import com.sf.dblib.annotation.Column;
//import com.sf.dblib.annotation.Foreign;
//import com.sf.dblib.annotation.Table;
//
///**
// * Created by xieningtao on 16/9/5.
// */
//@Table(name = "DBImage")
//public class DBImage {
//
//    @Column(column = "url")
//    private String url;
//
//    @Column(column = "id")
//    private int id;
//
//    @Foreign(column = "contentId",foreign = "id")
//    private DBContent content;
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public DBContent getContent() {
//        return content;
//    }
//
//    public void setContent(DBContent content) {
//        this.content = content;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//}
