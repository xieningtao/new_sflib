package com.sf.httpclient.newcore.cachecore;

import com.sf.dblib.annotation.Column;
import com.sf.dblib.annotation.Table;

import java.util.Date;

/**
 * Created by xieningtao on 16/9/4.
 */
@Table(name = "HttpCacheBean")
public class HttpCacheBean {
    private int id;

    @Column(column = "contentMd5")
    private String contentMd5;

    @Column(column = "content")
    private String content;

    @Column(column = "indexMd5")
    private String indexMd5;

    @Column(column = "time")
    private Date date;

    public String getIndexMd5() {
        return indexMd5;
    }

    public void setIndexMd5(String indexMd5) {
        this.indexMd5 = indexMd5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
