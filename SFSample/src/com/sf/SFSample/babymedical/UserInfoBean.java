package com.sf.SFSample.babymedical;

import com.sf.dblib.annotation.Column;
import com.sf.dblib.annotation.Table;

/**
 * Created by xieningtao on 16/9/3.
 */
@Table(name = "UserInfoBean")
public class UserInfoBean {

    private int id;

    @Column(column = "userName")
    private String userName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
