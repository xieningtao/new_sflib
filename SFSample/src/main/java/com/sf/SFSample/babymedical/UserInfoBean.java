package com.sf.SFSample.babymedical;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xieningtao on 16/9/3.
 */
@Entity(nameInDb = "UserInfoBean")
public class UserInfoBean {

    private int id;

    @Property(nameInDb = "userName")
    private String userName;


    @Generated(hash = 1819963636)
    public UserInfoBean(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }


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
