/**
 * @(#)ImageBean.java, 2015年1月15日. 
 * 
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import java.io.Serializable;

/**
 *
 * @author xltu
 *
 */
public class ImageBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7788070669895370135L;
    private String mPath;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImageBean)) {
            return false;
        }
        return mPath.equals(((ImageBean)o).mPath);
    }
    
    public void setPath(String path){
        mPath = path;
    }
    
    public String getPath(){
        return mPath;
    }
}
