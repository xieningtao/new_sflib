//package com.sf.circlelib.circledb;
//
//import com.sf.dblib.annotation.Column;
//import com.sf.dblib.annotation.Finder;
//import com.sf.dblib.annotation.NoAutoIncrement;
//import com.sf.dblib.annotation.Table;
//import com.sf.dblib.sqlite.FinderLazyLoader;
//
//import java.util.Date;
//
///**
// * Created by xieningtao on 16/9/5.
// */
//@Table(name = "DBContent")
//public class DBContent {
//    @Column(column = "id")
//    private int id;
//
//    @Column(column = "title")
//    private String title;
//
//    @Column(column = "content")
//    private String content;
//
//    @Column(column = "date")
//    private Date date;
//
//    @Finder(valueColumn = "id",targetColumn = "contentId")
//    private FinderLazyLoader<DBComment> comments;
//
//    @Finder(valueColumn = "id",targetColumn = "contentId")
//    private FinderLazyLoader<DBImage> images;
//    public int getId() {
//
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public FinderLazyLoader<DBComment> getComments() {
//        return comments;
//    }
//
//    public FinderLazyLoader<DBImage> getImages() {
//        return images;
//    }
//}
