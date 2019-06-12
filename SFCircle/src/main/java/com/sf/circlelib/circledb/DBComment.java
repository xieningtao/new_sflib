//package com.sf.circlelib.circledb;
//
//import com.sf.dblib.annotation.Column;
//import com.sf.dblib.annotation.Foreign;
//import com.sf.dblib.annotation.Table;
//
///**
// * Created by xieningtao on 16/9/5.
// */
//@Table(name = "DBComment")
//public class DBComment {
//    @Column(column = "id")
//    private int id;
//    @Column(column = "comment")
//    private String comment;
//
//    @Column(column = "fromName")
//    private String fromName;
//
//    @Column(column = "toName")
//    private String toName;
//
//    @Column(column = "fromId")
//    private int fromId;
//
//    @Column(column = "toId")
//    private int toId;
//
//    @Foreign(column = "contentId",foreign = "id")
//    private DBContent content;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
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
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public String getFromName() {
//        return fromName;
//    }
//
//    public void setFromName(String fromName) {
//        this.fromName = fromName;
//    }
//
//    public String getToName() {
//        return toName;
//    }
//
//    public void setToName(String toName) {
//        this.toName = toName;
//    }
//
//    public int getFromId() {
//        return fromId;
//    }
//
//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }
//
//    public int getToId() {
//        return toId;
//    }
//
//    public void setToId(int toId) {
//        this.toId = toId;
//    }
//}
